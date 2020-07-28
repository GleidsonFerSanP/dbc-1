package com.sicred.api.response.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PaltaResponse extends RepresentationModel<PaltaResponse> {
    private final String code;
    private final String title;
    private String description;
}
