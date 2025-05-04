package com.dietitianTrackingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.dietitianTrackingApp")
@EnableJpaRepositories(basePackages = "com.dietitianTrackingApp.repository")
@EntityScan(basePackages = "com.dietitianTrackingApp.entity")
public class DietitianTrackingApp
{
    public static void main(String[] args) {
        SpringApplication.run(DietitianTrackingApp.class, args);
    }

}
