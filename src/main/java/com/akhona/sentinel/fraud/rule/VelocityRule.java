package com.akhona.sentinel.fraud.rule;

import com.akhona.sentinel.fraud.model.Transaction;
import com.akhona.sentinel.fraud.repository.TransactionRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class VelocityRule implements FraudRule {

    private final TransactionRepository transactionRepository;
    private final StringRedisTemplate stringRedisTemplate;

    public VelocityRule(TransactionRepository transactionRepository, StringRedisTemplate stringRedisTemplate) {
        this.transactionRepository = transactionRepository;
        this.stringRedisTemplate = stringRedisTemplate;
    }

//    @Override
//    /*
//     * Detect rapid transactions from the same user within a short window.
//     * If a customer performs ≥ 3 transactions within 1 minute, flag fraud.
//     */
//    public RuleResult check(Transaction transaction) {
//        LocalDateTime now = LocalDateTime.now().minusMinutes(1);
//        List<Transaction> recentTransaction = transactionRepository.findRecentTransactions(transaction.getUserId(), now);
//
//        if (recentTransaction.size() >= 3) {
//            return new RuleResult(true, 40);
//        }
//        return new RuleResult(false, 0);
//    }

    @Override
    public RuleResult check(Transaction transaction) {
        String key = "velocity: " + transaction.getUserId();

        Long count = stringRedisTemplate.opsForValue().increment(key);

        stringRedisTemplate.expire(key, Duration.ofMinutes(1));

        if (count >= 3) {
            return new RuleResult(true, 40);
        }
        return new RuleResult(false, 0);
    }

    @Override
    public String getRuleName() {
        return "VELOCITY";
    }
}
