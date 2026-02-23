package com.akhona.sentinel.fraud.controller;

import com.akhona.sentinel.fraud.model.FraudDecision;
import com.akhona.sentinel.fraud.model.Transaction;
import com.akhona.sentinel.fraud.repository.TransactionRepository;
import com.akhona.sentinel.fraud.service.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionRepository repository;
    private final FraudEngineService fraudEngine;

    public TransactionController(TransactionRepository repository, FraudEngineService fraudEngine) {
        this.repository = repository;
        this.fraudEngine = fraudEngine;
    }

    @PostMapping
    public FraudDecision create(@Valid @RequestBody Transaction transaction) {
        transaction.setTimestamp(LocalDateTime.now());
        Transaction result = repository.save(transaction);
        return fraudEngine.assess(result);
    }
}
