package br.com.dbccompany.core.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteResultDto {
    private String option;
    private Integer quantity;
}
