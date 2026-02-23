package com.akhona.sentinel.fraud.rule;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@AllArgsConstructor
public class RuleRegistry {

    private final List<FraudRule> rules;

}
