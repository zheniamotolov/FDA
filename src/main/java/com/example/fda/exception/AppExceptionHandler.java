package com.example.fda.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {
    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ApiErrorResponse> handleRestClientException(RestClientException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse("Error fetching data from external API", errors);

        log.error("RestClientException: {}", ex.getMessage(), ex);

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpClientErrorException(HttpClientErrorException ex) {
        List<String> errors = Collections.singletonList(ex.getResponseBodyAsString());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse("Error fetching data from external API " + ex.getMessage(), errors);

        log.error("HttpClientErrorException: Status code: {}, Message: {}", ex.getStatusCode(), ex.getMessage(), ex);

        return new ResponseEntity<>(apiErrorResponse, ex.getStatusCode());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                "Request method '" + ex.getMethod() + "' not supported");

        log.error("HttpRequestMethodNotSupportedException: Supported methods: {}, Message: {}",
                ex.getSupportedHttpMethods(), ex.getMessage(), ex);

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                "Request method '" + ex.getResourcePath() + "' not supported");

        log.error("NoResourceFoundException: {}", ex.getMessage(), ex);

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(Exception ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse("Server error");

        log.error("Exception: {}", ex.getMessage(), ex);

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex) {

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage());

        log.error("DataIntegrityViolationException: {}", ex.getMessage());

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + ": " + x.getDefaultMessage())
                .collect(Collectors.toList());

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse("Validation error", errors);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
