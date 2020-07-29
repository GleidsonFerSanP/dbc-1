package br.com.dbccompany.api.resource.response.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseList {
    private List<ExceptionResponse> errors;
}
