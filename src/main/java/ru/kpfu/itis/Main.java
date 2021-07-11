package ru.kpfu.itis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.kpfu.itis.services.CoursesRepository;
import ru.kpfu.itis.services.CoursesRepositoryJdbcTemplateImpl;
import ru.kpfu.itis.services.LessonsRepository;
import ru.kpfu.itis.services.LessonsRepositoryJdbcTemplateImpl;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Properties properties = new Properties();

        try {
            properties.load(ClassLoader.getSystemResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(properties.getProperty("db.driver"));
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.user"));
        config.setPassword(properties.getProperty("db.password"));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.pool-size")));

        DataSource dataSource = new HikariDataSource(config);

        CoursesRepository coursesRepository = new CoursesRepositoryJdbcTemplateImpl(dataSource);

        LessonsRepository lessonsRepository = new LessonsRepositoryJdbcTemplateImpl(dataSource);
    }
}
