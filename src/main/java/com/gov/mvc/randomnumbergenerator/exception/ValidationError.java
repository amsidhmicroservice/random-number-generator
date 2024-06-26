package com.gov.mvc.randomnumbergenerator.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationError implements Serializable {
    private String field;
    private String message;
}
