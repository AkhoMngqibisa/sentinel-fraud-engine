package com.akhona.sentinel.fraud.model;

import lombok.*;

@Getter
public class FraudResult {

    private boolean flagged;
    private String ruleCode;
    private String message;

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
