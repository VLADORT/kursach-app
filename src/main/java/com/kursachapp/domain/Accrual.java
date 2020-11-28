package com.kursachapp.domain;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Proxy(lazy=false)
@Table(name="accruals", indexes={ @Index(name="accruals_id_uindex", unique=true, columnList="id") })
public class Accrual {

	@Column(name="id", nullable=false)	
	@Id	
	@GeneratedValue(generator="ACCRUALS_ID_GENERATOR")	
	@GenericGenerator(name="ACCRUALS_ID_GENERATOR", strategy="native")
	private int id;
	
	@ManyToOne(targetEntity= AccrualType.class, fetch=FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	@JoinColumns(
			value=@JoinColumn(name="accrual_type_id", referencedColumnName="id", nullable=false),
			foreignKey=@ForeignKey(name="accruals_accrual_type_id_fk"))
	private AccrualType accrual_type;
	
	@Column(name="sum", nullable=false, precision=19)
	private BigDecimal sum;
	
	@Column(name="`date`", nullable=false)	
	@Temporal(TemporalType.DATE)	
	private Date date;
	
	@Column(name="days", nullable=false)	
	private int days;
	
	@ManyToOne(targetEntity= Employee.class, fetch=FetchType.LAZY)
	@Cascade({CascadeType.ALL})
	@JoinColumns(value={ @JoinColumn(name="employee_id", referencedColumnName="id", nullable=false) }, foreignKey=@ForeignKey(name="accruals_employees_id_fk"))	
	private Employee employee;
}
