package com.mortgage.repository;

import com.mortgage.model.InterestRate;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class InterestRateRepository {

    private List<InterestRate> rates;

    @PostConstruct
    public void init() {
        this.rates = List.of(
                new InterestRate(10, new BigDecimal("0.035"), LocalDateTime.now()),
                new InterestRate(20, new BigDecimal("0.040"), LocalDateTime.now()),
                new InterestRate(30, new BigDecimal("0.042"), LocalDateTime.now())
        );

    }

    public List<InterestRate> findAll() {
        return rates;
    }

    public Optional<InterestRate> findByMaturityPeriod(int maturityPeriod) {
        return rates.stream()
                .filter(r -> r.getMaturityPeriod() == maturityPeriod)
                .findFirst();
    }
}
