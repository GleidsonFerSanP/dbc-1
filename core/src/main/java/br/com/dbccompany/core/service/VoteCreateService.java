package br.com.dbccompany.core.service;

import br.com.dbccompany.core.domain.dto.VoteDto;
import br.com.dbccompany.core.domain.entity.VoteEntity;
import br.com.dbccompany.core.exception.ScheduleExpiredException;
import br.com.dbccompany.core.exception.ScheduleNotOpenException;
import br.com.dbccompany.core.exception.UserNotFoundException;
import br.com.dbccompany.core.exception.VoteAlreadyExistsException;
import br.com.dbccompany.core.mapper.VoteMapper;
import br.com.dbccompany.core.repository.VoteRepository;
import br.com.dbccompany.core.utils.DateUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.dbccompany.core.utils.TimeMachine.now;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteCreateService {

    private final CpfFindService cpfFindService;

    private final VoteRepository voteRepository;

    private final VoteFindService voteFindService;

    private final ScheduleFindService scheduleFindService;

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

        var cpfExists = cpfFindService.exists(voteDto.getCpf(), voteDto.getBirthday());

        if(!cpfExists){
            throw new UserNotFoundException("this cpf not exists on brazilian receita federal");
        }

        final Optional<VoteEntity> optionalVoteEntity = voteFindService
                .findByScheduleCodeAndCpf(voteDto.getScheduleCode(), voteDto.getCpf());

        var scheduleDto = scheduleFindService.findByCode(voteDto.getScheduleCode().toString());

        if(isNull(scheduleDto.getExpiration())){
            throw new ScheduleNotOpenException("this schedule not be open to voting");
        }

        if(optionalVoteEntity.isPresent()){
            throw new VoteAlreadyExistsException("this vote already exists");
        }

        var expirationLocalDateTime = DateUtils.toLocalDateTime(scheduleDto.getExpiration());

        if(expirationLocalDateTime.isBefore(now())){
            throw new ScheduleExpiredException("this schedule expired");
        }
    }
}
