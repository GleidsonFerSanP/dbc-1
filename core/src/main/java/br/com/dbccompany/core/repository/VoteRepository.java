package br.com.dbccompany.core.repository;

import br.com.dbccompany.core.domain.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoteRepository extends JpaRepository<VoteEntity, UUID> {
}
