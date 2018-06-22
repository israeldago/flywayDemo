package db.migration;

import com.israeldago.flywayDemo.entities.Car;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Component

public class V0_0_5__Read_CSV implements SpringJdbcMigration {

    private String obtainAndProcessData(Clob clob) {
        try {
            return clob.getSubString(1, (int) clob.length());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
        Path dataUri = getFilePath();
        Reader reader = Files.newBufferedReader(dataUri);
        CsvToBean<Car> csvToBean = new CsvToBeanBuilder<Car>(reader)
                .withType(Car.class)
                .withIgnoreLeadingWhiteSpace(true)
                .withSeparator("#".charAt(0))
                .build();

        csvToBean.iterator().forEachRemaining(carCSV -> {
            String json = String.format("{\"brand\":\"%s\",\"color\":\"%s\"}", carCSV.getBrand(), carCSV.getColor());
            final boolean noneMatch = jdbcTemplate.query("select * from cars", (rs, index) -> new Car(rs.getInt("id"), rs.getString("brand"), rs.getString("color")))
                                                    .stream()
                                                    .noneMatch(car -> car.getId().equals(carCSV.getId()));
            if(noneMatch){
                jdbcTemplate.update("insert into cars (id,brand,color,custom) values(?,?,?,?)",
                        carCSV.getId(), carCSV.getBrand(), carCSV.getColor(), json);
            }else {
                jdbcTemplate.update("update cars set brand=?, color=? , custom=? where id=?",
                        carCSV.getBrand(), carCSV.getColor(), json, carCSV.getId());
            }
        });
        fetchAllCars(jdbcTemplate).forEach(System.out::println);
    }

    private Path getFilePath() throws IOException, URISyntaxException {
        return Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource("carData.csv"),
                "cannot find the CSV resource file at given location").toURI());
    }

    private List<Car> fetchAllCars(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.query("select * from cars", (rs, i) -> {
            Car car = new Car();
            car.setId(rs.getInt("id"));
            car.setBrand(rs.getString("brand"));
            car.setColor(rs.getString("color"));
            String customData = Optional.ofNullable(rs.getClob("custom"))
                                            .map(this::obtainAndProcessData)
                                            .orElse("");
            car.setCustom(customData);
            return car;
        });
    }

}
