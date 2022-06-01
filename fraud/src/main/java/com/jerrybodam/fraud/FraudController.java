package com.jerrybodam.fraud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/fraud-check")
public class FraudController {

    private final FraudCheckServiceImpl fraudCheckServiceImpl;

    public FraudController(FraudCheckServiceImpl fraudCheckServiceImpl) {
        this.fraudCheckServiceImpl = fraudCheckServiceImpl;
    }

    @GetMapping(path = "/{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId) {
        boolean isFraudulentCustomer = fraudCheckServiceImpl
                .isFraudulentCustomer(customerId);
        log.info("Approval request for customer {}", customerId);
        return new FraudCheckResponse(isFraudulentCustomer);
    }

    @PutMapping("/approve/{customerId}")
    public ResponseEntity<String> giveApproval(@PathVariable("customerId") Integer customerId) {
        log.info("approval made for customer {}", customerId);
        return ResponseEntity.ok().body(fraudCheckServiceImpl.approve(customerId));
    }


}
