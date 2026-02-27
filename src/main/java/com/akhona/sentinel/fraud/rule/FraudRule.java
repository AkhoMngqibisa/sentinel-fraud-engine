package com.akhona.sentinel.fraud.rule;

import com.akhona.sentinel.fraud.model.FraudResult;
import com.akhona.sentinel.fraud.model.Transaction;

public interface FraudRule {
    FraudResult check(Transaction transaction);

    String getRuleName();
}
