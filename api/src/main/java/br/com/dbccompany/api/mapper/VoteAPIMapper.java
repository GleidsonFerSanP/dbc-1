package br.com.dbccompany.api.mapper;

import br.com.dbccompany.api.resource.request.v1.VoteRequest;
import br.com.dbccompany.api.resource.response.v1.VoteResponse;
import br.com.dbccompany.core.domain.dto.VoteDto;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface VoteAPIMapper {

    VoteDto toDto(final VoteRequest request);

    VoteResponse toResponse(final VoteDto voteDtoSaved);
}
