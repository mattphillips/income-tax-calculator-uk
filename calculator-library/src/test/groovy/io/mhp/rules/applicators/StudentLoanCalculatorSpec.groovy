package io.mhp.rules.applicators

import io.mhp.properties.RulesConfig
import io.mhp.rules.applicators.StudentLoanRulesApplicator;
import io.mhp.domains.StudentLoanPlan
import spock.lang.Specification
import spock.lang.Unroll

class StudentLoanCalculatorSpec extends Specification {
    
    RulesConfig mockRules = Mock()
    StudentLoanRulesApplicator classUnderTest
    
    def setup() {
        
        classUnderTest = new StudentLoanRulesApplicator(mockRules)
        configureMock()
    }
    
    def "should return repayment amount for student loan plan 1"() {
        
        given: "student loan plan 1 is active"
        
        def studentLoanPlan1 = StudentLoanPlan.ONE
        
        when: "applying correct rate to salary"
        
        def repayment = classUnderTest.calculate(salary, studentLoanPlan1)
        
        then: "the expected result should be 9% of any earnings over 17335"
        
        repayment == expectedRepayment
        
        where:
        
        salary      || expectedRepayment
        0.00        || 0.00
        20000.00    || 239.85
        50000.00    || 2939.85
        42500.65    || 2264.91
        17335.00    || 0.00
    }
    
    def "should return repayment amount for student loan plan 2"() {
    
        given: "student loan plan 2 is active"
        
        def studentLoanPlan2 = StudentLoanPlan.TWO
        
        when: "applying correct rate to salary"
        
        def repayment = classUnderTest.calculate(salary, studentLoanPlan2)
        
        then: "the expected result should be 9% of any earnings over 21000"
        
        repayment == expectedRepayment
        
        where:
        
        salary      || expectedRepayment
        0.00        || 0.00
        25000.00    || 360.00
        50000.00    || 2610.00
        42500.65    || 1935.06
        21000.00    || 0.00
    }
    
    def "should return 0 when neither loan plan is active"() {
        
        given: "no active loan plan"
        
        def salary = 100000.00
        def studentLoanPlan = StudentLoanPlan.NEITHER
        
        when: "applying correct rate to salary"
        
        def repayment = classUnderTest.calculate(salary, studentLoanPlan)
        
        then: "the expected result should be 0"
        
        repayment == 0
    }
    
    @Unroll
    def "should return 9% of supplied salary: #salary"() {
    
        given:
        
        salary
        
        when: "applying student loan rate to salary"
        
        def repayment = classUnderTest.applyStudentLoanRate(salary)
        
        then: "the expected return amount should be 9% of the given salary"
        
        repayment == expectedRepayment
        
        where:
        
        salary  || expectedRepayment
        1.00    || 0.09
        10.00   || 0.90
        100.00  || 9.00
        1000.00 || 90.00
        350.91  || 31.58
        89020.01|| 8011.80
        3819.58 || 343.76
    }
    
    void configureMock() {
        
        mockRules.getStudentLoanPlan1() >> 17335.00
        mockRules.getStudentLoanPlan2() >> 21000.00
        mockRules.getStudentLoanRate() >> 0.09
    }
}