package io.mhp.calculators

import io.mhp.properties.RulesConfig
import spock.lang.Specification
import spock.lang.Unroll

class TaxCalculatorTest extends Specification {

    RulesConfig mockRules = Mock()
    TaxCalculator classUnderTest

    def setup() {

        classUnderTest = new TaxCalculator(mockRules)
        configureMock()
    }
    
    @Unroll
    def "should return correct tax amount for salary: #salary"() {
       
        given: "a salary, personal allowance and a pension"
        
        def personalAllowance = 10600.00
        def pension = 0.00
        
        when:
        
        def result = classUnderTest.calculate(salary, personalAllowance, pension)
        
        then: "result should be the expected tax amount"
        
        result == expectedResult
        
        where:
        
        salary      || expectedResult
        1.00        || 0.00
        20000.00    || 1880.00
        70000.00    || 23760.00
        250000.00   || 107730.00
    }
    
    @Unroll
    def "should return correct tax rate for taxable income: #taxableIncome amount"() {
        
        given: "a taxable income"
        
        taxableIncome
        
        when: 
        
        def result = classUnderTest.getTaxRate(taxableIncome)
        
        then: "result should be the expected taxRate"
        
        result == expectedResult
        
        where:
        
        taxableIncome   || expectedResult
        -1.00           || 0.00
        20000.00        || 0.20
        50000.00        || 0.40
        150001.00       || 0.45
    }
    
    @Unroll
    def "should determine if taxable income: #taxableIncome is less than 0"() {
        
        given: "a taxable income"
        
        taxableIncome
        
        when: "taxable income is 0 or less"
        
        def result = classUnderTest.isTaxableIncomeLessThanOrEqualToZero(taxableIncome)
        
        then: "result should be true otherwise false"
        
        result == expectedResult
        
        where:
        
        taxableIncome   || expectedResult
        -1.00           || true
        0.00            || true
        1.00            || false
    }
    
    @Unroll
    def "should determine if taxable income: #taxableIncome is in the highier bracket"() {
        
        given: "a taxable income"
        
        taxableIncome
        
        when: "taxable income is between 31786 and 150000"
        
        def result = classUnderTest.isTaxableIncomeInHighierTaxBracket(taxableIncome)
        
        then: "result should be true otherwise false"
        
        result == expectedResult
        
        where:
        
        taxableIncome   || expectedResult
        31785.00        || false
        150000.01       || false
        31785.01        || true
        150000.00       || true
    }
    
    @Unroll
    def "should determine if taxable income: #taxableIncome is in the additional bracket"() {
        
        given: "a taxable income"
        
        taxableIncome
        
        when: "taxable income is between greater than 150000"
        
        def result = classUnderTest.isTaxableIncomeInAdditionalTaxBracket(taxableIncome)
        
        then: "result should be true otherwise false"
        
        result == expectedResult
        
        where:
        
        taxableIncome   || expectedResult
        150000.01       || true
        149999.99       || false
    }

    void configureMock() {

        mockRules.getBasicTaxRate() >> 0.20
        mockRules.getBasicTaxBand() >> 31785.00
        mockRules.getHighierTaxRate() >> 0.40
        mockRules.getAdditionalTaxRate() >> 0.45
        mockRules.getAdditionalTaxBand() >> 150000.00
    }
}