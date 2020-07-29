package br.com.dbccompany.api.app;

import br.com.dbccompany.api.resource.response.v1.ExceptionResponse;
import br.com.dbccompany.api.resource.response.v1.ExceptionResponseList;
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
import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
}