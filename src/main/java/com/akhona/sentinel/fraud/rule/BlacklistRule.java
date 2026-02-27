package com.akhona.sentinel.fraud.rule;

import com.akhona.sentinel.fraud.model.FraudResult;
import com.akhona.sentinel.fraud.model.Transaction;
import com.akhona.sentinel.fraud.repository.BlacklistRepository;
import org.springframework.stereotype.Component;

@Component
public class BlacklistRule implements FraudRule {

    private final BlacklistRepository repository;

    public BlacklistRule(BlacklistRepository repository) {
        this.repository = repository;
    }

    @Override
    public FraudResult check(Transaction transaction) {

        if (repository.existsByTypeAndValue("MERCHANT", transaction.getMerchantId()) ||
                repository.existsByTypeAndValue("ACCOUNT", transaction.getAccountId())) {

            return FraudResult.flag(
                    "BLACKLIST",
                    "Transaction involves blacklisted entity"
            );
        }

        return FraudResult.clear();
    }

    @Override
    public String getRuleName() {
        return "BLACKLIST";
    }
}