package br.com.dbccompany.core.repository;

import br.com.dbccompany.core.domain.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
}