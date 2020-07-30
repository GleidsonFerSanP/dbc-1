package br.com.dbccompany.api.resource.response.v1;

import lombok.Data;

import java.util.Date;

@Data
public class VoteResponse {

    private String scheduleCode;

    private String optionSelected;

    private Date voteDate;

}
