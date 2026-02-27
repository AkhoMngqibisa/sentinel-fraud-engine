package com.akhona.sentinel.fraud.rule;

import com.akhona.sentinel.fraud.model.FraudResult;
import com.akhona.sentinel.fraud.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VelocityRuleTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    private VelocityRule velocityRule;

    private Transaction transaction;

    @BeforeEach
    void setup() {
        velocityRule = new VelocityRule(redisTemplate);

        transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setUserId("user123");
        transaction.setTimestamp(LocalDateTime.now());

        // Mock opsForValue() to return our mocked ValueOperations
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void shouldFlagWhenThresholdExceeded() {
        // Mock increment() to simulate threshold exceeded
        when(valueOperations.increment(anyString())).thenReturn(6L);

        FraudResult result = velocityRule.check(transaction);

        assertTrue(result.isFlagged());
        assertEquals("Too many transactions in short period", result.getMessage());
    }
}