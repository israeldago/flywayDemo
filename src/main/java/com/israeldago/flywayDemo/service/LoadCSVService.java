package com.israeldago.flywayDemo.service;

import com.israeldago.flywayDemo.entities.CarBIS;
import com.israeldago.flywayDemo.repository.CarREpository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@Service @Transactional
public class LoadCSVService {
    @Autowired
    private CarREpository repository;

    public Stream<CarBIS> findAll(){
        return repository.findAll().stream();
    }

    public void loadData() throws IOException, URISyntaxException {
        Path dataUri = getCSVLocationPath();
        Reader reader = Files.newBufferedReader(dataUri);
        CsvToBean<CarBIS> csvToBean = new CsvToBeanBuilder<CarBIS>(reader)
                .withType(CarBIS.class)
                .withIgnoreLeadingWhiteSpace(true)
                .withSeparator("#".charAt(0))
                .build();

        csvToBean.iterator().forEachRemaining(carCSV -> {
            String json = String.format("{\"brand\":\"%s\",\"color\":\"%s\"}", carCSV.getBrand(), carCSV.getColor());
            CarBIS carBIS = new CarBIS();
            carBIS.setId(carCSV.getId());
            carBIS.setBrand(carCSV.getBrand());
            carBIS.setColor(carCSV.getColor());

            carBIS.setCustom(json);
            repository.saveAndFlush(carBIS);
        });
    }

    private Path getCSVLocationPath() throws IOException, URISyntaxException {
        return Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource("carData.csv"),
                "cannot find the CSV resource file at given location").toURI());
    }
}
