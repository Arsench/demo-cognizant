package com.mortgage.service;

import com.mortgage.dto.request.MortgageCheckRequestDto;
import com.mortgage.dto.response.MortgageCheckResponseDto;
import com.mortgage.exception.BadRequestException;
import com.mortgage.exception.NotFoundException;
import com.mortgage.model.InterestRate;
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
        MortgageCheckRequestDto request = new MortgageCheckRequestDto(
                new BigDecimal("20000"),
                new BigDecimal("100000"),
                new BigDecimal("150000"),
                20);
        MortgageCheckResponseDto response = mortgageCheckService.checkMortgage(request);
        assertFalse(response.isFeasible());
        assertEquals(BigDecimal.ZERO, response.getMonthlyCosts());
    }

    @Test
    void mortgageNotFeasible_WhenLoanExceedsHomeValue() {
        MortgageCheckRequestDto request = new MortgageCheckRequestDto(
                new BigDecimal("50000"),
                new BigDecimal("200000"),
                new BigDecimal("150000"),
                20);
        MortgageCheckResponseDto response = mortgageCheckService.checkMortgage(request);
        assertFalse(response.isFeasible());
        assertEquals(BigDecimal.ZERO, response.getMonthlyCosts());
    }

    @Test
    void mortgageFeasible_ShouldReturnMonthlyPayment() {
        // Arrange
        MortgageCheckRequestDto request = new MortgageCheckRequestDto(
                new BigDecimal("50000"),
                new BigDecimal("120000"),
                new BigDecimal("180000"),
                20);
        InterestRate rate = new InterestRate(20, new BigDecimal("0.04"), null);
        when(interestRateService.getInterestRateForMaturity(20))
                .thenReturn(rate);
        // Act
        MortgageCheckResponseDto response = mortgageCheckService.checkMortgage(request);
        // Assert
        assertTrue(response.isFeasible());
        assertTrue(response.getMonthlyCosts().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void interestRateNotFound_ShouldThrowException() {
        MortgageCheckRequestDto request = new MortgageCheckRequestDto(
                new BigDecimal("40000"),
                new BigDecimal("80000"),
                new BigDecimal("120000"), 25 // not exist
        );

        when(interestRateService.getInterestRateForMaturity(25))
                .thenThrow(new NotFoundException("No interest rate for maturity 25"));
        assertThrows(NotFoundException.class,
                () -> mortgageCheckService.checkMortgage(request));
    }

    @Test
    void loanValueZero_ShouldReturnZeroMonthlyPayment() {

        MortgageCheckRequestDto request = new MortgageCheckRequestDto(
                new BigDecimal("50000"),
                BigDecimal.ZERO, // we must get an exception because the value is 0
                new BigDecimal("100000"),
                20
        );
        assertThrows(BadRequestException.class,
                () -> mortgageCheckService.checkMortgage(request));
    }
}
