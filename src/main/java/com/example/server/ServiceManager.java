package com.example.server;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class ServiceManager {
    @Value("${filename}")
    private String filename;
    @Value("${directory}")
    private String directoryPath;

    public CSVReader getCSV(String url) throws FileNotFoundException {
        try {
            InputStream inputStream = new URL(url).openStream();
            return new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCSV(CSVReader csvReader) throws IOException, CsvException {
//        List<String[]> records = csvReader.readAll();
        File directory = new File(directoryPath);
        File existingCSVFile = null;
        if (!directory.exists()) {
            directory.mkdirs();
        } else {
            existingCSVFile = new File(directory, "mergedData.csv");
        }


        File mergedCSVFile = mergeCSVFile(existingCSVFile, csvReader);


    }

    private File mergeCSVFile(File existingCSVFile, CSVReader csvReader) {
        try {
            if (csvReader == null) {
                return existingCSVFile;
            }
            HashSet<String[]> uniqueRows = new HashSet(0);
            CSVReader existingCSVReader = new CSVReader(new FileReader(existingCSVFile)) ;


            uniqueRows.addAll(existingCSVReader.readAll());
            uniqueRows.addAll(csvReader.readAll());

            List<String[]> uniqueList = new ArrayList<>();
            uniqueList.addAll(uniqueRows);
            File mergedCSV = new File(directoryPath,"mergedData.csv");
            try (CSVWriter writer = new CSVWriter(new FileWriter(mergedCSV))) {
                writer.writeAll(uniqueList);
            }

            return mergedCSV;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
