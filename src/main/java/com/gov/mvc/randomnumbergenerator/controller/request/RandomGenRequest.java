package com.gov.mvc.randomnumbergenerator.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class RandomGenRequest implements Serializable {

    @NotNull(message = "totalRandomNumber is a required attribute")
    @Min(value = 1, message = "totalRandomNumber must be at least 1")
    @Max(value = 1000000, message = "totalRandomNumber must be at most 1000000")
    private Long totalRandomNumber;

    @NotNull(message = "randomNumberPerSheet is a required attribute")
    @Min(value = 1, message = "randomNumberPerSheet must be at least 1")
    @Max(value = 100000, message = "randomNumberPerSheet must be at most 100000")
    private Long randomNumberPerSheet;

    @NotNull(message = "excelOutFilePath is a required attribute")
    @NotBlank(message = "excelOutFilePath must not be empty or blank. It should be a valid system path to store the Excel file including the file name. For example, C:/Users/amsid/Documents/IntellijProjects/random-number-generator/output.xlsx")
    private String excelOutFilePath;
}
