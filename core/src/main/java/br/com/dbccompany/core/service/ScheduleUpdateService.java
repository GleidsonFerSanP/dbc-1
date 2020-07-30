package br.com.dbccompany.core.service;

import br.com.dbccompany.core.domain.dto.ScheduleDto;
import br.com.dbccompany.core.excepiton.InvalidExpirationTimeException;
import br.com.dbccompany.core.mapper.ScheduleMapper;
import br.com.dbccompany.core.repository.ScheduleRepository;
import br.com.dbccompany.core.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

import static br.com.dbccompany.core.domain.entity.ScheduleEntity.DEFAULT_EXPIRES_TIME;

@Service
@AllArgsConstructor
@Slf4j
public class ScheduleUpdateService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleFindService scheduleFindService;

    private final ScheduleMapper scheduleMapper;

    public ScheduleDto update(final String code, final ScheduleDto scheduleDto) {
        log.info("I=updating, schedule={}", scheduleDto);

        var existsScheduleEntity = scheduleFindService.findEntityByCode(code);

        scheduleMapper.updateEntity(scheduleDto, existsScheduleEntity);
        scheduleRepository.save(existsScheduleEntity);

        var savedScheduleDto = scheduleMapper.toDto(existsScheduleEntity);
        log.info("I=saved with success, schedule={}", savedScheduleDto);
        return savedScheduleDto;
    }

    public ScheduleDto updateSetExpiration(final String code, final Integer expiresTimeMinutes) {
        log.info("I=updating expiration, code={}, expiresTimeMinutes={}", code, expiresTimeMinutes);

        var existsScheduleEntity = scheduleFindService.findEntityByCode(code);

        existsScheduleEntity.setExpiration(buildExpiration(expiresTimeMinutes));

        scheduleRepository.save(existsScheduleEntity);

        var savedScheduleDto = scheduleMapper.toDto(existsScheduleEntity);
        log.info("I=saved with success, schedule={}", savedScheduleDto);
        return savedScheduleDto;
    }

    private Date buildExpiration(Integer expiresTimeMinutes) {
        return Objects.nonNull(expiresTimeMinutes) && expiresTimeMinutes.intValue() > 0 ?
                DateUtils.toDate(LocalDateTime.now().plus(expiresTimeMinutes, ChronoUnit.MINUTES)) :
                DateUtils.toDate(LocalDateTime.now().plus(DEFAULT_EXPIRES_TIME, ChronoUnit.MINUTES));
    }
}
