package br.com.dbccompany.core.repository.impl;

import br.com.dbccompany.core.domain.dto.VoteResultDto;
import br.com.dbccompany.core.repository.VoteRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

public class VoteRepositoryImpl implements VoteRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<VoteResultDto> findByScheduleGroupByOption(String scheduleCode) {

        final var query = entityManager.createNativeQuery(queryVotesResultByScheduleCode);
        query.setParameter("scheduleCode", scheduleCode);
        final var resultList = query.getResultList();

        return null;
    }
}
