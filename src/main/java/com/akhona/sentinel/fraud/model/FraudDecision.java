package com.akhona.sentinel.fraud.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FraudDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID transactionId;
    private Integer fraudScore;
    private Boolean flagged;

    @ElementCollection
    private List<String> triggeredRules;

    private LocalDateTime createdAt;
}
