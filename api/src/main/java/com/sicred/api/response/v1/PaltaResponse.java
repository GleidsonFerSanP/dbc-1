package com.sicred.api.response.v1;

import lombok.*;
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
