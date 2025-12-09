package com.mortgage.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InterestRate {


    int maturityPeriod;
    BigDecimal interestRate;
    LocalDateTime lastUpdate;

    private InterestRate() {
    }

    public InterestRate(int maturityPeriod, BigDecimal interestRate, LocalDateTime lastUpdate) {
        this.maturityPeriod = maturityPeriod;
        this.interestRate = interestRate;
        this.lastUpdate = lastUpdate;
    }

    public int getMaturityPeriod() {
        return maturityPeriod;
    }

    public void setMaturityPeriod(int maturityPeriod) {
        this.maturityPeriod = maturityPeriod;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
