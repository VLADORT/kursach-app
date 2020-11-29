package com.kursachapp.domain.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Immutable
@Table(name = "current_vacation")
public class EmployeesCurrentlyInVacationView {

    @Id
    private long id;

    private String name;

    private String surname;

    private String middleName;

    private String positionName;

    private String personnelNumber;
}
