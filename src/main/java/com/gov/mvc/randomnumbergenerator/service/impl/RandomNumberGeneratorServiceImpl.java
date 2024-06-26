package com.gov.mvc.randomnumbergenerator.service.impl;

import com.gov.mvc.randomnumbergenerator.controller.request.RandomGenRequest;
import com.gov.mvc.randomnumbergenerator.service.RandomNumberGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

@RequiredArgsConstructor
@Service
@Slf4j
public class RandomNumberGeneratorServiceImpl implements RandomNumberGeneratorService {


    @Override
    public String generateRandomNumbersExcel(RandomGenRequest randomGenRequest) {
        Workbook workbook = new SXSSFWorkbook(); // Use SXSSFWorkbook for large data sets
        log.info("***************************START- Generating random numbers***************************");

        // Generate random numbers stream
        Random random = ThreadLocalRandom.current();
        long totalRandomNumber = randomGenRequest.getTotalRandomNumber();
        long numberPerSheet = randomGenRequest.getRandomNumberPerSheet();
        long sheetCount = (totalRandomNumber + numberPerSheet - 1) / numberPerSheet; // calculate the number of sheets required

        log.info("***************************END- Generating random numbers***************************");
        log.info("***************************START- Preparing excel sheet***************************");

        // Initialize sheets
        for (long sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
            workbook.createSheet("Sheet" + (sheetIndex + 1));
        }

        Set<Long> uniqueNumbers = new HashSet<>();
        LongStream.generate(() -> generateRandom10DigitNumber(random))
                .distinct()
                .limit(totalRandomNumber)
                .forEach(randomNumber -> {
                    synchronized (workbook) {
                        long index = uniqueNumbers.size();
                        uniqueNumbers.add(randomNumber);
                        long sheetIndex = index / numberPerSheet;
                        long rowIndex = index % numberPerSheet;
                        Sheet sheet = workbook.getSheetAt((int) sheetIndex);
                        Row row = sheet.createRow((int) rowIndex);
                        row.createCell(0).setCellValue(randomNumber);
                    }
                });

        log.info("***************************END- Preparing excel sheet***************************");
        log.info("***************************START- Writing excel sheet***************************");

        try (FileOutputStream fileOut = new FileOutputStream(randomGenRequest.getExcelOutFilePath())) {
            workbook.write(fileOut);
            log.info("***************************END- Writing excel sheet***************************");
            return String.format("Excel file generated at path %s successfully", randomGenRequest.getExcelOutFilePath());
        } catch (IOException e) {
            log.error("IO Exception occurred {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                log.error("Exception occurred in finally block {}", e.getMessage(), e);
            }
        }
    }

    private long generateRandom10DigitNumber(Random random) {
        long min = 1_000_000_000L;
        long max = 9_999_999_999L;
        return min + (long) (random.nextDouble() * (max - min));
    }
}
