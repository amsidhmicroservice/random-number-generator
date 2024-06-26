package com.gov.mvc.randomnumbergenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class RandomNumberGeneratorApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RandomNumberGeneratorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        String excelOutPath = System.getProperty("user.dir", "C:") + "/random_num.xlsx";
        log.info("Out of generated file is stored in file {}", excelOutPath);
        generateRandomNumbersExcel(1000000, 10000, excelOutPath);
    }

    public void generateRandomNumbersExcel(long totalRandomNumber, long numberPerSheet, String excelOutFilePath) {
        Workbook workbook = new SXSSFWorkbook(); // Use SXSSFWorkbook for large data sets
        log.info("***************************START- Generating random numbers***************************");

        // Generate random numbers stream
        Random random = ThreadLocalRandom.current();
        long sheetCount = (totalRandomNumber + numberPerSheet - 1) / numberPerSheet; // calculate the number of sheets required

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

        try (FileOutputStream fileOut = new FileOutputStream(excelOutFilePath)) {
            workbook.write(fileOut);
            log.info("Excel file generated at path {} successfully", excelOutFilePath);
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
