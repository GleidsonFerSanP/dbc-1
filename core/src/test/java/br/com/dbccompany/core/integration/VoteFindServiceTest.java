package br.com.dbccompany.core.integration;

import br.com.dbccompany.core.repository.VoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("tests a VoteFindService")
public class VoteFindServiceTest {

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @Sql("/sql/schedule-insert.sql")
    public void findVotesByScheduleCodeGroupByVoteOption(){
            voteRepository.findByScheduleCodeGroupByVoteOption("8de44aec-d624-44d7-b14b-d342fc0bf14e");
    }

}
