package com.sbrf.student.cryptoinvest.crypto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
