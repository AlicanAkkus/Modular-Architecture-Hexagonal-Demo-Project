package com.hexagonaldemo.paymentapi;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("contractTest")
@Import(TestConfig.class)
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}