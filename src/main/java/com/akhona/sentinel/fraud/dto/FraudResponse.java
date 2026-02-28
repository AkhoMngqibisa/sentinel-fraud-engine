package com.akhona.sentinel.fraud.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Response returned after fraud risk evaluation")
public record FraudResponse(

        @Schema(description = "Unique identifier of the evaluated transaction", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID transactionId,

        @Schema(description = "Calculated fraud risk score (0 = low risk, 100 = high risk)", example = "82", minimum = "0", maximum = "100")
        Integer fraudScore,

        @Schema(description = "Risk level classification", example = "HIGH")
        String flagged,

        @Schema(description = "Comma-separated list of fraud detection rules triggered during evaluation", example = "HighAmountRule, GeoLocationRule")
        String triggeredRules,

        @Schema(description = "Timestamp when the fraud evaluation was completed (ISO-8601 format)", example = "2026-03-01T20:45:12")
        LocalDateTime createdAt
) {
}