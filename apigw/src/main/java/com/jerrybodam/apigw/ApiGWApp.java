package com.jerrybodam.apigw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ApiGWApp {
    public static void main(String[] args) {
        SpringApplication.run(ApiGWApp.class, args);
    }
}
