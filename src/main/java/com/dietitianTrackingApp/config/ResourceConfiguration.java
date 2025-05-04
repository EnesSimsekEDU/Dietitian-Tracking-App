package com.dietitianTrackingApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map the old META-INF/resources/assets path to the new webapp/css path
        registry.addResourceHandler("/resources/assets/**")
                .addResourceLocations("/resources/assets/");

        // Add mapping for JSF resources
        registry.addResourceHandler("/jakarta.faces.resource/**")
                .addResourceLocations("/resources/", "classpath:/META-INF/resources/");
    }
}
