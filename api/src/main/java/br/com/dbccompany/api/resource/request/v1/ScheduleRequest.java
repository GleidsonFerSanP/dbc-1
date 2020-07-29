package br.com.dbccompany.api.resource.request.v1;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString
@Builder
@Data
@EqualsAndHashCode(callSuper=false)
public class ScheduleRequest {

    @NotBlank(message = "this field cannot be empty ")
    @Size(min = 1, max = 255, message = "this field size min is 1 character and max 255 characters")
    private String title;
    private String description;
    private Integer expiresTime;
}
