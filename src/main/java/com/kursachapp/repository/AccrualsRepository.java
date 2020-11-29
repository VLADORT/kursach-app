package com.kursachapp.repository;

import com.kursachapp.domain.entity.Accrual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccrualsRepository extends JpaRepository<Accrual, Long> {
}
