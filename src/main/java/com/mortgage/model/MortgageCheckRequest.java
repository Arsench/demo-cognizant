package com.mortgage.model;

import java.math.BigDecimal;

public class MortgageCheckRequest {

    BigDecimal salaryIncome;
    int matPeriod;
    BigDecimal loanValue;
    BigDecimal realEstateValue;

    private MortgageCheckRequest() {
    }

    public MortgageCheckRequest(BigDecimal salaryIncome, int matPeriod, BigDecimal loanValue, BigDecimal realEstateValue) {
        this.salaryIncome = salaryIncome;
        this.matPeriod = matPeriod;
        this.loanValue = loanValue;
        this.realEstateValue = realEstateValue;
    }

    public BigDecimal getSalaryIncome() {
        return salaryIncome;
    }

    public void setSalaryIncome(BigDecimal salaryIncome) {
        this.salaryIncome = salaryIncome;
    }

    public int getMatPeriod() {
        return matPeriod;
    }

    public void setMatPeriod(int matPeriod) {
        this.matPeriod = matPeriod;
    }

    public BigDecimal getLoanValue() {
        return loanValue;
    }

    public void setLoanValue(BigDecimal loanValue) {
        this.loanValue = loanValue;
    }

    public BigDecimal getRealEstateValue() {
        return realEstateValue;
    }

    public void setRealEstateValue(BigDecimal realEstateValue) {
        this.realEstateValue = realEstateValue;
    }
}
