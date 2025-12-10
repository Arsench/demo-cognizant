package com.mortgage.service;

import com.mortgage.dto.request.MortgageCheckRequestDto;
import com.mortgage.dto.response.MortgageCheckResponseDto;
import com.mortgage.exception.BadRequestException;
import com.mortgage.model.InterestRate;
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

    public MortgageCheckResponseDto checkMortgage(MortgageCheckRequestDto request) {

        validateRequest(request);

        BigDecimal income = request.getSalaryIncome();
        BigDecimal loanValue = request.getLoanValue();
        BigDecimal homeValue = request.getRealEstateValue();
        int maturity = request.getMaturityPeriod();

        BigDecimal salaryValue = income.multiply(BigDecimal.valueOf(4));
        if (loanValue.compareTo(salaryValue) > 0) {
            return new MortgageCheckResponseDto(false, BigDecimal.ZERO);
        }
        if (loanValue.compareTo(homeValue) > 0) {
            return new MortgageCheckResponseDto(false, BigDecimal.ZERO);
        }
        InterestRate rate =interestRateService.getInterestRateForMaturity(request.getMaturityPeriod());
        BigDecimal monthlyRate = rate.getInterestRate()
                .divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
        int months = maturity * 12;
        BigDecimal monthlyPayment = calculateMonthlyPayment(loanValue, monthlyRate, months);
        return new MortgageCheckResponseDto(true, monthlyPayment);
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal principal, BigDecimal monthlyInterestRate, int totalMonths) {

        if (monthlyInterestRate.compareTo(BigDecimal.ZERO) == 0) {
            if (totalMonths == 0) {
                return BigDecimal.ZERO;
            }
            return principal.divide(BigDecimal.valueOf(totalMonths), RoundingMode.HALF_UP);
        }

        MathContext mc = new MathContext(20, RoundingMode.HALF_UP);

        BigDecimal growthFactor = BigDecimal.ONE.add(monthlyInterestRate, mc);

        BigDecimal growthFactorPower = growthFactor.pow(totalMonths, mc);

        BigDecimal discountFactor = BigDecimal.ONE.divide(growthFactorPower, mc);

        BigDecimal amortizationDenominator = BigDecimal.ONE.subtract(discountFactor, mc);

        BigDecimal amortizationFactor = monthlyInterestRate.divide(amortizationDenominator, mc);

        return principal.multiply(amortizationFactor, mc);
    }

    private void validateRequest(MortgageCheckRequestDto request) {
        if (request.getSalaryIncome().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Income cannot be zero or negative");
        }
        if (request.getLoanValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Loan value cannot be zero or negative");
        }
        if (request.getRealEstateValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Home value cannot be zero or negative");
        }
        if (request.getMaturityPeriod() <= 0) {
            throw new BadRequestException("Maturity must be greater than zero");
        }
    }



}
