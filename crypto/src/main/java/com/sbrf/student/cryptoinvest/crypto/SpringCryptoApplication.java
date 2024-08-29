package com.sbrf.student.cryptoinvest.crypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, KafkaAutoConfiguration.class})
public class SpringCryptoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCryptoApplication.class, args);
    }
}
