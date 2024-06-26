package com.gov.mvc.randomnumbergenerator.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationErrorDetails implements Serializable {
    private Integer code;
    private List<ValidationError> errors;
    private Instant timestamp;
}

