package com.akhona.sentinel.fraud.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "fraud.rules.velocity")
public class VelocityRuleProperties {

    private int windowSeconds;
    private int maxTransactions;

}
