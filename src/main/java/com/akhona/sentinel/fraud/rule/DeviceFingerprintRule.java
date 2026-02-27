package com.akhona.sentinel.fraud.rule;

import com.akhona.sentinel.fraud.model.FraudResult;
import com.akhona.sentinel.fraud.model.Transaction;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class DeviceFingerprintRule implements FraudRule {

    private final StringRedisTemplate redisTemplate;

    public DeviceFingerprintRule(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public FraudResult check(Transaction transaction) {

        String key = "device:" + transaction.getUserId();
        String knownDevice = redisTemplate.opsForValue().get(key);

        if (knownDevice == null) {
            redisTemplate.opsForValue().set(key, transaction.getDeviceId());
            return FraudResult.clear();
        }

        if (!knownDevice.equals(transaction.getDeviceId())) {
            return FraudResult.flag(
                    "NEW_DEVICE",
                    "Transaction initiated from new device"
            );
        }

        return FraudResult.clear();
    }

    @Override
    public String getRuleName() {
        return "NEW_DEVICE";
    }
}