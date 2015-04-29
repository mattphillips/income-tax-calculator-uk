package io.mhp.calculators;

import io.mhp.properties.RulesConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;

// TODO: it may be worth creating a domain for this now to set a custom scale
public class TaxCalculator {

    private static final int COMPARABLE_DIFFERENCE = 0;
    private static final int SCALE = 2;

    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    
    private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(SCALE, ROUNDING_MODE);
    
    private final RulesConfig rules;

    public TaxCalculator(final RulesConfig rules) {
        this.rules = rules;
    }

    BigDecimal calculate(final BigDecimal salary, final BigDecimal personalAllowance, final BigDecimal pension) {

        BigDecimal taxableIncome = salary.subtract(personalAllowance).subtract(pension);

        BigDecimal taxRate = getTaxRate(taxableIncome);

        return taxableIncome.multiply(taxRate).setScale(SCALE, ROUNDING_MODE);
    }

    private BigDecimal getTaxRate(BigDecimal taxableIncome) {

        if (isTaxableIncomeLessThanOrEqualToZero(taxableIncome))
            return ZERO;

        else if (isTaxableIncomeInHighierTaxBracket(taxableIncome))
            return rules.getHighierTaxRate();

        else if (isTaxableIncomeInAdditionalTaxBracket(taxableIncome))
            return rules.getAdditionalTaxRate();

        else
            return rules.getBasicTaxRate();
    }

    private boolean isTaxableIncomeLessThanOrEqualToZero(BigDecimal taxableIncome) {
        return taxableIncome.compareTo(ZERO) <= COMPARABLE_DIFFERENCE;
    }

    private boolean isTaxableIncomeInHighierTaxBracket(BigDecimal taxableIncome) {
        return taxableIncome.compareTo(rules.getBasicTaxBand()) > COMPARABLE_DIFFERENCE
                && taxableIncome.compareTo(rules.getAdditionalTaxBand()) <= COMPARABLE_DIFFERENCE;
    }

    private boolean isTaxableIncomeInAdditionalTaxBracket(BigDecimal taxableIncome) {
        return taxableIncome.compareTo(rules.getAdditionalTaxBand()) > COMPARABLE_DIFFERENCE;
    }
}