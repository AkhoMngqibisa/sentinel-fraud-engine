package com.akhona.sentinel.fraud.service;

import com.akhona.sentinel.fraud.exception.BusinessException;
import com.akhona.sentinel.fraud.messaging.TransactionProducer;
import com.akhona.sentinel.fraud.model.*;
import com.akhona.sentinel.fraud.repository.*;
import com.akhona.sentinel.fraud.rule.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FraudEngineService {

    private final RuleRegistry ruleRegistry;
    private final FraudDecisionRepository decisionRepository;
    private final TransactionProducer transactionProducer;
    private static final Logger log = LoggerFactory.getLogger(FraudEngineService.class);

    public FraudEngineService(RuleRegistry ruleRegistry, FraudDecisionRepository decisionRepository, TransactionProducer transactionProducer) {
        this.ruleRegistry = ruleRegistry;
        this.decisionRepository = decisionRepository;
        this.transactionProducer = transactionProducer;
    }

    public FraudDecision assess(Transaction transaction) {
        int totalScore = 0;
        List<String> triggered = new ArrayList<>();

        if(transaction.getAmount().compareTo(BigDecimal.valueOf(0)) <= 0){
            throw new BusinessException("Invalid transaction", List.of("Amount must be greater than 0"));
        }

        log.info("Assessing transaction {}", transaction.getId());

        // 1. Evaluate
        for (FraudRule rule : ruleRegistry.getRules()) {
            // Get Transaction rule results
            RuleResult result = rule.check(transaction);

            if (result.isTriggered()) {
                totalScore += result.getScore();
                triggered.add(rule.getRuleName());
            }
        }

        // 2. Log evaluation
        boolean flagged = totalScore >= 70;
        FraudDecision fraudDecision = FraudDecision.builder()
                .transactionId(transaction.getId())
                .fraudScore(totalScore)
                .flagged(flagged)
                .triggeredRules(triggered)
                .createdAt(LocalDateTime.now())
                .build();

        // 3. Publish event
        transactionProducer.send(transaction);
        log.info("Fraud result: score={}, flagged={}, rules={}", totalScore, flagged, triggered);
        return decisionRepository.save(fraudDecision);
    }
}
