package br.com.dbccompany.core.domain.dto;

import br.com.dbccompany.core.domain.entity.VoteOption;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VoteResultDto {
    private String scheduleCode;
    private VoteOption option;
    private BigDecimal quantity;
}
