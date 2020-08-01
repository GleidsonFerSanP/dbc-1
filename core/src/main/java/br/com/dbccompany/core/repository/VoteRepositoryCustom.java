package br.com.dbccompany.core.repository;

import br.com.dbccompany.core.domain.dto.VoteResultDto;

import java.util.Set;

public interface VoteRepositoryCustom {

    String queryVotesResultByScheduleCode = "select " +
            "    s.title as scheduleTitle, " +
            "    s.description as scheduleDescription, " +
            "    v.option, " +
            "    count(v.option) as quantity " +
            " from vote as v " +
            " right join schedule s on s.code = v.code_schedule " +
            " where s.code = :scheduleCode " +
            " group by option, scheduleTitle, scheduleDescription ";

    Set<VoteResultDto> findByScheduleGroupByOption(final String scheduleCode);

}
