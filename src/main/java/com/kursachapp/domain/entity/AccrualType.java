package com.kursachapp.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", length = 10)
	private String name;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
    @OneToMany(mappedBy = "accrualType", targetEntity = Accrual.class)
	@Cascade(CascadeType.SAVE_UPDATE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set accruals = new HashSet();
}