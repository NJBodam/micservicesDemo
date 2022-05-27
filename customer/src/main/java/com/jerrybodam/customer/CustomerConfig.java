package com.jerrybodam.customer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomerConfig {

    // To send an http request to the other microservice
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}