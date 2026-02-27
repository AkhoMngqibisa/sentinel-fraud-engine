package com.akhona.sentinel.fraud.rule;

import com.akhona.sentinel.fraud.model.FraudResult;
import com.akhona.sentinel.fraud.model.Transaction;
import com.akhona.sentinel.fraud.model.User;
import com.akhona.sentinel.fraud.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeoLocationMismatchRuleTest {

    @Mock
    private UserRepository userProfileRepository;

    @InjectMocks
    private GeoLocationMismatchRule geoRule;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = Transaction.builder()
                .userId("user123")
                .location("DE")
                .build();
    }

    @Test
    void shouldFlagWhenCountryMismatch() {
        User user = new User();
        user.setId("user123");
        user.setRegisteredCountry("US");

        when(userProfileRepository.findById("user123"))
                .thenReturn(Optional.of(user));

        FraudResult result = geoRule.check(transaction);

        assertTrue(result.isFlagged());
    }

    @Test
    void shouldNotFlagWhenCountryMatches() {
        User user = new User();
        user.setId("user123");
        user.setRegisteredCountry("DE");

        when(userProfileRepository.findById("user123"))
                .thenReturn(Optional.of(user));

        FraudResult result = geoRule.check(transaction);

        assertFalse(result.isFlagged());
    }
}
