package br.com.dbccompany.api.resource.response.v1;

import lombok.Data;

import java.util.Set;

@Data
public class ScheduleResultsResponse {

    private String title;
    private String description;

    private Set<VoteResultResponse> results;
}
