package com.jerrybodam.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {
                "com.jerrybodam.customer",
                "com.jerrybodam.amqp",
        }
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.jerrybodam.clients"
)
public class CustomerApp {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApp.class, args);
    }
}
