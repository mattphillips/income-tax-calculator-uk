package io.mhp.calculators;

import io.mhp.properties.RulesConfig;

import java.math.BigDecimal;

public class StudentLoanCalculator extends Calculator {

    public StudentLoanCalculator(final RulesConfig rules) {
        super(rules);
    }

    BigDecimal calculate(final BigDecimal salary, final boolean studenLoanPlan1, final boolean studentLoanPlan2) {

        if (studenLoanPlan1)
            return getStudentLoanRepayment(salary, rules.getStudentLoanPlan1());

        else if (studentLoanPlan2)
            return getStudentLoanRepayment(salary, rules.getStudentLoanPlan2());

        else
            return ZERO;
    }

    private BigDecimal getStudentLoanRepayment(final BigDecimal salary, final BigDecimal loanPlan) {

        if (isSalaryLessThanLoanPlan(salary, loanPlan))
            return ZERO;

        else
            return applyStudentLoanRate(salary.subtract(loanPlan));
    }

    private BigDecimal applyStudentLoanRate(BigDecimal remainingSalary) {
        return remainingSalary.multiply(rules.getStudentLoanRate()).setScale(SCALE, ROUNDING_MODE);
    }

    private boolean isSalaryLessThanLoanPlan(final BigDecimal salary, final BigDecimal loanPlan) {
        return salary.compareTo(loanPlan) < COMPARABLE_DIFFERENCE;
    }
}