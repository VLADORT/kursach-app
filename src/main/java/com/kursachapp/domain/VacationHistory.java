package com.kursachapp.domain;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
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
    @GeneratedValue(generator = "VACATION_HISTORY_ID_GENERATOR")
    @GenericGenerator(name = "VACATION_HISTORY_ID_GENERATOR", strategy = "native")
    private long id;

    @ManyToOne(targetEntity = Employee.class, fetch = FetchType.LAZY)
    @Cascade({CascadeType.ALL})
    @JoinColumns(value =
	@JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false),
			foreignKey = @ForeignKey(name = "vacation_history_employees_id_fk"))
    private Employee employee;

    @Column(name = "vacation_start", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date vacation_start;

    @Column(name = "vacation_end", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date vacation_end;

    @Column(name = "vacation_left", nullable = false)
    private int vacation_left;
}
