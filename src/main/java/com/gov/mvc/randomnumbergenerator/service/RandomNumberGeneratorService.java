package com.gov.mvc.randomnumbergenerator.service;

import com.gov.mvc.randomnumbergenerator.controller.request.RandomGenRequest;

public interface RandomNumberGeneratorService {
    String generateRandomNumbersExcel(RandomGenRequest randomGenRequest);
}
