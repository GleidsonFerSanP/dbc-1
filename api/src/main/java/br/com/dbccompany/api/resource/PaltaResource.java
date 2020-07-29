package br.com.dbccompany.api.resource;

import br.com.dbccompany.api.mapper.PaltaAPIMapper;
import br.com.dbccompany.api.resource.request.v1.PaltaRequest;
import br.com.dbccompany.api.resource.response.v1.PaltaResponse;
import br.com.dbccompany.core.domain.dto.PaltaDto;
import br.com.dbccompany.core.service.PaltaCreateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static br.com.dbccompany.api.resource.mediatype.V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/paltas")
@RequiredArgsConstructor
@Validated
public class PaltaResource {

    private final PaltaCreateService paltaCreateService;
    private final PaltaAPIMapper paltaMapper;

    @Operation(summary = "Create a palta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created a palta",
                    content = { @Content(mediaType = APPLICATION_VND_SICRED_APP_V_1_JSON,
                            schema = @Schema(implementation = PaltaResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid payload",
                    content = @Content)})
    @PostMapping(produces = APPLICATION_VND_SICRED_APP_V_1_JSON)
    public ResponseEntity<PaltaResponse> create(@RequestBody @Valid final PaltaRequest request, final Errors errors) {

        var paltaDto = paltaMapper.toDto(request);

        var paltaDtoSaved = paltaCreateService.create(paltaDto);

        var response = paltaMapper.toResponse(paltaDtoSaved);

        var link = linkTo(PaltaResource.class).slash(response.getCode()).withSelfRel();

        response.add(link);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
