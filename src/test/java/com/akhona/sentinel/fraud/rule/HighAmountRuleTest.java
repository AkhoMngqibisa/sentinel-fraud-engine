package com.akhona.sentinel.fraud.rule;

import com.akhona.sentinel.fraud.config.HighAmountRuleProperties;
import com.akhona.sentinel.fraud.model.FraudResult;
import com.akhona.sentinel.fraud.model.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class HighAmountRuleTest {
    @Test
    void shouldFlagWhenAmountExceedsThreshold() {

        HighAmountRuleProperties properties = new HighAmountRuleProperties();
        properties.setThreshold(10000);
        properties.setCurrency("ZAR");

        HighAmountRule rule = new HighAmountRule(properties);

        Transaction tx = new Transaction("C123", BigDecimal.valueOf(15000), "ZAR","OnlineShop","ZA");

        FraudResult result = rule.check(tx);

        assertThat(result.isFlagged()).isTrue();
        assertThat(result.getRuleCode()).isEqualTo("HIGH_AMOUNT");
    }

    @Test
    void shouldNotFlagWhenBelowThreshold() {

        HighAmountRuleProperties properties = new HighAmountRuleProperties();
        properties.setThreshold(10000);
        properties.setCurrency("ZAR");

        HighAmountRule rule = new HighAmountRule(properties);

        Transaction tx = new Transaction("C123", BigDecimal.valueOf(1500), "ZAR","OnlineShop","ZA");

        FraudResult result = rule.check(tx);

        assertThat(result.isFlagged()).isFalse();
    }

}
