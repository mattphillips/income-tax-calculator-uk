package io.mhp.properties;

import java.math.BigDecimal;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources ({"classpath:Rules.properties"})
public interface RulesConfig extends Config {
    
    @Key("studentLoan.rate")
    BigDecimal getStudentLoanRate();
    
    @Key("studentLoan.plan-1")
    BigDecimal getStudentLoanPlan1();
    
    @Key("studentLoan.plan-2")
    BigDecimal getStudentLoanPlan2();
    
    @Key("basicTax.rate")
    BigDecimal getBasicTaxRate();
    
    @Key("basicTax.band")
    BigDecimal getBasicTaxBand();
    
    @Key("highierTax.rate")
    BigDecimal getHighierTaxRate();
    
    @Key("additionalTax.rate")
    BigDecimal getAdditionalTaxRate();
    
    @Key("additionalTax.band")
    BigDecimal getAdditionalTaxBand();
}