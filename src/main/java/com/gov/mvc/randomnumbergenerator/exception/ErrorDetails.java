package com.gov.mvc.randomnumbergenerator.exception;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class ErrorDetails implements Serializable {
    private Integer code;
    private String errorMessage;
    private Instant timestamp;
}
