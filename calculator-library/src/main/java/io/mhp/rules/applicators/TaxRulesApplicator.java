package io.mhp.rules.applicators;

import io.mhp.properties.RulesConfig;

import java.math.BigDecimal;

// TODO: it may be worth creating a domain for this now to set a custom scale
public class TaxRulesApplicator extends RulesApplicator {

    public TaxRulesApplicator(final RulesConfig rules) {
        super(rules);
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