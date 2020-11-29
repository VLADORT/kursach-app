create schema public;

comment on schema public is 'standard public schema';

alter schema public owner to postgres;

create table if not exists accrual_type
(
    id serial not null
        constraint accrual_type_pkey
            primary key,
    name varchar(10)
);

alter table accrual_type owner to postgres;

create table if not exists positions
(
    id bigserial not null
        constraint positions_pkey
            primary key,
    position_name varchar(255) not null,
    salary numeric(19) not null,
    standard_vacation integer not null
);

alter table positions owner to postgres;

create table if not exists employees
(
    id bigserial not null
        constraint employees_pkey
            primary key,
    name varchar(255) not null,
    surname varchar(255),
    middle_name varchar(255),
    personnel_number varchar(10) not null,
    position_id bigint not null
        constraint employees_positions_id_fk
            references positions,
    passport_number varchar(10) not null,
    identification_code integer not null,
    start_date date not null,
    personal_salary_allowance integer
);

alter table employees owner to postgres;

create table if not exists accruals
(
    id serial not null
        constraint accruals_pkey
            primary key,
    accrual_type_id integer not null
        constraint accruals_accrual_type_id_fk
            references accrual_type,
    sum numeric(19) not null,
    date date not null,
    days integer not null,
    employee_id bigint not null
        constraint accruals_employees_id_fk
            references employees
);

alter table accruals owner to postgres;

create unique index if not exists accruals_id_uindex
    on accruals (id);

create unique index if not exists employees_personnel_number_uindex
    on employees (personnel_number);

create unique index if not exists employees_identification_code_uindex
    on employees (identification_code);

create unique index if not exists employees_passport_number_uindex
    on employees (passport_number);

create unique index if not exists positions_position_name_uindex
    on positions (position_name);

create table if not exists premium_types
(
    id serial not null
        constraint premium_types_pkey
            primary key,
    number_of_years integer not null,
    accrual_to_salary integer not null,
    accrual_to_vacation integer
);

alter table premium_types owner to postgres;

create table if not exists vacation_history
(
    id bigserial not null
        constraint vacation_history_pkey
            primary key,
    employee_id bigint not null
        constraint vacation_history_employees_id_fk
            references employees,
    vacation_start date not null,
    vacation_end date not null,
    vacation_left integer not null
);

alter table vacation_history owner to postgres;

create or replace view current_vacation(id, name, surname, middle_name, position_name, personnel_number) as
SELECT employees.id,
       employees.name,
       employees.surname,
       employees.middle_name,
       p.position_name,
       employees.personnel_number
FROM employees
         JOIN positions p ON employees.position_id = p.id
         JOIN vacation_history vh ON employees.id = vh.employee_id
WHERE CURRENT_DATE <= vh.vacation_end;

alter table current_vacation owner to postgres;

create or replace view last_year_vacation(id, name, surname, middle_name, position_name, personnel_number) as
SELECT employees.id,
       employees.name,
       employees.surname,
       employees.middle_name,
       p.position_name,
       employees.personnel_number
FROM employees
         JOIN positions p ON employees.position_id = p.id
         JOIN vacation_history vh ON employees.id = vh.employee_id
WHERE date_part('year'::text, vh.vacation_start) = date_part('year'::text, CURRENT_DATE);

alter table last_year_vacation owner to postgres;

create or replace function check_vacation() returns trigger
    language plpgsql
as $$
declare
    last_month_vacation_times cursor (e_id int) for
        (select count(*)
         from vacation_history
         where vacation_history.employee_id = e_id
           and vacation_start > CURRENT_TIMESTAMP - interval '30 day');
    count int;
begin
    open last_month_vacation_times(new.employee_id);
    fetch last_month_vacation_times into count;
    if (count >= 2) then raise exception 'Cannot take vacation more than twice for the last 30 days'; end if;
    close last_month_vacation_times;
    return new;
end
$$;

alter function check_vacation() owner to postgres;

create trigger check_vacation
    before insert or update
    on vacation_history
    for each row
execute procedure check_vacation();

create or replace function accrue_money() returns trigger
    language plpgsql
as $$
declare
    accrual_cursor cursor (a_id bigint) for
        (select name
         from accrual_type
         where id = a_id);
    accrual_type varchar(10);
    position_cursor cursor (e_id bigint) for
        (select *
         from positions join employees e on positions.id = e.position_id
         where e.id = e_id);
    position positions%rowtype;
    personal_salary_allowance_cursor cursor (e_id int) for
        (select personal_salary_allowance
         from employees
         where employees.id = e_id);
    personal_salary_allowance numeric;
    sum numeric;
begin
    open accrual_cursor(new.accrual_type_id);
    open position_cursor(new.employee_id);
    open personal_salary_allowance_cursor(new.employee_id);
    fetch accrual_cursor into accrual_type;
    fetch position_cursor into position;
    fetch personal_salary_allowance_cursor into personal_salary_allowance;

    if (new.sum is not null) then
        raise exception 'Sum of accrual is calculated automatically on insert, if you need to set a custom value, do it in the update operation.';
    end if;
    if (accrual_type = 'salary') then
        sum := (position.salary + (position.salary * calculate_premium_salary(new.employee_id))/100 + personal_salary_allowance) * 0.8;
        new.sum := sum;
    end if;
    if (accrual_type = 'vacation') then
        if (new.days > calculate_remaining_vacation(new.employee_id)) then raise exception 'Cannot take more vacation days then left'; end if;
        new.sum = calculate_average_salary(new.employee_id) * new.days; end if;
    if (accrual_type != 'salary' and accrual_type != 'vacation') then
        raise exception 'Undefined accrual type';
    end if;
    close accrual_cursor;
    close position_cursor;
    close personal_salary_allowance_cursor;
    return new;
end
$$;

alter function accrue_money() owner to postgres;

create trigger accrual_cursor
    before insert
    on accruals
    for each row
execute procedure accrue_money();

create or replace function calculate_average_salary(e_id bigint) returns numeric
    language plpgsql
as $$
declare
    accruals_cursor cursor for
        (select * from accruals where employee_id = e_id and date > CURRENT_TIMESTAMP - interval '1 year');
    accruals accruals%rowtype;
    salary_cursor cursor for
        (select salary from employees join positions p on employees.position_id = p.id where employees.id = e_id );
    salary numeric;
    sum numeric;
begin
    open accruals_cursor;
    open salary_cursor;
    sum := 0;
    loop
        fetch accruals_cursor into accruals;
        exit when not found;
        if (sum=0) then sum := accruals.sum/accruals.days;
        else sum = (sum + accruals.sum/accruals.days)/2;
        end if;
    end loop;
    if (sum=0) then
        fetch salary_cursor into salary;
        sum:= salary/22;
    end if;
    return sum;
end;
$$;

alter function calculate_average_salary(bigint) owner to postgres;

create or replace function calculate_premium_salary(employee_id bigint) returns integer
    language plpgsql
as $$
declare
    years_cursor cursor for
        (select date_part('year', CURRENT_DATE) - date_part('year', start_date)
         from employees
         where id = employee_id);
    years int;
    premium_cursor cursor (years int) for
        (select *
         from premium_types
         where number_of_years - years > 0
           and number_of_years - years <= 5);
    premium premium_types%rowtype;
begin
    open years_cursor;
    fetch years_cursor into years;
    open premium_cursor(years);
    fetch premium_cursor into premium;
    return premium.accrual_to_salary;
end;
$$;

alter function calculate_premium_salary(bigint) owner to postgres;

create or replace function calculate_remaining_vacation(e_id bigint) returns integer
    language plpgsql
as $$
declare
    years_cursor cursor for
        (select date_part('year', CURRENT_DATE) - date_part('year', start_date)
         from employees
         where id = e_id);
    years int;
    premium_cursor cursor (years int) for
        (select *
         from premium_types
         where number_of_years - years > 0
           and number_of_years - years <= 5);
    premium premium_types%rowtype;
    position_cursor cursor (e_id bigint) for
        (select *
         from positions join employees e on positions.id = e.position_id
         where e.id = e_id);
    position positions%rowtype;

    used_vacation_cursor cursor for
        (select COALESCE(sum(vacation_end-vacation_start), 0) as sum
         from vacation_history
         where date_part('year', CURRENT_DATE) = date_part('year', vacation_start) and
                 employee_id = e_id);
    used_vacation_days int;
    paid_vacation_cursor cursor for
        (select COALESCE(sum(days), 0) as sum
         from accruals join accrual_type a on accruals.accrual_type_id = a.id
         where date_part('year', CURRENT_DATE) = date_part('year', date) and
                 employee_id = e_id and a.name = 'vacation');
    paid_vacation_days int;
begin
    open years_cursor;
    fetch years_cursor into years;
    open premium_cursor(years);
    fetch premium_cursor into premium;
    open position_cursor(e_id);
    fetch position_cursor into position;
    open used_vacation_cursor;
    fetch used_vacation_cursor into used_vacation_days;
    open paid_vacation_cursor;
    fetch paid_vacation_cursor into paid_vacation_days;

    close years_cursor;
    close premium_cursor;
    close position_cursor;
    close used_vacation_cursor;
    close paid_vacation_cursor;
    return premium.accrual_to_vacation + position.standard_vacation - used_vacation_days - paid_vacation_days;

end;
$$;

alter function calculate_remaining_vacation(bigint) owner to postgres;

create or replace function calculate_vacation() returns trigger
    language plpgsql
as $$
declare
    work_span_cursor cursor (e_id bigint) for
        (select (DATE_PART('year', CURRENT_DATE::date) - DATE_PART('year', start_date::date)) * 12 +
                (DATE_PART('month', CURRENT_DATE::date) - DATE_PART('month', start_date::date)) from employees
         where employees.id = e_id);
    work_span int;
    current_vacation_left int;
begin
    open work_span_cursor(new.employee_id);
    fetch work_span_cursor into work_span;
    if (new.vacation_left is not null) then raise exception 'Field vacation_left is calculated automatically and must be null'; end if;
    if (work_span < 6) then raise exception 'Cannot take vacation if worked less then 6 months'; end if;
    current_vacation_left := calculate_remaining_vacation(new.employee_id);
    if (current_vacation_left - (new.vacation_end - new.vacation_start) < 0) then raise exception 'Cannot take more vacation than left, left: %', current_vacation_left; end if;
    new.vacation_left := current_vacation_left - (new.vacation_end - new.vacation_start);
    close work_span_cursor;
    return new;
end
$$;

alter function calculate_vacation() owner to postgres;

create trigger calculate_vacation
    before insert or update
    on vacation_history
    for each row
execute procedure calculate_vacation();

