package com.akhona.sentinel.fraud.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Represents a financial transaction submitted for fraud evaluation")
public record TransactionRequest(

        @Schema(description = "Unique identifier of the user initiating the transaction", example = "USR100234")
        @NotNull String userId,

        @Schema(description = "Transaction amount", example = "15000.00", minimum = "0.01")
        @NotNull
        @Positive
        BigDecimal amount,

        @Schema(description = "Currency code (ISO 4217)", example = "ZAR")
        @NotNull String currency,

        @Schema(description = "Merchant display name", example = "Amazon SA")
        @NotNull String merchant,

        @Schema(description = "Geographical location of the transaction (City or Geo-coordinates)", example = "Cape Town, ZA")
        @NotNull String location,

        @Schema(description = "Unique identifier assigned to the merchant", example = "MERCH99821")
        @NotNull String merchantId,

        @Schema(description = "Unique account identifier", example = "ACC12345")
        @NotNull String accountId,

        @Schema(description = "Unique identifier of the device used for the transaction", example = "DEVICE-ABC-9988")
        @NotNull String deviceId
) {
}
