package com.kursachapp.repository;

import com.kursachapp.domain.entity.VacationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VacationHistoryRepository extends JpaRepository<VacationHistory, Long> {
    List<VacationHistory> findByEmployeeId(long employeeId);
}
