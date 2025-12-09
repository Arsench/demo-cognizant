package com.mortgage.service;

import com.mortgage.exception.NotFoundException;
import com.mortgage.model.InterestRate;
import com.mortgage.repository.InterestRateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterestRateService {


    private InterestRateRepository repository;

    public InterestRateService(InterestRateRepository repository) {
        this.repository = repository;
    }
    public List<InterestRate> getAllRates() {
        return repository.findAll();
    }

    public InterestRate getInterestRateForMaturity(int period) {
        return repository.findByMaturityPeriod(period)
                .orElseThrow(() -> new NotFoundException("No interest rate for maturity " + period));
    }

}
