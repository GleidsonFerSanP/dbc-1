package br.com.dbccompany.api.resource.request.v1;

import lombok.Builder;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString
@Builder
public class PaltaRequest extends RepresentationModel<PaltaRequest> {

    @NotBlank(message = "this field cannot be empty ")
    @Size(min = 1, max = 255, message = "this field size min is 1 character and max 255 characters")
    private String title;
    private String description;
}
