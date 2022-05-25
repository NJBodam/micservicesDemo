package com.jerrybodam.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
public record CustomerController(CustomerService customerService) {

    @PostMapping
    public void registerCuatomer(@RequestBody CustomerRegReq customerRegReq) {
        log.info("new customer created {}", customerRegReq);
        customerService.registerCustomer(customerRegReq);
    }
}
