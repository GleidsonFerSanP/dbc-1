package br.com.dbccompany.core.service;

import br.com.dbccompany.core.domain.entity.VoteEntity;
import br.com.dbccompany.core.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoteFindService {

    private final VoteRepository voteRepository;

    Optional<VoteEntity> findByScheduleCodeAndCpf(final UUID uuid, final String cpf){
        return voteRepository.findByScheduleCodeAndCpf(uuid, cpf);
    }
}
