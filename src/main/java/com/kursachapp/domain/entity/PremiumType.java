package com.kursachapp.domain.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import javax.persistence.*;

@Entity
@Data
@Proxy(lazy = false)
@Table(name = "premium_types")
public class PremiumType {

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "number_of_years", nullable = false)
    private int numberOfYears;

    @Column(name = "accrual_to_salary", nullable = false)
    private Float accrualToSalary;

    @Column(name = "accrual_to_vacation")
    private Float accrualToVacation;
}
