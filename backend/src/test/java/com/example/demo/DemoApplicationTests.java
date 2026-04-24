package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:postgresql://localhost:5432/my_database",
    "spring.datasource.username=postgres",
    "spring.datasource.password=${DB_PASSWORD:testpassword}",
    "app.database.name=my_database"
})
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }
}
