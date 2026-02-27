package com.akhona.sentinel.fraud.controller;

import com.akhona.sentinel.fraud.dto.FraudResponse;
import com.akhona.sentinel.fraud.dto.TransactionRequest;
import com.akhona.sentinel.fraud.service.FraudEngineService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final FraudEngineService fraudEngine;

    public TransactionController(FraudEngineService fraudEngine) {
        this.fraudEngine = fraudEngine;
    }

    @PostMapping
    public ResponseEntity<FraudResponse> analyze(@Valid @RequestBody TransactionRequest request) {
        FraudResponse response = fraudEngine.assess(request);
        return ResponseEntity.ok(response);
    }
}
