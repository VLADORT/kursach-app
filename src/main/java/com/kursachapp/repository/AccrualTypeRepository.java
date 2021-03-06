package com.kursachapp.repository;

import com.kursachapp.domain.entity.AccrualType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccrualTypeRepository extends JpaRepository<AccrualType, Long> {

}