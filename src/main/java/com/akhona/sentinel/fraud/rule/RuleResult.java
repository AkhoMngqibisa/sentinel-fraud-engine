package com.akhona.sentinel.fraud.rule;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RuleResult {
    private boolean triggered;
    private int score;
}
