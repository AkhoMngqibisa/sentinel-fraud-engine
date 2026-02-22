package com.akhona.sentinel.fraud.model;

public class FraudResult {

    private boolean flagged;
    private String ruleCode;
    private String message;

    private FraudResult(boolean flagged, String ruleCode, String message) {
        this.flagged = flagged;
        this.ruleCode = ruleCode;
        this.message = message;
    }
}
