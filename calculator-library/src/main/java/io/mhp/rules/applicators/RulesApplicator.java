package io.mhp.rules.applicators;

import java.math.BigDecimal;
import java.math.RoundingMode;

import io.mhp.properties.RulesConfig;

public abstract class RulesApplicator {

    protected static final int SCALE = 2;
    protected static final int COMPARABLE_DIFFERENCE = 0;

    protected static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    
    protected static final BigDecimal ZERO = BigDecimal.ZERO.setScale(SCALE, ROUNDING_MODE);

    protected final RulesConfig rules;
    
    public RulesApplicator(final RulesConfig rules) {
        this.rules = rules;
    }
    
}