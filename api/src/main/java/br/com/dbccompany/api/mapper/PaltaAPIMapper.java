package br.com.dbccompany.api.mapper;

import br.com.dbccompany.api.resource.request.v1.PaltaRequest;
import br.com.dbccompany.api.resource.response.v1.PaltaResponse;
import br.com.dbccompany.core.domain.dto.PaltaDto;
import br.com.dbccompany.core.utils.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface PaltaAPIMapper {

    Integer DEFAULT_EXPIRES_TIME = 1;

    @Mapping(target = "expiration", source = "expiresTime", qualifiedByName = "getExpiration")
    PaltaDto toDto(final PaltaRequest request);

    PaltaResponse toResponse(final PaltaDto paltaDto);

    @Named("getExpiration")
    default Date getExpiration(final Integer expiresTime){
        return Objects.nonNull(expiresTime) && expiresTime.intValue() > 0 ?
                DateUtils.toDate(LocalDateTime.now().plus(expiresTime, ChronoUnit.MINUTES)) :
                DateUtils.toDate(LocalDateTime.now().plus(DEFAULT_EXPIRES_TIME, ChronoUnit.MINUTES));
    }
}
