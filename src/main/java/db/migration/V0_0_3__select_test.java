package db.migration;

import com.israeldago.flywayDemo.entities.Car;
import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

public class V0_0_3__select_test implements SpringJdbcMigration {
    @Override
    public void migrate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.query("select * from cars",
                (rs, i) -> new Car(rs.getInt("id"), rs.getString("brand"), rs.getString("color")))
                .forEach(System.out::println);
    }
}
