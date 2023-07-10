package com.linked.matched.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")      //패턴
                .allowedOriginPatterns("*")    //URL
                .allowCredentials(true) // 보안과 관련
                .allowedHeaders("Origin, X-Requested-With, Content-Type, Accept, Authorization")  //header
                .allowedMethods("GET", "POST", "PATCH", "DELETE","OPTIONS");     //method
    }
}
