package com.kursachapp.domain;

import lombok.Data;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.*;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Proxy(lazy = false)
@Table(name = "employees", indexes = {
		@Index(name = "employees_personnel_number_uindex", unique = true, columnList = "personnel_number"),
		@Index(name = "employees_identification_code_uindex", unique = true, columnList = "identification_code"),
		@Index(name = "employees_passport_number_uindex", unique = true, columnList = "passport_number")})
public class Employee {

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(generator = "EMPLOYEES_ID_GENERATOR")
    @GenericGenerator(name = "EMPLOYEES_ID_GENERATOR", strategy = "native")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "middle_name")
    private String middle_name;

    @Column(name = "personnel_number", nullable = false, length = 10)
    private String personnel_number;

    @ManyToOne(targetEntity = Position.class, fetch = FetchType.LAZY)
    @Cascade({CascadeType.ALL})
    @JoinColumns(
    		value = @JoinColumn(name = "position_id", referencedColumnName = "id", nullable = false),
			foreignKey = @ForeignKey(name = "employees_positions_id_fk"))
    private Position position;

    @Column(name = "passport_number", nullable = false, length = 10)
    private String passport_number;

    @Column(name = "identification_code", nullable = false)
    private int identification_code;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date start_date;

    @Column(name = "personal_salary_allowance")
    private Integer personal_salary_allowance;

    @OneToMany(mappedBy = "employee", targetEntity = Accrual.class)
    @Cascade({CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set accruals = new HashSet();

    @OneToMany(mappedBy = "employee", targetEntity = VacationHistory.class)
    @Cascade({CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set vacation_history = new HashSet();
}
