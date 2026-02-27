package com.akhona.sentinel.fraud.rule;

import com.akhona.sentinel.fraud.model.FraudResult;
import com.akhona.sentinel.fraud.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleEngine {

    private final List<FraudRule> rules;

    public RuleEngine(List<FraudRule> rules) {
        this.rules = rules;
    }

    public FraudResult evaluate(Transaction transaction) {

        for (FraudRule rule : rules) {
            FraudResult result = rule.check(transaction);

            if (result.isFlagged()) {
                return result; // fail-fast strategy
            }
        }

        return FraudResult.clear();
    }
}
