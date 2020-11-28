package com.kursachapp.domain;

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
    @GeneratedValue(generator = "PREMIUM_TYPES_ID_GENERATOR")
    @GenericGenerator(name = "PREMIUM_TYPES_ID_GENERATOR", strategy = "native")
    private long id;

    @Column(name = "number_of_years", nullable = false)
    private int number_of_years;

    @Column(name = "accrual_to_salary", nullable = false)
    private int accrual_to_salary;

    @Column(name = "accrual_to_vacation")
    private Integer accrual_to_vacation;
}
