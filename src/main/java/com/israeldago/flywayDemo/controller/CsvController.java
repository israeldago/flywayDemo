package com.israeldago.flywayDemo.controller;

import com.israeldago.flywayDemo.entities.CarBIS;
import com.israeldago.flywayDemo.service.LoadCSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/load")
public class CsvController {
    @Autowired
    private LoadCSVService service;

    @GetMapping("/csv")
    public List<CarBIS> loadData(){
        try {
            service.loadData();
            return service.findAll().collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
