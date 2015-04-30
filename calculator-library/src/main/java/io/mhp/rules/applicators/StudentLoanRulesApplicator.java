package io.mhp.rules.applicators;

import io.mhp.calculators.Calculator;
import io.mhp.domains.StudentLoanPlan;
import io.mhp.properties.RulesConfig;

import java.math.BigDecimal;

public class StudentLoanRulesApplicator extends RulesApplicator {

    public StudentLoanRulesApplicator(final RulesConfig rules) {
        super(rules);
    }

    BigDecimal calculate(final BigDecimal salary, final StudentLoanPlan studenLoanPlan) {

        if (studenLoanPlan.equals(StudentLoanPlan.ONE))
            return getStudentLoanRepayment(salary, rules.getStudentLoanPlan1());

        else if (studenLoanPlan.equals(StudentLoanPlan.TWO))
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
        return Calculator.calculateStudentLoan(remainingSalary, rules.getStudentLoanRate());
    }

    private boolean isSalaryLessThanLoanPlan(final BigDecimal salary, final BigDecimal loanPlan) {
        return salary.compareTo(loanPlan) < COMPARABLE_DIFFERENCE;
    }
}