package br.com.dbccompany.api.resource;

import br.com.dbccompany.api.mapper.ScheduleAPIMapper;
import br.com.dbccompany.api.resource.request.v1.ScheduleRequest;
import br.com.dbccompany.api.resource.response.v1.ScheduleResponse;
import br.com.dbccompany.core.service.ScheduleCreateService;
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
@RequestMapping("/schedules")
@RequiredArgsConstructor
@Validated
public class ScheduleResource {

    private final ScheduleCreateService scheduleCreateService;
    private final ScheduleAPIMapper scheduleAPIMapper;

    @Operation(summary = "Create a Schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created a Schedule",
                    content = { @Content(mediaType = APPLICATION_VND_SICRED_APP_V_1_JSON,
                            schema = @Schema(implementation = ScheduleResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid payload",
                    content = @Content)})
    @PostMapping(produces = APPLICATION_VND_SICRED_APP_V_1_JSON)
    public ResponseEntity<ScheduleResponse> create(@RequestBody @Valid final ScheduleRequest request, final Errors errors) {

        var scheduleDto = scheduleAPIMapper.toDto(request);

        var scheduleDtoSaved = scheduleCreateService.create(scheduleDto);

        var response = scheduleAPIMapper.toResponse(scheduleDtoSaved);

        var link = linkTo(ScheduleResource.class).slash(response.getCode()).withSelfRel();

        response.add(link);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
