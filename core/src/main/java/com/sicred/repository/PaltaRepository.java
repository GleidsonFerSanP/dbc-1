package com.sicred.repository;

import com.sicred.domain.Palta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaltaRepository extends JpaRepository<Palta, Long> {
}
