package com.mortgage.model;

import java.math.BigDecimal;

public class MortgageCheckResponse {

    private boolean feasible;
    private BigDecimal monthlyCosts;

    private MortgageCheckResponse() {
    }

    public MortgageCheckResponse(boolean isFeasible, BigDecimal monthlyCosts) {
        this.feasible = isFeasible;
        this.monthlyCosts = monthlyCosts;
    }

    public boolean isFeasible() {
        return feasible;
    }

    public void setFeasible(boolean feasible) {
        this.feasible = feasible;
    }

    public BigDecimal getMonthlyCosts() {
        return monthlyCosts;
    }

    public void setMonthlyCosts(BigDecimal monthlyCosts) {
        this.monthlyCosts = monthlyCosts;
    }
}
