package com.jerrybodam.customer.service.seviceimpl;

import com.jerrybodam.customer.CustomerConfig;
import com.jerrybodam.customer.dto.CustomerRegReq;
import com.jerrybodam.customer.dto.FraudCheckResponse;
import com.jerrybodam.customer.model.Customer;
import com.jerrybodam.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerServiceImpl {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, RestTemplate restTemplate) {
        this.customerRepository = customerRepository;
        this.restTemplate = restTemplate;
    }

    public void registerCustomer(CustomerRegReq customerRegReq) {
        Customer customer = Customer.builder()
                .firstName(customerRegReq.firstName())
                .lastName(customerRegReq.lastName())
                .email(customerRegReq.email())
                .build();

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://FRAUD/api/v1/fraud-check/{customer_id}",
                FraudCheckResponse.class,
                customer.getId()
                );

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster");
        }
        //todo: validate email, check if email is taken
        //todo: check if fraudster
        //todo: send notifiction
    }
}
