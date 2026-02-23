package com.akhona.sentinel.fraud.controller;

import com.akhona.sentinel.fraud.repository.TransactionRepository;
import com.akhona.sentinel.fraud.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionRepository repository;
    private final FraudEngineService fraudEngine;

    public TransactionController(TransactionRepository repository, FraudEngineService fraudEngine) {
        this.repository = repository;
        this.fraudEngine = fraudEngine;
    }
}
