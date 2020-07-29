package br.com.dbccompany.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {

    private String code;

    @NonNull
    private String title;
    private String description;
    private Date expiration;
}
