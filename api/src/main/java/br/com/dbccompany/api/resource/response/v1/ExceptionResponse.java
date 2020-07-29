package br.com.dbccompany.api.resource.response.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ExceptionResponse {

    private String message;
    private Integer httpStatus;
    private String error;
    private String path;

    public String getTimestamp() {
        return LocalDateTime.now().toString();
    }
}
