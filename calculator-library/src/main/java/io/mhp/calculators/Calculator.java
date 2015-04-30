package io.mhp.calculators;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {

    private static final int SCALE = 2;
    private static final int NO_DECIMAL_PLACES = 0;

    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    private static final RoundingMode ROUND_TO_NEAREST_WHOLE_POUND = RoundingMode.DOWN;

    private static final BigDecimal DIVIDER = new BigDecimal(2.00);

    public static BigDecimal calculateTax(final BigDecimal taxableIncome, final BigDecimal taxRate) {
        return taxableIncome.multiply(taxRate).setScale(SCALE, ROUNDING_MODE);
    }

    public static BigDecimal calculateStudentLoan(final BigDecimal remainingSalary, final BigDecimal loanRate) {
        return remainingSalary.multiply(loanRate).setScale(SCALE, ROUNDING_MODE);
    }

    public static BigDecimal calculatePersonalAllowanceWithBlindAllowance(
            final BigDecimal personalAllowance,
            final BigDecimal blindAllowance) {
        return personalAllowance.add(blindAllowance).setScale(SCALE, ROUNDING_MODE);
    }

    public static BigDecimal calculateUpperBandPersonalAllowance(
            final BigDecimal salary,
            final BigDecimal upperPersonalAllowanceBand,
            final BigDecimal personAllowanceRate) {

     // TODO: there is too much going on here this could be clearer
        BigDecimal reductionInPersonalAllowance =
                salary
                        .remainder(upperPersonalAllowanceBand)
                        .setScale(NO_DECIMAL_PLACES, ROUND_TO_NEAREST_WHOLE_POUND)
                        .divide(DIVIDER);

        return personAllowanceRate.subtract(reductionInPersonalAllowance);
    }
}