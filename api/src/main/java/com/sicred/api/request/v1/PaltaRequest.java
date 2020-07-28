package com.sicred.api.request.v1;

import lombok.Builder;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@ToString
@Builder
public class PaltaRequest extends RepresentationModel<PaltaRequest> {

    private String title;
    private String description;
}
