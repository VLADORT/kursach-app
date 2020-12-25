package com.demo.database_service.repository;

import com.demo.database_service.domain.RecognitionResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecognitionResultRepository extends JpaRepository<RecognitionResultEntity, Long> {
}
