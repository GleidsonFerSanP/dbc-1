package br.com.dbccompany.core.service;

import br.com.dbccompany.core.domain.dto.ScheduleDto;
import br.com.dbccompany.core.mapper.ScheduleMapper;
import br.com.dbccompany.core.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
