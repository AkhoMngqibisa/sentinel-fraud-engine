package com.akhona.sentinel.fraud.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private BigDecimal amount;

    private String currency;
    private String merchant;
    private String location;
    private String merchantId;
    private String accountId;
    private String deviceId;
    private LocalDateTime timestamp;

    public Transaction(String userId, BigDecimal amount, String currency, String merchant, String location) {
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.merchant = merchant;
        this.location = location;
        this.timestamp = LocalDateTime.now();
    }
}
