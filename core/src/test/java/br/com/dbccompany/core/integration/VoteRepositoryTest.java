package br.com.dbccompany.core.integration;

import br.com.dbccompany.core.CoreApplication;
import br.com.dbccompany.core.domain.dto.ScheduleResultDto;
import br.com.dbccompany.core.domain.entity.VoteOption;
import br.com.dbccompany.core.repository.ScheduleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = CoreApplication.class)
@ActiveProfiles("test")
@DisplayName("tests a VoteFindService")
public class VoteRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    @Sql("/sql/schedule-insert.sql")
    @DisplayName("testa a query com os resultados da votação")
    public void findVotesByScheduleCodeGroupByVoteOption(){
        final ScheduleResultDto scheduleResultDto = scheduleRepository
                .findScheduleResults("8de44aec-d624-44d7-b14b-d342fc0bf1d9");
        assertEquals("expected 2 results", 2, scheduleResultDto.getResults().size());

        scheduleResultDto.getResults().stream().forEach(voteResultDto -> {
            if(VoteOption.NO.equals(voteResultDto.getOption())){
                assertEquals("expected 3 results to NO", valueOf(3), voteResultDto.getQuantity());
            } else {
                assertEquals("expected 6 results to YES", valueOf(6),  voteResultDto.getQuantity());
            }
        });
    }

}
