package br.com.dbccompany.core.service;

import br.com.dbccompany.core.domain.dto.ScheduleDto;
import br.com.dbccompany.core.mapper.ScheduleMapper;
import br.com.dbccompany.core.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleCreateService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    public ScheduleDto create(final ScheduleDto scheduleDto){
        log.info("I=saving a new, schedule={}", scheduleDto);
        var scheduleEntity = scheduleMapper.toEntity(scheduleDto);
        var savedScheduleDto = scheduleMapper.toDto(scheduleRepository.save(scheduleEntity));
        log.info("I=saved with success, schedule={}", savedScheduleDto);
        return savedScheduleDto;
    }


}
