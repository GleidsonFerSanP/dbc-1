package br.com.dbccompany.core.domain.dto;

import lombok.Data;

@Data
public class VoteResultDto {

    private String scheduleTitle;
    private String scheduleDescription;
    private Integer optionYes;
    private Integer optionNo;
}
