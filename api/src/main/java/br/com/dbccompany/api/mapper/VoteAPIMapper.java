package br.com.dbccompany.api.mapper;

import br.com.dbccompany.api.resource.request.v1.VoteRequest;
import br.com.dbccompany.api.resource.response.v1.VoteResponse;
import br.com.dbccompany.core.domain.dto.ScheduleDto;
import br.com.dbccompany.core.domain.dto.VoteDto;
import br.com.dbccompany.core.domain.entity.ScheduleEntity;
import br.com.dbccompany.core.domain.entity.VoteOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface VoteAPIMapper {

    @Mapping(target = "scheduleCode", source = "scheduleCode", qualifiedByName = "textToUUID")
    @Mapping(target = "option", source = "option", qualifiedByName = "textToVoteOption")
    VoteDto toDto(final VoteRequest request);

    @Mapping(target = "scheduleCode", source = "scheduleCode", qualifiedByName = "toScheduleCode")
    @Mapping(target = "code", source = "code", qualifiedByName = "toScheduleCode")
    @Mapping(target = "optionSelected", source = "option", qualifiedByName = "toOptionString")
    @Mapping(target = "voteDate", source = "datCreation")
    VoteResponse toResponse(final VoteDto voteDtoSaved);

    @Named("textToUUID")
    default UUID textToUUID(final String code){
        return UUID.fromString(code);
    }

    @Named("textToVoteOption")
    default VoteOption textToVoteOption(final String text){
        return VoteOption.from(text);
    }

    @Named("toScheduleCode")
    default String toScheduleCode(final UUID code){
        return code.toString();
    }

    @Named("toOptionString")
    default String toOptionString(final VoteOption voteOption){
        return voteOption.getDescription();
    }
}
