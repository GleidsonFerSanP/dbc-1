package br.com.dbccompany.core.repository;

import br.com.dbccompany.core.domain.entity.PaltaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaltaRepository extends JpaRepository<PaltaEntity, Long> {
}
