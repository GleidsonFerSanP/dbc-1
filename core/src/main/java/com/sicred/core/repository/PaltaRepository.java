package com.sicred.core.repository;

import com.sicred.core.domain.entity.PaltaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaltaRepository extends JpaRepository<PaltaEntity, Long> {
}
