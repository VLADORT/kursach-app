package com.kursachapp.repository;

import com.kursachapp.domain.view.EmployeesCurrentlyInVacationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.persistence.Id;

@Repository
public interface EmployeesCurrentlyInVacationViewRepository extends JpaRepository<EmployeesCurrentlyInVacationView, Long> {

}
