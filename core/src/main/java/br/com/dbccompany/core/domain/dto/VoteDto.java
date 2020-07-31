package br.com.dbccompany.core.domain.dto;

import br.com.dbccompany.core.domain.entity.VoteOption;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class VoteDto {

    private UUID code;

    private UUID scheduleCode;

    private VoteOption option;

    private String cpf;

    private Date datCreation;
}
