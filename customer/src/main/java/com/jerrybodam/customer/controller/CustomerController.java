package com.jerrybodam.customer.controller;

import com.jerrybodam.customer.dto.CustomerRegReq;
import com.jerrybodam.customer.service.seviceimpl.CustomerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @Autowired
    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegReq customerRegReq) {
        log.info("new customer created {}", customerRegReq);
        customerService.registerCustomer(customerRegReq);
    }
}
