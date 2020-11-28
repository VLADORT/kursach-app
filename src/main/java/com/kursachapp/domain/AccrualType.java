package com.kursachapp.domain;

import lombok.Data;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Proxy(lazy=false)
@Table(name="accrual_type")
public class AccrualType {

	@Column(name = "id", nullable = false)
	@Id
	@GeneratedValue(generator = "ACCRUAL_TYPE_ID_GENERATOR")
	@GenericGenerator(name = "ACCRUAL_TYPE_ID_GENERATOR", strategy = "native")
	private int id;

	@Column(name = "name", length = 10)
	private String name;

	@OneToMany(mappedBy = "accrual_type", targetEntity = Accrual.class)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.ALL})
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set accruals = new HashSet();
}