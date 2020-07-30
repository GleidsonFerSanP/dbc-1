package br.com.dbccompany.api.resource.request.v1;

import br.com.dbccompany.api.validator.UUID;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class VoteRequest {

    @NotBlank(message = "cpf is required")
    @Size(max = 11, min = 11, message = "this field size required 11 characters")
    @CPF
    private String cpf;

    @NotBlank(message = "this field cannot be empty")
    @UUID
    private String scheduleCode;

    @NotBlank(message = "option is required [Sim, Nao]")
    @Size(max = 3, min = 3, message = "this field size required 3 characters [Sim, Nao]")
    private String option;
}
