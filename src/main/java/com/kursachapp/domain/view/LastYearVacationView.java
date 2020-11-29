package com.kursachapp.domain.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Immutable
@Table(name = "last_year_vacation")
public class LastYearVacationView {

    @Id
    private long id;

    private String name;

    private String surname;

    private String middleName;

    private String positionName;

    private String personnelNumber;

    private Date vacationStart;

    private Date vacationEnd;
}
