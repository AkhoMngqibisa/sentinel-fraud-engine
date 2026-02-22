package com.akhona.sentinel.fraud.rule;

import com.akhona.sentinel.fraud.model.Transaction;

public interface FraudRule {
    RuleResult check(Transaction transaction);

    String getRuleName();
}
