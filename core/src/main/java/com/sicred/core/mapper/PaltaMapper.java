package com.sicred.core.mapper;

import com.sicred.core.domain.dto.PaltaDto;
import com.sicred.core.domain.entity.PaltaEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE)
public interface PaltaMapper {

    PaltaEntity toEntity(final PaltaDto paltaDto);

    PaltaDto toDto(final PaltaEntity paltaEntity);
}
