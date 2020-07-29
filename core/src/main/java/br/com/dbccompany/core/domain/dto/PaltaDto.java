package br.com.dbccompany.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaltaDto {

    private String code;

    @NonNull
    private String title;
    private String description;
}
