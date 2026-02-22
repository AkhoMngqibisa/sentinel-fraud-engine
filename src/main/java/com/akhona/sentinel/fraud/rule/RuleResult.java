package com.akhona.sentinel.fraud.rule;

import lombok.*;

@AllArgsConstructor
@Getter
public class RuleResult {
    private boolean triggered;
    private int score;
}
