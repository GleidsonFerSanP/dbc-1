package br.com.dbccompany.api.resource.response.v1;

import lombok.Data;

@Data
public class VoteResultResponse {
    private String option;
    private Integer quantity;
}
