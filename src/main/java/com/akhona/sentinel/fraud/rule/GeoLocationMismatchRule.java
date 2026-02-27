package com.akhona.sentinel.fraud.rule;

import com.akhona.sentinel.fraud.model.FraudResult;
import com.akhona.sentinel.fraud.model.Transaction;
import com.akhona.sentinel.fraud.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class GeoLocationMismatchRule implements FraudRule {

    private final UserRepository userRepository;

    public GeoLocationMismatchRule(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public FraudResult check(Transaction transaction) {

        return userRepository.findById(transaction.getUserId())
                .map(user -> {
                    if (!user.getRegisteredCountry()
                            .equalsIgnoreCase(transaction.getLocation())) {

                        return FraudResult.flag(
                                "GEO_MISMATCH",
                                "Transaction country differs from registered country"
                        );
                    }
                    return FraudResult.clear();
                })
                .orElse(FraudResult.clear());
    }

    @Override
    public String getRuleName() {
        return "GEO_MISMATCH";
    }
}