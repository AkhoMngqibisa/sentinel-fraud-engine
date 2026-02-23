package com.akhona.sentinel.fraud.service;

import com.akhona.sentinel.fraud.model.*;
import com.akhona.sentinel.fraud.repository.*;
import com.akhona.sentinel.fraud.rule.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FraudEngineService {

    private final RuleRegistry ruleRegistry;
    private final FraudDecisionRepository decisionRepository;

    public FraudEngineService(RuleRegistry ruleRegistry, FraudDecisionRepository decisionRepository) {
        this.ruleRegistry = ruleRegistry;
        this.decisionRepository = decisionRepository;
    }

    public FraudDecision assess(Transaction transaction) {
        int totalScore = 0;
        List<String> triggered = new ArrayList<>();

        for (FraudRule rule : ruleRegistry.getRules()) {
            // Get Transaction rule results
            RuleResult result = rule.check(transaction);

            if (result.isTriggered()) {
                totalScore += result.getScore();
                triggered.add(rule.getRuleName());
            }
        }

        boolean flagged = totalScore >= 70;
        FraudDecision fraudDecision = FraudDecision.builder()
                .transactionId(transaction.getId())
                .fraudScore(totalScore)
                .flagged(flagged)
                .triggeredRules(triggered)
                .createdAt(LocalDateTime.now())
                .build();

        return decisionRepository.save(fraudDecision);
    }
}
