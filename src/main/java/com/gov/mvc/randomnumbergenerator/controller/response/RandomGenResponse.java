package com.gov.mvc.randomnumbergenerator.controller.response;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class RandomGenResponse implements Serializable {
    private String outputFilePath;
    private String message;
    private Instant timestamp;
}
