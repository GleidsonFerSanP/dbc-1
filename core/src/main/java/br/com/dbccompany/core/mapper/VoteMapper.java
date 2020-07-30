package br.com.dbccompany.core.mapper;

import br.com.dbccompany.core.domain.dto.VoteDto;
import br.com.dbccompany.core.domain.entity.VoteEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface VoteMapper {

    VoteEntity toEntity(final VoteDto voteDto);
}
