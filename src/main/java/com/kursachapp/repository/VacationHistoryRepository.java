package com.kursachapp.repository;

import com.kursachapp.domain.VacationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationHistoryRepository extends JpaRepository<VacationHistory, Long> {

}
