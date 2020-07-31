package br.com.dbccompany.core.service;

import br.com.dbccompany.core.domain.dto.VoteDto;
import br.com.dbccompany.core.domain.entity.VoteEntity;
import br.com.dbccompany.core.excepiton.VoteAlreadyExistsException;
import br.com.dbccompany.core.mapper.VoteMapper;
import br.com.dbccompany.core.repository.ScheduleRepository;
import br.com.dbccompany.core.repository.VoteRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteCreateService {

    private final VoteRepository voteRepository;

    private final VoteFindService voteFindService;

    private final ScheduleRepository scheduleRepository;

    private final VoteMapper voteMapper;

    public VoteDto create(@NonNull final VoteDto voteDto) {

        validate(voteDto);

        log.info("I=saving a new, vote={}", voteDto);
        var voteEntity = voteMapper.toEntity(voteDto);
        var savedVoteDto = voteMapper.toDto(voteRepository.save(voteEntity));
        log.info("I=saved with success, vote={}", savedVoteDto);
        return savedVoteDto;
    }

    private void validate(VoteDto voteDto) {
        final Optional<VoteEntity> optionalVoteEntity = voteFindService
                .findByScheduleCodeAndCpf(voteDto.getScheduleCode(), voteDto.getCpf());

        if(optionalVoteEntity.isPresent()){
            throw new VoteAlreadyExistsException("this vote already exists");
        }
    }
}
