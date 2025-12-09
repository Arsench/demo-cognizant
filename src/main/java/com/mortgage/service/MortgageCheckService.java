package com.mortgage.service;

import com.mortgage.model.InterestRate;
import com.mortgage.model.MortgageCheckRequest;
import com.mortgage.model.MortgageCheckResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class MortgageCheckService {


    private InterestRateService interestRateService;

    public MortgageCheckService(InterestRateService interestRateService) {
        this.interestRateService = interestRateService;
    }

    public MortgageCheckResponse checkMortgage(MortgageCheckRequest request){

        BigDecimal income = request.getSalaryIncome();
        BigDecimal loanValue = request.getLoanValue();
        BigDecimal homeValue = request.getRealEstateValue();
        int maturity = request.getMatPeriod();

        BigDecimal salaryValue = income.multiply(BigDecimal.valueOf(4));
        if (loanValue.compareTo(salaryValue) > 0) {
            return new MortgageCheckResponse(false, BigDecimal.ZERO);
        }
        if (loanValue.compareTo(homeValue) > 0) {
            return new MortgageCheckResponse(false, BigDecimal.ZERO);
        }
        InterestRate rate =interestRateService.getInterestRateForMaturity(request.getMatPeriod());
        BigDecimal monthlyRate = rate.getInterestRate()
                .divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
        int months = maturity * 12;
        BigDecimal monthlyPayment = calculateMonthlyPayment(loanValue, monthlyRate, months);
        return new MortgageCheckResponse(true, monthlyPayment);
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal principal, BigDecimal monthlyInterestRate, int totalMonths) {

        MathContext mc = new MathContext(20, RoundingMode.HALF_UP);

        BigDecimal growthFactor = BigDecimal.ONE.add(monthlyInterestRate, mc);

        BigDecimal growthFactorPower = growthFactor.pow(totalMonths, mc);

        BigDecimal discountFactor = BigDecimal.ONE.divide(growthFactorPower, mc);

        BigDecimal amortizationDenominator = BigDecimal.ONE.subtract(discountFactor, mc);

        BigDecimal amortizationFactor = monthlyInterestRate.divide(amortizationDenominator, mc);

        return principal.multiply(amortizationFactor, mc);
    }


}
