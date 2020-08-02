package br.com.dbccompany.core.repository;

import br.com.dbccompany.core.domain.dto.ScheduleResultDto;
import br.com.dbccompany.core.domain.dto.VoteResultDto;

import java.util.Set;

public interface ScheduleRepositoryCustom {

    String queryVotesResultByScheduleCode = "select " +
            "    s.title, " +
            "    s.description, " +
            "    v.option, " +
            "    count(v.option) as quantity " +
            " from vote as v " +
            " right join schedule s on s.code = v.code_schedule " +
            " where s.code = :scheduleCode " +
            " group by s.code, option, title, description ";

    ScheduleResultDto findScheduleResults(final String scheduleCode);

}
