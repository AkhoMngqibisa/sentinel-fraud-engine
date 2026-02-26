package com.akhona.sentinel.fraud.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "fraud.rules.high-amount")
public class HighAmountRuleProperties {

    /**
     * Maximum allowed amount before triggering fraud flag.
     */
    private double threshold;

    /**
     * Currency applicable to rule.
     */
    private String currency;

}
