package br.com.dbccompany.api.resource;

import br.com.dbccompany.api.mapper.ScheduleAPIMapper;
import br.com.dbccompany.api.resource.request.v1.ExpiresTimeRequest;
import br.com.dbccompany.api.resource.request.v1.ScheduleRequest;
import br.com.dbccompany.api.resource.response.v1.ScheduleResponse;
import br.com.dbccompany.core.domain.dto.ScheduleDto;
import br.com.dbccompany.core.service.ScheduleCreateService;
import br.com.dbccompany.core.service.ScheduleFindService;
import br.com.dbccompany.core.service.ScheduleUpdateService;
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

import java.util.Objects;
import java.util.stream.Collectors;

import static br.com.dbccompany.api.resource.mediatype.V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
@Validated
public class ScheduleResource {

    private final ScheduleCreateService scheduleCreateService;
    private final ScheduleUpdateService scheduleUpdateService;
    private final ScheduleFindService scheduleFindService;
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

        var response = bindResponseWithHateoasLink(scheduleDtoSaved);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a Schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success update a Schedule",
                    content = { @Content(mediaType = APPLICATION_VND_SICRED_APP_V_1_JSON,
                            schema = @Schema(implementation = ScheduleResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "schedule not found", content = @Content)})
    @PutMapping(value = "/{code}", produces = APPLICATION_VND_SICRED_APP_V_1_JSON)
    public ResponseEntity<ScheduleResponse> update(@PathVariable final String code,
                                                   @RequestBody @Valid final ScheduleRequest request, final Errors errors) {

        var scheduleDto = scheduleAPIMapper.toDto(request);

        var scheduleDtoSaved = scheduleUpdateService.update(code, scheduleDto);

        var response = bindResponseWithHateoasLink(scheduleDtoSaved);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update a Schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success update a Schedule to open a voting",
                    content = { @Content(mediaType = APPLICATION_VND_SICRED_APP_V_1_JSON,
                            schema = @Schema(implementation = ScheduleResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "schedule not found", content = @Content)})
    @PutMapping(value = "/{code}/open", produces = APPLICATION_VND_SICRED_APP_V_1_JSON)
    public ResponseEntity<ScheduleResponse> openVoting(@PathVariable final String code,
                                                       @Valid @RequestBody(required = false) final ExpiresTimeRequest request,
                                                       final Errors errors) {

        var scheduleDtoSaved = scheduleUpdateService
                .updateSetExpiration(code, Objects.nonNull(request) ? request.getExpiresTimeInMinutes(): null);

        var response = bindResponseWithHateoasLink(scheduleDtoSaved);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Create a Schedule")
    @ApiResponses(value = { @ApiResponse(responseCode = "404", description = "not found", content = @Content)})
    @GetMapping(value = "/{code}", produces = APPLICATION_VND_SICRED_APP_V_1_JSON)
    public ResponseEntity<ScheduleResponse> findByCode(@PathVariable final String code) {

        var scheduleDto = scheduleFindService.findByCode(code);

        var response = bindResponseWithHateoasLink(scheduleDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "find all Schedules")
    @GetMapping(produces = APPLICATION_VND_SICRED_APP_V_1_JSON)
    public CollectionModel<ScheduleResponse> findAllSchedules() {
        var responses = scheduleFindService.findAll().stream()
                .map(this::bindResponseWithHateoasLink)
                .collect(Collectors.toList());
        final Link link = linkTo(ScheduleResource.class).withSelfRel();
        return new CollectionModel<>(responses, link);
    }

    private ScheduleResponse bindResponseWithHateoasLink(final ScheduleDto scheduleDtoSaved) {
        var response = scheduleAPIMapper.toResponse(scheduleDtoSaved);
        var link = linkTo(ScheduleResource.class).slash(response.getCode()).withSelfRel();
        response.add(link);
        return response;
    }
}
