package br.com.dbccompany.core.service;

import br.com.dbccompany.core.domain.dto.VoteDto;
import br.com.dbccompany.core.mapper.VoteMapper;
import br.com.dbccompany.core.repository.VoteRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteCreateService {

    private final VoteRepository voteRepository;

    private final VoteMapper voteMapper;

    public VoteDto create(@NonNull final VoteDto voteDto) {
        voteMapper.toEntity(voteDto);

        return null;
    }
}
