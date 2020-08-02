package br.com.dbccompany.core.domain.message;

import lombok.Data;

@Data
public class VoteResultMessage {
    private String option;
    private Integer quantity;
}
