package com.sbrf.student.cryptoinvest.asset.config;

import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfig {
    @Bean
    RestTemplate restTemplate() { return new RestTemplate();}
}
