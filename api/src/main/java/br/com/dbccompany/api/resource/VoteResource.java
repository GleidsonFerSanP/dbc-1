package br.com.dbccompany.api.resource;

import br.com.dbccompany.api.mapper.VoteAPIMapper;
import br.com.dbccompany.api.resource.request.v1.VoteRequest;
import br.com.dbccompany.api.resource.response.v1.ScheduleResponse;
import br.com.dbccompany.api.resource.response.v1.VoteResponse;
import br.com.dbccompany.api.validator.UUID;
import br.com.dbccompany.core.domain.dto.ScheduleDto;
import br.com.dbccompany.core.domain.dto.VoteDto;
import br.com.dbccompany.core.service.ScheduleFindService;
import br.com.dbccompany.core.service.VoteCreateService;
import br.com.dbccompany.core.service.VoteFindService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.stream.Collectors;

import static br.com.dbccompany.api.resource.mediatype.V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/votes")
@RequiredArgsConstructor
@Validated
public class VoteResource {

    private final VoteCreateService voteCreateService;

    private final VoteAPIMapper voteAPIMapper;
    private final VoteFindService voteFindService;

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

        var response = bindResponseWithHateoasLink(voteDtoSaved);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "find all votes from a schedule")
    @GetMapping(produces = APPLICATION_VND_SICRED_APP_V_1_JSON)
    public CollectionModel<VoteResponse> findAllVotesFromASchedule(@UUID @RequestParam final String scheduleCode) {
        var responses = voteFindService.findByScheduleCode(scheduleCode).stream()
                .map(this::bindResponseWithHateoasLink)
                .collect(Collectors.toList());
        final Link link = linkTo(VoteResource.class).withSelfRel();
        return new CollectionModel<>(responses, link);
    }


    private VoteResponse bindResponseWithHateoasLink(final VoteDto voteDtoSaved) {
        var response = voteAPIMapper.toResponse(voteDtoSaved);
        var link = linkTo(ScheduleResource.class).slash(response.getCode()).withSelfRel();
        response.add(link);
        return response;
    }

}
