package com.example.server;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    private ServiceManager serviceManager;

    @PostMapping("save")
    public void saveData(@RequestBody(required = false) Map<String, Object> requestData) throws FileNotFoundException {
        System.out.printf("something");
        // extract the content form the url
        String url = (String) requestData.get("url");
        CSVReader csvReader = serviceManager.getCSV(url);

        try {
            serviceManager.saveCSV(csvReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("getRate")
    public String getRate(@RequestParam("fromCurrency") String fromCurrency,@RequestParam("toCurrency") String toCurrency, @RequestParam("date") Date date) {
        return "";
    }



}
