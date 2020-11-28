package com.kursachapp.repository;

import com.kursachapp.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionsRepository extends JpaRepository<Position, Long> {
}