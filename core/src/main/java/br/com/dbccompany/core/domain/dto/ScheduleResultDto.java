package br.com.dbccompany.core.domain.dto;

import br.com.dbccompany.core.domain.entity.VoteOption;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Data
public class ScheduleResultDto {
    private String title;
    private String description;

    @Setter(AccessLevel.NONE)
    private Set<VoteResultDto> results;

    @Builder
    public ScheduleResultDto(String title, String description) {
        initResults();
        this.title = title;
        this.description = description;
    }

    public ScheduleResultDto() {
        initResults();
    }

    private void initResults() {
        this.results = results = new HashSet<>();
        results.add( VoteResultDto.builder()
                .option(VoteOption.YES.getDescription())
                .quantity(0)
                .build());

        results.add( VoteResultDto.builder()
                .option(VoteOption.NO.getDescription())
                .quantity(0)
                .build());
    }
}
