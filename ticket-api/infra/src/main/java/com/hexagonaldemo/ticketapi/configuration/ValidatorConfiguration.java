package com.hexagonaldemo.ticketapi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ValidatorConfiguration implements WebMvcConfigurer {

    private final Validator defaultValidator;

    public ValidatorConfiguration(Validator defaultValidator) {
        this.defaultValidator = defaultValidator;
    }

    @Override
    public Validator getValidator() {
        return defaultValidator;
    }
}