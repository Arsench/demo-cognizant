package com.mortgage.model;

import java.math.BigDecimal;

public class MortgageCheckResponse {

    boolean isFeasible;
    BigDecimal monthlyCosts;

    public MortgageCheckResponse() {
    }

    public MortgageCheckResponse(boolean isFeasible, BigDecimal monthlyCosts) {
        this.isFeasible = isFeasible;
        this.monthlyCosts = monthlyCosts;
    }

    public boolean isFeasible() {
        return isFeasible;
    }

    public void setFeasible(boolean feasible) {
        isFeasible = feasible;
    }

    public BigDecimal getMonthlyCosts() {
        return monthlyCosts;
    }

    public void setMonthlyCosts(BigDecimal monthlyCosts) {
        this.monthlyCosts = monthlyCosts;
    }
}
