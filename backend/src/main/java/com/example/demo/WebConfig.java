package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public final class WebConfig implements WebMvcConfigurer {

    /**
     * Adds CORS mappings.
     *
     * @param registry the cors registry
     */
    @Override
    public final void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOriginPatterns("*")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*");
    }
}
