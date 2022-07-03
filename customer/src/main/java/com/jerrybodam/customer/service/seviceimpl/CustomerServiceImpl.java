package com.jerrybodam.customer.service.seviceimpl;

import com.jerrybodam.amqp.RabbitMQMessageProducer;
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
    private final RabbitMQMessageProducer producer;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, NotificationClient notificationClient,
                               FraudClient fraudClient, RabbitMQMessageProducer producer) {
        this.customerRepository = customerRepository;
        this.notificationClient = notificationClient;
        this.fraudClient = fraudClient;
        this.producer = producer;
    }

    public void registerCustomer(CustomerRegReq customerRegReq) {
        Customer customer = Customer.builder()
                .firstName(customerRegReq.firstName())
                .lastName(customerRegReq.lastName())
                .email(customerRegReq.email())
                .build();

        customerRepository.saveAndFlush(customer);
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster");
        }

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to Amigoscode...",
                        customer.getFirstName())
        );
        producer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );



    }
}
