package com.mortgage.dto.response;

import java.math.BigDecimal;

public class MortgageCheckResponseDto {

    private boolean feasible;
    private BigDecimal monthlyCosts;

    public MortgageCheckResponseDto() {}

    public MortgageCheckResponseDto(boolean feasible, BigDecimal monthlyCosts) {
        this.feasible = feasible;
        this.monthlyCosts = monthlyCosts;
    }

    public boolean isFeasible() { return feasible; }
    public BigDecimal getMonthlyCosts() { return monthlyCosts; }

}
