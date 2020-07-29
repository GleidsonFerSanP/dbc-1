package br.com.dbccompany.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaltaDto {

    private String code;
    private String name;
    private String description;
}
