package com.kursachapp.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.*;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Proxy(lazy = false)
@Table(name = "positions", indexes = {@Index(name = "positions_position_name_uindex", unique = true, columnList = "position_name")})
public class Position {

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "position_name", nullable = false)
    private String positionName;

    @Column(name = "salary", nullable = false, precision = 19)
    private Float salary;

    @Column(name = "standard_vacation", nullable = false)
    private int standardVacation;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "position", targetEntity = Employee.class)
    @Cascade(CascadeType.SAVE_UPDATE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set employees = new HashSet();
}
