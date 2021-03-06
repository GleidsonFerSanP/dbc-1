package br.com.dbccompany.api.app;

import br.com.dbccompany.api.resource.response.v1.ExceptionResponse;
import br.com.dbccompany.api.resource.response.v1.ExceptionResponseList;
import br.com.dbccompany.core.exception.InvalidCodeException;
import br.com.dbccompany.core.exception.InvalidExpirationTimeException;
import br.com.dbccompany.core.exception.NotFoundException;
import br.com.dbccompany.core.exception.OptionVoteInvalidException;
import br.com.dbccompany.core.exception.ScheduleExpiredException;
import br.com.dbccompany.core.exception.ScheduleNotOpenException;
import br.com.dbccompany.core.exception.UserNotFoundException;
import br.com.dbccompany.core.exception.VoteAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@RestController
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponseList handleConstraintViolationException(final ConstraintViolationException e, final WebRequest request) {
        log.info("I=validation error, message={}", e.getMessage());
        final List<ExceptionResponse> errors = e.getConstraintViolations()
                .stream()
                .map(violation -> buildResponseError(e, BAD_REQUEST, request, buildConstraintMessage(violation)))
                .collect(toList());
        return new ExceptionResponseList(errors);
    }

    @ExceptionHandler({ NotFoundException.class, UserNotFoundException.class })
    @ResponseStatus(NOT_FOUND)
    public ExceptionResponse handle(final RuntimeException e, final WebRequest request) {
        final HttpStatus httpStatus = NOT_FOUND;
        logException("handle NotFoundException", httpStatus, e);
        return  buildResponseError(e, httpStatus, request);
    }

    @ExceptionHandler({
            InvalidCodeException.class,
            InvalidExpirationTimeException.class,
            OptionVoteInvalidException.class,
            ScheduleNotOpenException.class,
            ScheduleExpiredException.class
    })
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handleBadRequest(final RuntimeException e, final WebRequest request) {
        final HttpStatus httpStatus = BAD_REQUEST;
        logException("handleBadRequest", httpStatus, e);
        return  buildResponseError(e, httpStatus, request);
    }

    @ExceptionHandler({VoteAlreadyExistsException.class})
    @ResponseStatus(CONFLICT)
    public ExceptionResponse handleConflict(final RuntimeException e, final WebRequest request) {
        final HttpStatus httpStatus = CONFLICT;
        logException("handleConflict", httpStatus, e);
        return  buildResponseError(e, httpStatus, request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleInternalError(final Exception e, final WebRequest request) {
        final HttpStatus httpStatus = INTERNAL_SERVER_ERROR;
        logException("handleInternalError", httpStatus, e);
        return  buildResponseError(e, httpStatus, request);
    }

    private String buildConstraintMessage(final ConstraintViolation<?> violation) {
        var pathField =  violation.getPropertyPath().toString();
        var pathFieldArray = pathField.split("\\.");
        return violation.getMessage().concat(" [").concat(pathFieldArray[(pathFieldArray.length - 1)]).concat("]");
    }

    private ExceptionResponse buildResponseError(final Throwable e, final HttpStatus httpStatus, final WebRequest request) {
        return buildResponseError(e, httpStatus, request, e.getMessage());
    }

    private ExceptionResponse buildResponseError(final Throwable e, final HttpStatus httpStatus, final WebRequest request, final String message) {
        return ExceptionResponse.builder()
                .error(e.getClass().getSimpleName())
                .message(message)
                .httpStatus(httpStatus.value())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
    }

    private static void logException(final String methodName, final HttpStatus httpStatus, final Exception e) {
        log.warn("M={} status={} message={}", methodName, httpStatus.value(), e.getMessage());
    }
}
