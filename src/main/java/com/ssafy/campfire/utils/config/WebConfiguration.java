package com.ssafy.campfire.utils.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://i9a408.p.ssafy.io:3000")
                // .allowedOrigins("*")
                .allowedMethods("GET", "POST","DELETE","PUT","FETCH")
            .allowCredentials(true);
    }

}
