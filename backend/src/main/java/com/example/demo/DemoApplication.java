package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public final class DemoApplication {

    private DemoApplication() {
        // Private constructor to hide the implicit public one
    }

    /**
     * Main method to run the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
