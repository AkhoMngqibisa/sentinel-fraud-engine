package com.akhona.sentinel.fraud.controller;

import com.akhona.sentinel.fraud.dto.FraudResponse;
import com.akhona.sentinel.fraud.dto.TransactionRequest;
import com.akhona.sentinel.fraud.service.FraudEngineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Fraud Detection API", description = "Endpoints for real-time fraud analysis of transactions")
public class TransactionController {

    private final FraudEngineService fraudEngine;

    public TransactionController(FraudEngineService fraudEngine) {
        this.fraudEngine = fraudEngine;
    }

    @Operation(
            summary = "Analyze transaction for fraud risk",
            description = "Evaluates a transaction against configured fraud rules and returns a fraud risk decision."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fraud analysis completed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid transaction request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<FraudResponse> analyze(@Valid @RequestBody TransactionRequest request) {
        FraudResponse response = fraudEngine.assess(request);
        return ResponseEntity.ok(response);
    }
}
