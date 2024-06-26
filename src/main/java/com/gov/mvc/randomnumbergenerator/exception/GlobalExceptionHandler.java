package com.gov.mvc.randomnumbergenerator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({RandomNumberGenerateException.class})
    public ErrorDetails handleRandomNumberGenerateException(RandomNumberGenerateException randomNumberGenerateException) {
        return ErrorDetails.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(randomNumberGenerateException.getMessage())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ValidationErrorDetails handleValidationException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<ValidationError> validationErrors = fieldErrors.stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return ValidationErrorDetails.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .errors(validationErrors)
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler({Exception.class})
    public ErrorDetails handleException(Exception exception) {
        return ErrorDetails.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(exception.getMessage())
                .timestamp(Instant.now())
                .build();
    }
}
