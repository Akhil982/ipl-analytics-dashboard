package com.am.ipl.analytics.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // All endpoints in the API
                .allowedOriginPatterns("*") // Allows any origin (replaces allowedOrigins for better compatibility)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*") // Allows all headers (Authorization, Content-Type, etc.)
                .allowCredentials(true); // Required if you use Cookies or Auth headers
    }
}
