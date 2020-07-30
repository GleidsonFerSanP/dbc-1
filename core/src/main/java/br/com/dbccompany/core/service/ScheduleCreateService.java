package br.com.dbccompany.core.service;

import br.com.dbccompany.core.domain.dto.ScheduleDto;
import br.com.dbccompany.core.mapper.ScheduleMapper;
import br.com.dbccompany.core.repository.ScheduleRepository;
import br.com.dbccompany.core.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleCreateService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final RabbitTemplate rabbit;

    public ScheduleDto create(final ScheduleDto scheduleDto){
        log.info("I=saving a new, schedule={}", scheduleDto);
        var scheduleEntity = scheduleMapper.toEntity(scheduleDto);
        var savedScheduleDto = scheduleMapper.toDto(scheduleRepository.save(scheduleEntity));
        rabbit.convertAndSend("schedule-exchange", "finished-schedule-queue",
                savedScheduleDto.getCode(), new ExpirationMessagePostProcessor(getTtl(savedScheduleDto.getExpiration())));
        log.info("I=saved with success, schedule={}", savedScheduleDto);
        return savedScheduleDto;
    }

    private Long getTtl(final Date date) {
        var dateNow = new Date();
        return DateUtils.getDifferenceMilliseconds(date, dateNow);
    }
}
