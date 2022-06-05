package com.jerrybodam.customer.service.seviceimpl;

import com.jerrybodam.clients.fraud.FraudCheckResponse;
import com.jerrybodam.clients.fraud.FraudClient;
import com.jerrybodam.clients.notification.NotificationClient;
import com.jerrybodam.clients.notification.NotificationRequest;
import com.jerrybodam.customer.dto.CustomerRegReq;
import com.jerrybodam.customer.model.Customer;
import com.jerrybodam.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerServiceImpl {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, NotificationClient notificationClient,
                               FraudClient fraudClient) {
        this.customerRepository = customerRepository;
        this.notificationClient = notificationClient;
        this.fraudClient = fraudClient;
    }

    public void registerCustomer(CustomerRegReq customerRegReq) {
        Customer customer = Customer.builder()
                .firstName(customerRegReq.firstName())
                .lastName(customerRegReq.lastName())
                .email(customerRegReq.email())
                .build();

        customerRepository.saveAndFlush(customer);

        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, welcome to Amigoscode...",
                                customer.getFirstName())
                )
        );

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
            //Alternatively
//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://FRAUD/api/v1/fraud-check/{customer_id}",
//                FraudCheckResponse.class,
//                customer.getId()
//                );

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster");
        }
        //todo: validate email, check if email is taken
        //todo: check if fraudster
        //todo: send notifiction
    }
}
