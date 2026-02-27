package com.akhona.sentinel.fraud.rule;

import com.akhona.sentinel.fraud.config.VelocityRuleProperties;
import com.akhona.sentinel.fraud.model.FraudResult;
import com.akhona.sentinel.fraud.model.Transaction;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class VelocityRule implements FraudRule {

    private final StringRedisTemplate redisTemplate;
    private final VelocityRuleProperties properties ;

    public VelocityRule(StringRedisTemplate redisTemplate, VelocityRuleProperties properties) {
        this.redisTemplate = redisTemplate;
        this.properties = properties;
    }

    public VelocityRule(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.properties = new VelocityRuleProperties();
    }

    @Override
    public FraudResult check(Transaction transaction) {

        String key = "velocity:" + transaction.getUserId();

        Long count = redisTemplate.opsForValue().increment(key);

        if (count != null && count == 1) {
            redisTemplate.expire(key, Duration.ofSeconds(properties.getWindowSeconds()));
        }

        if (count != null && count > properties.getMaxTransactions()) {
            return FraudResult.flag(
                    "VELOCITY",
                    "Too many transactions in short period"
            );
        }

        return FraudResult.clear();
    }

    @Override
    public String getRuleName() {
        return "VELOCITY";
    }
}
