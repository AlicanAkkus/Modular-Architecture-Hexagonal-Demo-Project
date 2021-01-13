package com.hexagonaldemo.ticketapi.adapters.payment.rest.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConditionalOnProperty(name = "adapters.payment.enabled", havingValue = "true")
@ConfigurationProperties(prefix = "adapters.payment")
public class PaymentApiProperties {

    private String baseUrl;
    private String paymentPath;
}
