package com.akhona.sentinel.fraud.service;

import com.akhona.sentinel.fraud.repository.FraudDecisionRepository;
import com.akhona.sentinel.fraud.rule.RuleRegistry;
import org.springframework.stereotype.Service;

@Service
public class FraudEngineService {

    private final RuleRegistry ruleRegistry;
    private final FraudDecisionRepository decisionRepository;

    public FraudEngineService(RuleRegistry ruleRegistry, FraudDecisionRepository decisionRepository) {
        this.ruleRegistry = ruleRegistry;
        this.decisionRepository = decisionRepository;
    }
}
