package com.akhona.sentinel.fraud.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionRequest(

        @NotNull String userId,
        @NotNull
        @Positive
        BigDecimal amount,
        @NotNull String currency,
        @NotNull String merchant,
        @NotNull String location,
        @NotNull String merchantId,
        @NotNull String accountId,
        @NotNull String deviceId
) {
}
