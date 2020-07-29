package br.com.dbccompany.core.mapper;

import br.com.dbccompany.core.domain.dto.PaltaDto;
import br.com.dbccompany.core.domain.entity.PaltaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface PaltaMapper {

    PaltaEntity toEntity(final PaltaDto paltaDto);

    @Mapping(target = "code", source = "code", qualifiedByName = "getCode")
    PaltaDto toDto(final PaltaEntity paltaEntity);

    @Named("getCode")
    default String getCode(final UUID code){
        return code.toString();
    }
}
