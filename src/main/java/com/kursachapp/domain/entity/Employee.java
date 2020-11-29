package com.kursachapp.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "personnel_number", nullable = false, length = 10)
    private String personnelNumber;

    @ManyToOne(targetEntity = Position.class, fetch = FetchType.LAZY)
    @JoinColumns(
    		value = @JoinColumn(name = "position_id", referencedColumnName = "id", nullable = false),
			foreignKey = @ForeignKey(name = "employees_positions_id_fk"))
    private Position position;

    @Column(name = "passport_number", nullable = false, length = 10)
    private String passportNumber;

    @Column(name = "identification_code", nullable = false)
    private int identificationCode;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "personal_salary_allowance")
    private Integer personalSalaryAllowance;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "employee", targetEntity = Accrual.class)
    @Cascade({CascadeType.SAVE_UPDATE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set accruals = new HashSet();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "employee", targetEntity = VacationHistory.class)
    @Cascade({CascadeType.SAVE_UPDATE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set vacationHistory = new HashSet();
}
