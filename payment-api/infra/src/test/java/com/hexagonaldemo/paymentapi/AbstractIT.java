package com.hexagonaldemo.paymentapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

public abstract class AbstractIT {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @LocalServerPort
    protected Integer port;
}