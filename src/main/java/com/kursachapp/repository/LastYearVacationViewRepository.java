package com.kursachapp.repository;

import com.kursachapp.domain.view.LastYearVacationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastYearVacationViewRepository extends JpaRepository<LastYearVacationView, Long> {

}
