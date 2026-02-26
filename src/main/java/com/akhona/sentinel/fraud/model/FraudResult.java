package com.akhona.sentinel.fraud.model;

import com.akhona.sentinel.fraud.rule.RuleResult;
import lombok.*;

@Getter
public class FraudResult extends RuleResult {

    private final boolean flagged;
    private final String ruleCode;
    private final String message;

    private FraudResult(boolean flagged, String ruleCode, String message) {
        this.flagged = flagged;
        this.ruleCode = ruleCode;
        this.message = message;
    }

    public static FraudResult flag(String ruleCode, String message) {
        return new FraudResult(true, ruleCode, message);
    }

    public static FraudResult clear() {
        return new FraudResult(false, null, null);
    }
}