package br.com.dbccompany.api.resource;

import br.com.dbccompany.api.mapper.VoteAPIMapper;
import br.com.dbccompany.api.resource.request.v1.VoteRequest;
import br.com.dbccompany.api.resource.response.v1.VoteResponse;
import br.com.dbccompany.core.service.VoteCreateService;
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

@RestController
@RequestMapping("/votes")
@RequiredArgsConstructor
@Validated
public class VoteResource {

    private final VoteCreateService voteCreateService;

    private final VoteAPIMapper voteAPIMapper;

    @Operation(summary = "Create a vote")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created a vote",
                    content = { @Content(mediaType = APPLICATION_VND_SICRED_APP_V_1_JSON,
                            schema = @Schema(implementation = VoteResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid payload",
                    content = @Content)})
    @PostMapping(produces = APPLICATION_VND_SICRED_APP_V_1_JSON)
    public ResponseEntity<VoteResponse> create(@RequestBody @Valid final VoteRequest request, final Errors errors) {

        var voteDto = voteAPIMapper.toDto(request);
        var voteDtoSaved = voteCreateService.create(voteDto);
        var response = voteAPIMapper.toResponse(voteDtoSaved);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
