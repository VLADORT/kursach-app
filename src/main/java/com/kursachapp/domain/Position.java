package com.kursachapp.domain;

import lombok.Data;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.*;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Proxy(lazy = false)
@Table(name = "positions", indexes = {@Index(name = "positions_position_name_uindex", unique = true, columnList = "position_name")})
public class Position {

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(generator = "POSITIONS_ID_GENERATOR")
    @GenericGenerator(name = "POSITIONS_ID_GENERATOR", strategy = "native")
    private int id;

    @Column(name = "position_name", nullable = false)
    private String position_name;

    @Column(name = "salary", nullable = false, precision = 19)
    private BigDecimal salary;

    @Column(name = "standard_vacation", nullable = false)
    private int standard_vacation;

    @OneToMany(mappedBy = "position", targetEntity = Employee.class)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set employees = new HashSet();
}
