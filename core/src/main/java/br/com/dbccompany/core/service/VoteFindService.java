package br.com.dbccompany.core.service;

import br.com.dbccompany.core.domain.dto.VoteDto;
import br.com.dbccompany.core.domain.dto.VoteResultDto;
import br.com.dbccompany.core.domain.entity.VoteEntity;
import br.com.dbccompany.core.mapper.VoteMapper;
import br.com.dbccompany.core.repository.VoteRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteFindService {

    private final VoteRepository voteRepository;

    private final VoteMapper voteMapper;

    Optional<VoteEntity> findByScheduleCodeAndCpf(@NonNull final UUID uuid, @NonNull final String cpf){
        log.info("I=findByScheduleCodeAndCpf, uuid={}, cpf={}", uuid, cpf);
        final var voteEntityOptional = voteRepository.findByScheduleCodeAndCpf(uuid, cpf);
        log.info("I=findByScheduleCodeAndCpf success, voteEntityOptional={}", voteEntityOptional);
        return voteEntityOptional;
    }

    public List<VoteDto> findByScheduleCode(@NonNull final String scheduleCode) {
        log.info("I=findByScheduleCode, scheduleCode={}",scheduleCode);
        var voteDtoList = voteRepository.findByScheduleCode(UUID.fromString(scheduleCode)).stream()
                .map(voteMapper::toDto)
                .collect(Collectors.toList());
        log.info("I=findByScheduleCode success, size={}", voteDtoList.size());
        return voteDtoList;
    }

    public Set<VoteResultDto> findByScheduleCodeGroupByOption(final String scheduleCode) {
        return voteRepository.findByScheduleGroupByOption(scheduleCode);
    }
}
