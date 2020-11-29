package com.kursachapp.domain.entity;

import lombok.Data;
import org.hibernate.annotations.Proxy;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Proxy(lazy = false)
@Table(name = "vacation_history")
public class VacationHistory {

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = Employee.class, fetch = FetchType.LAZY)
    @JoinColumns(value =
	@JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false),
			foreignKey = @ForeignKey(name = "vacation_history_employees_id_fk"))
    private Employee employee;

    @Column(name = "vacation_start", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date vacationStart;

    @Column(name = "vacation_end", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date vacationEnd;

    @Column(name = "vacation_left")
    private Integer vacationLeft;
}
