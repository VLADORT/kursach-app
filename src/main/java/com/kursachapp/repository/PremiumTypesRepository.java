package com.kursachapp.repository;

import com.kursachapp.domain.entity.PremiumType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumTypesRepository extends JpaRepository<PremiumType, Long> {

}
