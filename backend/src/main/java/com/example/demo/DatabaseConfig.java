package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/** Configuration class to initialize the database if it does not exist. */
@Configuration(proxyBeanMethods = false)
public final class DatabaseConfig {

    /** Logger for this class. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(DatabaseConfig.class);

    /** The root datasource URL. */
    @Value("${spring.datasource.url}")
    private String rootUrl;

    /** The database username. */
    @Value("${spring.datasource.username}")
    private String user;

    /** The database password. */
    @Value("${spring.datasource.password}")
    private String pass;

    /** The name of the database to create. */
    @Value("${app.database.name}")
    private String dbName;

    /**
     * Initializes the database on application startup.
     * Creates the database if it does not already exist.
     */
    @PostConstruct
    public void init() {
        if (!dbName.matches("\\w+")) {
            LOGGER.warn(
                "Invalid database name '{}'. Skipping creation.", dbName
            );
            return;
        }
        try (Connection conn = DriverManager.getConnection(rootUrl, user, pass);
             Statement stmt = conn.createStatement()) {

            // Suppressed: dbName is validated above
            @SuppressWarnings("java:S2077")
            String createDbSql = "CREATE DATABASE " + dbName;
            stmt.executeUpdate(createDbSql);
            LOGGER.info("Database '{}' created successfully.", dbName);

        } catch (Exception e) {
            LOGGER.warn(
                "Database already exists or error: {}", e.getMessage()
            );
        }
    }
}
