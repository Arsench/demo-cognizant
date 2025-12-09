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

    private BigDecimal calculateMonthlyPayment(BigDecimal loanValue, BigDecimal monthlyRate, int months) {

        // Precisión para cálculos financieros
        MathContext mc = new MathContext(20, RoundingMode.HALF_UP);
        // 1 + r
        BigDecimal onePlusRate = BigDecimal.ONE.add(monthlyRate, mc);
        // (1 + r)^n
        BigDecimal power = onePlusRate.pow(months, mc);
        // (1 + r)^(-n)  →  1 / (1 + r)^n
        BigDecimal inverse = BigDecimal.ONE.divide(power, mc);
        // 1 - (1 + r)^(-n)
        BigDecimal denominator = BigDecimal.ONE.subtract(inverse, mc);
        // r / (1 - (1 + r)^(-n))
        BigDecimal fraction = monthlyRate.divide(denominator, mc);
        // Pago mensual = P × fraction
        return loanValue.multiply(fraction, mc);
    }

}
