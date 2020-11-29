package com.kursachapp.domain.entity;

import lombok.Data;
import org.hibernate.annotations.Proxy;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Proxy(lazy=false)
@Table(name="accruals", indexes={ @Index(name="accruals_id_uindex", unique=true, columnList="id") })
public class Accrual {

	@Column(name="id", nullable=false)	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(targetEntity= AccrualType.class, fetch=FetchType.LAZY)
	@JoinColumns(
			value=@JoinColumn(name="accrual_type_id", referencedColumnName="id", nullable=false),
			foreignKey=@ForeignKey(name="accruals_accrual_type_id_fk"))
	private AccrualType accrualType;
	
	@Column(name="sum", precision=19)
	private Float sum;
	
	@Column(name="`date`", nullable=false)	
	@Temporal(TemporalType.DATE)	
	private Date date;
	
	@Column(name="days", nullable=false)	
	private int days;
	
	@ManyToOne(targetEntity= Employee.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="employee_id", referencedColumnName="id", nullable=false) }, foreignKey=@ForeignKey(name="accruals_employees_id_fk"))	
	private Employee employee;
}
