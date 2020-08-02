package br.com.dbccompany.core.service;

import br.com.dbccompany.core.domain.dto.ScheduleDto;
import br.com.dbccompany.core.domain.dto.ScheduleResultDto;
import br.com.dbccompany.core.domain.entity.ScheduleEntity;
import br.com.dbccompany.core.exception.NotFoundException;
import br.com.dbccompany.core.mapper.ScheduleMapper;
import br.com.dbccompany.core.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ScheduleFindService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    public List<ScheduleDto> findAll(){
        log.info("I=init find all schedules");
        var scheduleDtos = scheduleRepository.findAll().stream()
                .map(scheduleMapper::toDto)
                .collect(Collectors.toList());

        log.info("I=schedules has be found, size={}", scheduleDtos.size());
        return scheduleDtos;
    }

    public ScheduleDto findByCode(@NonNull final String code) {
        log.info("I=init find schedule, code={}", code);
        var scheduleDto = scheduleMapper.toDto(scheduleRepository.findByCode(UUID.fromString(code))
                .orElseThrow(() -> new NotFoundException("schedule not found by code ".concat(code))));
        log.info("I=schedule has be found, schedule={}", scheduleDto);
        return scheduleDto;
    }

    ScheduleEntity findEntityByCode(@NonNull final String code) {
        log.info("I=init find schedule, code={}", code);
        var scheduleEntity = scheduleRepository.findByCode(UUID.fromString(code))
                .orElseThrow(() -> new NotFoundException("schedule not found by code"));
        log.info("I=schedule has be found, schedule={}", scheduleEntity);
        return scheduleEntity;
    }

    public ScheduleResultDto findResultsByCode(final String code) {
        log.info("I=init find scheduleResults, code={}", code);
        final var scheduleResultDto = scheduleRepository.findScheduleResults(code);
        log.info("I=schedule has be found, scheduleResultDto={}", scheduleResultDto);
        return scheduleResultDto;
    }
}
