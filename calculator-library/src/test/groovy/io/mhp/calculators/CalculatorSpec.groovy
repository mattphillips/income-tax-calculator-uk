package io.mhp.calculators

import spock.lang.Specification
import spock.lang.Unroll

class CalculatorSpec extends Specification {

    @Unroll
    def "should return the loan percentage: #loanRate of the remaining salary: #salary"() {

        when:

        def result = Calculator.calculateStudentLoan(salary, loanRate)

        then:

        result == expectedResult

        where:

        salary  | loanRate || expectedResult
        100.00  | 0.10     || 10.00
        100.00  | 0.09     || 9.00
        100.00  | 0.50     || 50.00
    }

    @Unroll
    def "should return the tax percentage: #taxRate of the taxable income: #taxableIncome"() {

        when:

        def result = Calculator.calculateTax(taxableIncome, taxRate)

        then:

        result == expectedResult

        where:

        taxableIncome   | taxRate  || expectedResult
        100.00          | 0.20     || 20.00
        100.00          | 0.40     || 40.00
        100.00          | 0.45     || 45.00
    }

    @Unroll
    def "should return personal allowance: #personalAllowance add blind allowance income: #blindAllowance"() {

        when:

        def result = Calculator.calculatePersonalAllowanceWithBlindAllowance(personalAllowance, blindAllowance)

        then:

        result == expectedResult

        where:

        personalAllowance   | blindAllowance    || expectedResult
        10600.00            | 2290.00           || 12890.00
        100.00              | 2290.00           || 2390.00
        1850.63             | 2290.00           || 4140.63
    }

    @Unroll
    def "should return personal allowance rate: #paRate minus salary: #salary mode personal Allowance Band: #paBand divided by 2 and rounded down"() {

        when:

        def result = Calculator.calculateUpperBandPersonalAllowance(salary, paBand, paRate)

        then:

        result == expectedResult

        where:

        salary    | paBand    | paRate    || expectedResult
        116000.00 | 100000.00 | 10600.00  || 2600.00
        105000.00 | 100000.00 | 10600.00  || 8100.00
    }
}