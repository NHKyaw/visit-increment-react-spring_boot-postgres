package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Configuration
public class DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Value("${spring.datasource.url}")
    private String rootUrl;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String pass;

    @Value("${app.database.name}")
    private String dbName;

    @PostConstruct
    public void init() {
        try (Connection conn = DriverManager.getConnection(rootUrl, user, pass);
             Statement stmt = conn.createStatement()) {

            String createDbSql = "CREATE DATABASE " + dbName;
            stmt.executeUpdate(createDbSql);
            logger.info("Database '{}' created successfully.", dbName);

        } catch (Exception e) {
            logger.warn("Database already exists or error: {}", e.getMessage());
        }
    }
}
