package com.gov.mvc.randomnumbergenerator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gov.mvc.randomnumbergenerator.controller.request.RandomGenRequest;
import com.gov.mvc.randomnumbergenerator.controller.response.RandomGenResponse;
import com.gov.mvc.randomnumbergenerator.service.RandomNumberGeneratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/random/number/generator")
@Slf4j
@RequiredArgsConstructor
@Validated
public class RandomNumberGeneratorController {

    private final RandomNumberGeneratorService randomNumberGeneratorService;
    private final ObjectMapper objectMapper;
    @PostMapping
    public RandomGenResponse generateNumbers(@RequestBody @Valid RandomGenRequest randomGenRequest) throws JsonProcessingException {
        log.info("Generating Random Number with given details {}", objectMapper.writeValueAsString(randomGenRequest));
        final String numberGenerateStatus = randomNumberGeneratorService.generateRandomNumbersExcel(randomGenRequest);
        return RandomGenResponse.builder()
                .message(numberGenerateStatus)
                .outputFilePath(randomGenRequest.getExcelOutFilePath())
                .timestamp(Instant.now())
                .build();
    }

    @GetMapping("/health")
    public String serviceHealth() {
        log.info("Inside serviceHealth method of class RandomNumberGeneratorController");
        return "RandomNumberGenerator is up and running";
    }

}
