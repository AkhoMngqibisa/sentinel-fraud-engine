package com.akhona.sentinel.fraud.rule;

import com.akhona.sentinel.fraud.model.FraudResult;
import com.akhona.sentinel.fraud.model.Transaction;
import com.akhona.sentinel.fraud.repository.BlacklistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlacklistRuleTest {

    @Mock
    private BlacklistRepository blacklistRepository;

    @InjectMocks
    private BlacklistRule blacklistRule;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = Transaction.builder()
                .merchantId("M123")
                .accountId("ACC456")
                .build();
    }

    @Test
    void shouldFlagWhenMerchantIsBlacklisted() {

        when(blacklistRepository
                .existsByTypeAndValue("M123", "ACC456"))
                .thenReturn(true);

        FraudResult result = blacklistRule.check(transaction);

        assertTrue(result.isFlagged());
        verify(blacklistRepository)
                .existsByTypeAndValue("M123", "ACC456");
    }

    @Test
    void shouldNotFlagWhenNotBlacklisted() {

        when(blacklistRepository
                .existsByTypeAndValue(any(), any()))
                .thenReturn(false);

        FraudResult result = blacklistRule.check(transaction);

        assertFalse(result.isFlagged());
    }
}