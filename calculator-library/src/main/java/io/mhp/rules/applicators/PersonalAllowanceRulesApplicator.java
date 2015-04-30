package io.mhp.rules.applicators;

import io.mhp.calculators.Calculator;
import io.mhp.properties.RulesConfig;

import java.math.BigDecimal;

// TODO: marriage and age logic still need to be implemented pending further investigation
public class PersonalAllowanceRulesApplicator extends RulesApplicator {

    public PersonalAllowanceRulesApplicator(RulesConfig rules) {
        super(rules);
    }

    // TODO: change these bools to enum
    BigDecimal calculate(
            final BigDecimal salary,
            final boolean blindAllowance,
            final boolean marragieAllowance,
            final boolean ageAllowance) {

        // TODO: marriage and age logic will sit here
        BigDecimal personalAllowance = calculatePersonalAllowanceAgainstSalary(salary, blindAllowance);

        if (blindAllowance)
            return Calculator.calculatePersonalAllowanceWithBlindAllowance(
                    personalAllowance,
                    rules.getBlindAllowanceRate());

        else
            return personalAllowance;
    }

    private BigDecimal calculatePersonalAllowanceAgainstSalary(final BigDecimal salary, final boolean blindAllowance) {

        if (isSalaryGreateThanOrEqualToMaxPersonalAllowanceBand(salary))
            return ZERO;

        else if (isSalaryGreaterThanUpperPersonalAllowanceBand(salary))
            return getReducedPersonalAllowanceRate(salary);

        else
            return rules.getPersonalAllowanceRate();
    }

    private BigDecimal getReducedPersonalAllowanceRate(final BigDecimal salary) {

        return Calculator.calculateUpperBandPersonalAllowance(
                salary,
                rules.getUpperPersonalAllowanceBand(),
                rules.getPersonalAllowanceRate());
    }

    private boolean isSalaryGreaterThanUpperPersonalAllowanceBand(final BigDecimal salary) {
        return salary.compareTo(rules.getUpperPersonalAllowanceBand()) > COMPARABLE_DIFFERENCE;
    }

    private boolean isSalaryGreateThanOrEqualToMaxPersonalAllowanceBand(final BigDecimal salary) {
        return salary.compareTo(rules.getMaxPersonalAllowanceBand()) >= COMPARABLE_DIFFERENCE;
    }
}