package com.akhona.sentinel.fraud.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record FraudResponse(
        UUID transactionId,
        Integer fraudScore,
        String flagged,
        String triggeredRules,
        LocalDateTime createdAt
) {
}
