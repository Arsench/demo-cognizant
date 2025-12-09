package com.mortgage.service;

import com.mortgage.exception.NotFoundException;
import com.mortgage.model.InterestRate;
import com.mortgage.model.MortgageCheckRequest;
import com.mortgage.model.MortgageCheckResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MortgageCheckServiceTest {

    @Mock
    private InterestRateService interestRateService;

    @InjectMocks
    private MortgageCheckService mortgageCheckService;

    @Test
    void mortgageNotFeasible_WhenLoanExceedsSalaryRule() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("20000"),   // income
                20,                        // maturity
                new BigDecimal("100000"),  // loanValue
                new BigDecimal("150000")   // homeValue
        );
        MortgageCheckResponse response = mortgageCheckService.checkMortgage(request);
        assertFalse(response.isFeasible());
        assertEquals(BigDecimal.ZERO, response.getMonthlyCosts());
    }

    @Test
    void mortgageNotFeasible_WhenLoanExceedsHomeValue() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                20,
                new BigDecimal("200000"),  // loanValue > homeValue
                new BigDecimal("150000")
        );
        MortgageCheckResponse response = mortgageCheckService.checkMortgage(request);
        assertFalse(response.isFeasible());
        assertEquals(BigDecimal.ZERO, response.getMonthlyCosts());
    }

    @Test
    void mortgageFeasible_ShouldReturnMonthlyPayment() {
        // Arrange
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                20,
                new BigDecimal("120000"),
                new BigDecimal("180000")
        );
        InterestRate rate = new InterestRate(20, new BigDecimal("0.04"), null);
        when(interestRateService.getInterestRateForMaturity(20))
               .thenReturn(rate);
        // Act
        MortgageCheckResponse response = mortgageCheckService.checkMortgage(request);
        // Assert
        assertTrue(response.isFeasible());
        assertTrue(response.getMonthlyCosts().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void interestRateNotFound_ShouldThrowException() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("40000"),
                25, // does not exist
                new BigDecimal("80000"),
                new BigDecimal("120000")
        );

        when(interestRateService.getInterestRateForMaturity(25))
                .thenThrow(new NotFoundException("No interest rate for maturity 25"));
        assertThrows(NotFoundException.class,
                () -> mortgageCheckService.checkMortgage(request));
    }

    @Test
    void loanValueZero_ShouldReturnZeroMonthlyPayment() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                20,
                BigDecimal.ZERO,
                new BigDecimal("100000")
        );
        InterestRate rate = new InterestRate(20, new BigDecimal("0.04"), null);
        when(interestRateService.getInterestRateForMaturity(20))
                .thenReturn(rate);

        MortgageCheckResponse response = mortgageCheckService.checkMortgage(request);
        assertTrue(response.isFeasible());
        assertEquals(BigDecimal.ZERO, response.getMonthlyCosts());
    }
}
