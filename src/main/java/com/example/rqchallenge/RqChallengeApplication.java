package com.example.rqchallenge;

import com.example.rqchallenge.utils.CustomRestTemplateCustomizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RqChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RqChallengeApplication.class, args);
    }

    @Bean
    public CustomRestTemplateCustomizer customRestTemplateCustomizer() {
        return new CustomRestTemplateCustomizer();
    }
}
