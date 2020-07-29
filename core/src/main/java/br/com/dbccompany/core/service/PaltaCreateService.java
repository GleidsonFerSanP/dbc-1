package br.com.dbccompany.core.service;

import br.com.dbccompany.core.domain.dto.PaltaDto;
import br.com.dbccompany.core.mapper.PaltaMapper;
import br.com.dbccompany.core.repository.PaltaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaltaCreateService {

    private final PaltaRepository paltaRepository;

    private final PaltaMapper paltaMapper;

    public PaltaDto create(final PaltaDto paltaDto){
        log.info("I=saving a new, palta={}", paltaDto);
        var paltaEntity = paltaMapper.toEntity(paltaDto);
        var paltaDtoSaved = paltaMapper.toDto(paltaRepository.save(paltaEntity));
        log.info("I=saved with success, palta={}", paltaDtoSaved);
        return paltaDtoSaved;
    }
}
