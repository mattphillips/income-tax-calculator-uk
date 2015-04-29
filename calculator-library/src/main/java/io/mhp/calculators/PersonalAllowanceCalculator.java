package io.mhp.calculators;

import io.mhp.properties.RulesConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;

// TODO: marriage and age logic still need to be implemented pending further investigation
public class PersonalAllowanceCalculator extends Calculator {

    private static final int NO_DECIMAL_PLACES = 0;

    private static final RoundingMode ROUND_TO_NEAREST_WHOLE_POUND = RoundingMode.DOWN;

    private static final BigDecimal DIVIDER = new BigDecimal(2.00);

    public PersonalAllowanceCalculator(RulesConfig rules) {
        super(rules);
    }

    BigDecimal calculate(
            final BigDecimal salary,
            final boolean blindAllowance,
            final boolean marragieAllowance,
            final boolean ageAllowance) {

        // TODO: marriage and age logic will sit here

        return calculatePersonalAllowanceAgainstSalary(salary, blindAllowance);
    }

    private BigDecimal calculatePersonalAllowanceAgainstSalary(final BigDecimal salary, final boolean blindAllowance) {

        if (isSalaryGreateThanOrEqualToMaxPersonalAllowanceBand(salary))
            return blindAllowance ? rules.getBlindAllowanceRate() : ZERO;

        else if (isSalaryGreaterThanUpperPersonalAllowanceBand(salary))
            return blindAllowance
                    ? getReducedPersonalAllowanceRate(salary).add(rules.getBlindAllowanceRate())
                    : getReducedPersonalAllowanceRate(salary);

        else
            return blindAllowance ? rules.getPersonalAllowanceRate().add(rules.getBlindAllowanceRate()) : rules
                    .getPersonalAllowanceRate();
    }

    private BigDecimal getReducedPersonalAllowanceRate(final BigDecimal salary) {
        // TODO: there is too much going on here
        BigDecimal reductionInPersonalAllowance =
                salary
                        .remainder(rules.getUpperPersonalAllowanceBand())
                        .setScale(NO_DECIMAL_PLACES, ROUND_TO_NEAREST_WHOLE_POUND)
                        .divide(DIVIDER);

        return rules.getPersonalAllowanceRate().subtract(reductionInPersonalAllowance);
    }

    private boolean isSalaryGreaterThanUpperPersonalAllowanceBand(final BigDecimal salary) {
        return salary.compareTo(rules.getUpperPersonalAllowanceBand()) > COMPARABLE_DIFFERENCE;
    }

    private boolean isSalaryGreateThanOrEqualToMaxPersonalAllowanceBand(final BigDecimal salary) {
        return salary.compareTo(rules.getMaxPersonalAllowanceBand()) >= COMPARABLE_DIFFERENCE;
    }
}