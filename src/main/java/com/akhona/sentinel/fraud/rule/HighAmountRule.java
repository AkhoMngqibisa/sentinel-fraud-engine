package com.akhona.sentinel.fraud.rule;

import com.akhona.sentinel.fraud.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class HighAmountRule implements FraudRule {

    private static final BigDecimal THRESHOLD = new BigDecimal(50000);

    @Override
    public String getRuleName() {
        return "HIGH_AMOUNT";
    }

    @Override
    /*
      Flag if amount > threshold
     */
    public RuleResult check(Transaction transaction) {
        if (transaction.getAmount().compareTo(THRESHOLD) > 0) {
            return new RuleResult(true, 50);
        }
        return new RuleResult(false, 0);
    }
}
