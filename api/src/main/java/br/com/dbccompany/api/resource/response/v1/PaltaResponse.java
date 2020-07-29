package br.com.dbccompany.api.resource.response.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PaltaResponse extends RepresentationModel<PaltaResponse> {
    private String code;
    private String title;
    private String description;
}
