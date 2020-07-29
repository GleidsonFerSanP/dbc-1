package br.com.dbccompany.core.mapper;

import br.com.dbccompany.core.domain.dto.PaltaDto;
import br.com.dbccompany.core.domain.entity.PaltaEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface PaltaMapper {

    PaltaEntity toEntity(final PaltaDto paltaDto);

    PaltaDto toDto(final PaltaEntity paltaEntity);
}
