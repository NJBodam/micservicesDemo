package com.jerrybodam.customer;

import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository) {
    public void registerCustomer(CustomerRegReq customerRegReq) {
        Customer customer = Customer.builder()
                .firstName(customerRegReq.firstName())
                .lastName(customerRegReq.lastName())
                .email(customerRegReq.email())
                .build();

        customerRepository.save(customer);

        //todo: validate email, check if email is taken
    }
}
