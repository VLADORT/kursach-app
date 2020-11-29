package com.kursachapp.repository;

import com.kursachapp.domain.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeesRepository extends JpaRepository<Employee, Long> {

    @Query(value = "select distinct employees.id, name, surname, middle_name, position_id, personnel_number," +
            " position_id, passport_number, identification_code, start_date, personal_salary_allowance " +
            "from employees " +
            "join positions p on employees.position_id = p.id " +
            "join vacation_history vh on employees.id = vh.employee_id " +
            "where date_part('year', vacation_start) = date_part('year', CURRENT_DATE)", nativeQuery = true)
    List<Employee> findAllThatTookVacationCurrentYear();

    @Query(value = "select distinct employee_id as id, name, middle_name, surname, vacation_end - vacation_start as duration from employees join vacation_history vh on employees.id = vh.employee_id where vacation_end - vacation_start = (select max(vacation_end - vacation_start) from vacation_history);", nativeQuery = true)
    List<TheLongestVacations> findEmployeesWithLongestVacation();

    @Query(value = "select employees.id as id, position_name as position, name, middle_name as middle, surname as surname, (CURRENT_DATE - start_date) as worked from employees join positions p on employees.position_id = p.id where CURRENT_DATE - start_date = (select max(CURRENT_DATE - start_date) from employees join positions p2 on employees.position_id = p2.id where p.position_name = p2.position_name);", nativeQuery = true)
    List<TheLongestWorkersByPosition> findTheLongestWorkersByPosition();

    @Query(value = "select employee_id, date_part('year', date) as year, sum(accruals.sum) as accrual, name, surname, middle_name as mid, personnel_number as num from accruals join employees e on accruals.employee_id = e.id where employee_id = ?1 group by employee_id, date_part('year', date), name, surname, middle_name, personnel_number;", nativeQuery = true)
    List<AccrualsSumForEachYear> getAccrualsSumForEachYear(Long id);

    @Query(value = "select distinct employee_id as id, name, middle_name as mid, surname from employees join vacation_history vh on employees.id = vh.employee_id where date_part('month', CURRENT_DATE) - date_part('month', vacation_start) <= 2 and ((select count(*) from vacation_history where date_part('month', CURRENT_DATE)-date_part('month', vacation_start) = 0 and vacation_history.employee_id = vh.employee_id) > 0    and (select count(*) from vacation_history where date_part('month', CURRENT_DATE)-date_part('month', vacation_start) = 1 and vacation_history.employee_id = vh.employee_id) > 0    and (select count(*) from vacation_history where date_part('month', CURRENT_DATE)-date_part('month', vacation_start) = 2 and vacation_history.employee_id = vh.employee_id) > 0) group by name, surname, middle_name, personnel_number, vacation_start, employee_id;", nativeQuery = true)
    List<EveryMonthForLastThreeMonthsVacationEmployee> findAllByEveryMonthVacationForLastThreeMonths();

    @Query(value = "select distinct  max(count) as count, name, middle_name as mid, surname, personnel_number as num from (select count(vh.id) as count, name, middle_name, surname, personnel_number from employees join vacation_history vh on employees.id = vh.employee_id group by name, middle_name, surname, personnel_number) as ev where (count = (SELECT MAX (result) FROM (select count(vh.id) as result from employees join vacation_history vh on employees.id = vh.employee_id group by name) as res)) group by name, middle_name, surname, personnel_number;", nativeQuery = true)
    List<MostFrequentVacationEmployees> findAllByMostFrequentVacation();
}