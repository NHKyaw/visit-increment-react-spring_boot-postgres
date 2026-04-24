package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/** Integration test to verify the Spring application context loads. */
@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:postgresql://localhost:5432/my_database",
    "spring.datasource.username=postgres",
    "spring.datasource.password=${DB_PASSWORD:testpassword}",
    "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect",
    "app.database.name=my_database"
})
class DemoApplicationTests {

    /** Verifies that the Spring application context loads successfully. */
    @Test
    void contextLoads() {
    }
}
