package com.mortgage.dto.request;

import java.math.BigDecimal;

public class MortgageCheckRequestDto {

    private BigDecimal salaryIncome;
    private BigDecimal loanValue;
    private BigDecimal realEstateValue;
    private int maturityPeriod;

    public MortgageCheckRequestDto() {}

    public MortgageCheckRequestDto(BigDecimal salaryIncome, BigDecimal loanValue, BigDecimal realEstateValue, int maturityPeriod) {
        this.salaryIncome = salaryIncome;
        this.loanValue = loanValue;
        this.realEstateValue = realEstateValue;
        this.maturityPeriod = maturityPeriod;
    }

    public BigDecimal getSalaryIncome() { return salaryIncome; }
    public BigDecimal getLoanValue() { return loanValue; }
    public BigDecimal getRealEstateValue() { return realEstateValue; }
    public int getMaturityPeriod() { return maturityPeriod; }
}
