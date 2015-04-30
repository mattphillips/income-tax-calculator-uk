package io.mhp.rules.applicators

import io.mhp.properties.RulesConfig;
import io.mhp.rules.applicators.PersonalAllowanceRulesApplicator;
import spock.lang.Specification
import spock.lang.Unroll;

class PersonalAllowanceCalculatorSpec extends Specification {

    RulesConfig mockRules = Mock()
    PersonalAllowanceRulesApplicator classUnderTest

    def setup() {

        classUnderTest = new PersonalAllowanceRulesApplicator(mockRules)
        configureMock()
    }
    
    @Unroll
    def "should determine correct personal allowance based on salary: #salary, blind: #blind"() {

        given: "a salary and a blind flag"

        salary

        when: 

        def result = classUnderTest.calculate(salary, blind, false, false)

        then: "result should be correct personal allowance"

        result == expectedResult

        where:
        
        salary      | blind || expectedResult
        0           | true  || 12890.00
        0           | false || 10600.00
        99999.00    | true  || 12890.00
        99999.00    | false || 10600.00
        110000.00   | true  || 7890.00
        110000.00   | false || 5600.00
        115000.65   | true  || 5390.00
        115000.65   | false || 3100.00
        120000.00   | true  || 2290.00
        120000.00   | false || 0.00
        150000.00   | true  || 2290.00
        150000.00   | false || 0.00
    }
        
    
    @Unroll
    def "should return personal allowance minus 50% of every pound of a salary over 100000, salary: #salary"() {

        given: "a salary"

        salary

        when: "salary is over 100000"

        def result = classUnderTest.getReducedPersonalAllowanceRate(salary)

        then: "result should be personal allowance minus 50% of a salary over 100000"

        result == expectedResult

        where:

        salary      || expectedResult
        110000.00   || 5600.00
        100000.00   || 10600.00
        100000.01   || 10600.00
        115000.65   || 3100.00
    }

    @Unroll
    def "should determine if salary: #salary is greater than the upper personal allowance band"() {

        given: "a salary"

        salary

        when: "is greater than 100000"

        def result = classUnderTest.isSalaryGreaterThanUpperPersonalAllowanceBand(salary)

        then: "result should be true otherwise false"

        result == expectedResult

        where:

        salary      || expectedResult
        99999.99    || false
        100000.00   || false
        100000.01   || true
    }

    @Unroll
    def "should determine if salary: #salary is greater than the max personal allowance band"() {

        given: "a salary"

        salary

        when: "is greater than or equal to 120000"

        def result = classUnderTest.isSalaryGreateThanOrEqualToMaxPersonalAllowanceBand(salary)

        then: "result should be true otherwise false"

        result == expectedResult

        where:

        salary      || expectedResult
        119999.99   || false
        120000.00   || true
        120000.01   || true
    }

    void configureMock() {

        mockRules.getMaxPersonalAllowanceBand() >> 120000.00
        mockRules.getUpperPersonalAllowanceBand() >> 100000.00
        mockRules.getPersonalAllowanceRate() >> 10600.00
        mockRules.getBlindAllowanceRate() >> 2290.00
    }
}