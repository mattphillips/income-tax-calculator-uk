package io.mhp.calculators;

import java.math.BigDecimal;
import java.math.RoundingMode;

import io.mhp.properties.RulesConfig;

public abstract class Calculator {

    protected static final int SCALE = 2;
    protected static final int COMPARABLE_DIFFERENCE = 0;

    protected static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    
    protected static final BigDecimal ZERO = BigDecimal.ZERO.setScale(SCALE, ROUNDING_MODE);

    protected final RulesConfig rules;
    
    public Calculator(final RulesConfig rules) {
        this.rules = rules;
    }
    
}