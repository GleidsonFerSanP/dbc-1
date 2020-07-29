package br.com.dbccompany.api.resource.v1;

import br.com.dbccompany.api.mapper.PaltaAPIMapper;
import br.com.dbccompany.api.request.v1.PaltaRequest;
import br.com.dbccompany.api.response.v1.PaltaResponse;
import br.com.dbccompany.core.service.PaltaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/paltas")
@RequiredArgsConstructor
public class PaltaResource {

    public static final String APPLICATION_VND_SICRED_APP_V_1_JSON = "application/vnd.sicred.app-v1+json";

    private final PaltaService paltaService;
    private final PaltaAPIMapper paltaMapper;

    @Operation(summary = "Create a palta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created a palta",
                    content = { @Content(mediaType = APPLICATION_VND_SICRED_APP_V_1_JSON,
                            schema = @Schema(implementation = PaltaResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid payload",
                    content = @Content)})
    @PostMapping(produces = APPLICATION_VND_SICRED_APP_V_1_JSON)
    public ResponseEntity<PaltaResponse> create(@RequestBody final PaltaRequest request) {

        var paltaDto = paltaMapper.toDto(request);

        var response = paltaMapper.toResponse(paltaService.create(paltaDto));

        var link = linkTo(PaltaResource.class).slash(response.getCode()).withSelfRel();

        response.add(link);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}