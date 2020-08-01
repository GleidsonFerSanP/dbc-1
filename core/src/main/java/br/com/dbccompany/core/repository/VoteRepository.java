package br.com.dbccompany.core.repository;

import br.com.dbccompany.core.domain.dto.VoteResultDto;
import br.com.dbccompany.core.domain.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface VoteRepository extends JpaRepository<VoteEntity, UUID>, VoteRepositoryCustom {
    Optional<VoteEntity> findByScheduleCodeAndCpf(final UUID scheduleCode, final String cpf);

    List<VoteEntity> findByScheduleCode(final UUID fromString);

}
