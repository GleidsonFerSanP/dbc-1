package br.com.dbccompany.core.repository.impl;

import br.com.dbccompany.core.domain.dto.ScheduleResultDto;
import br.com.dbccompany.core.domain.dto.VoteResultDto;
import br.com.dbccompany.core.domain.entity.VoteOption;
import br.com.dbccompany.core.exception.NotFoundException;
import br.com.dbccompany.core.repository.ScheduleRepositoryCustom;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Transactional(readOnly = true)
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ScheduleResultDto findScheduleResults(String scheduleCode) {

        final var query = entityManager.createNativeQuery(queryVotesResultByScheduleCode);
        query.setParameter("scheduleCode", UUID.fromString(scheduleCode));
        final List<Object[]> resultList = query.getResultList();

        final ScheduleResultDto scheduleResultDto = resultList.stream()
                .findFirst()
                .map(objects ->
                        ScheduleResultDto.builder()
                                .title((String) objects[0])
                                .description(nonNull(objects[1]) ? (String) objects[1] : null)
                                .build()
                )
                .orElseThrow(() ->
                        new NotFoundException(("schedule not found to scheduleCode ".concat(scheduleCode))));

        resultList.stream()
                .forEach(o -> {
                            if(nonNull(o[2])){
                                final var voteOption = VoteOption.valueOf((String) o[2]);
                                scheduleResultDto.getResults().forEach(voteResultDto -> {
                                    if(voteResultDto.getOption().equals(voteOption.getDescription())){
                                        voteResultDto.setQuantity(((BigInteger) o[3]).intValue());
                                    }
                                });
                            }
                        }
                );

        return scheduleResultDto;
    }
}
