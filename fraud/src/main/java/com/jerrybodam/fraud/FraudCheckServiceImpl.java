package com.jerrybodam.fraud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FraudCheckServiceImpl implements FraudCheckService {

    public FraudCheckHistoryRepository fraudCheckHistoryRepository;

    @Autowired
    public FraudCheckServiceImpl(FraudCheckHistoryRepository fraudCheckHistoryRepository) {
        this.fraudCheckHistoryRepository = fraudCheckHistoryRepository;
    }

    public boolean isFraudulentCustomer(Integer customerId) {
        fraudCheckHistoryRepository.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .createdAt(LocalDateTime.now())
                        .isFraudster(false)
                        .build()
        );
        return false;
    }

    public String approve(Integer customerId) {
        fraudCheckHistoryRepository.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .isFraudster(true)
                        .build()
        );

        Optional<FraudCheckHistory> fraudResponse = fraudCheckHistoryRepository.findById(customerId);
        if(fraudResponse.isEmpty()) {
            return "Client does not exist";
        } else {
            return "Client Approved Successfully";
        }
       // return FraudCheckHistoryRepository.findById(customerId);
    }
}
