package com.akhona.sentinel.fraud.rule;

import com.akhona.sentinel.fraud.config.HighAmountRuleProperties;
import com.akhona.sentinel.fraud.model.FraudResult;
import com.akhona.sentinel.fraud.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class HighAmountRule implements FraudRule {

    private final HighAmountRuleProperties properties;

    public HighAmountRule(HighAmountRuleProperties properties) {
        this.properties = properties;
    }

    @Override
    public String getRuleName() {
        return "HIGH_AMOUNT";
    }

    @Override
    /*
      Flag if amount > threshold
     */
    public FraudResult check(Transaction transaction) {
        if (!properties.getCurrency().equalsIgnoreCase(transaction.getCurrency())) {
            return FraudResult.clear();
        }

        if (transaction.getAmount().compareTo(BigDecimal.valueOf(properties.getThreshold())) > 0) {
            return FraudResult.flag(
                    "HIGH_AMOUNT",
                    "Transaction exceeds allowed threshold of " + properties.getThreshold()
            );
        }

        return FraudResult.clear();
    }
}
