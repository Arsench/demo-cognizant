package com.mortgage.controller;


import com.mortgage.model.InterestRate;
import com.mortgage.model.MortgageCheckRequest;
import com.mortgage.model.MortgageCheckResponse;
import com.mortgage.service.InterestRateService;
import com.mortgage.service.MortgageCheckService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MortgageCheckController {

    private InterestRateService interestRateService;
    private MortgageCheckService mortgageCheckService;

    public MortgageCheckController(MortgageCheckService mortgageCheckService,InterestRateService interestRateService) {
        this.interestRateService = interestRateService;
        this.mortgageCheckService = mortgageCheckService;
    }

    @GetMapping("/interest-rates")
    public List<InterestRate> getRates() {
        return interestRateService.getAllRates();
    }

    @PostMapping("/mortgage-check")
    public MortgageCheckResponse check(@RequestBody MortgageCheckRequest request) {
        return mortgageCheckService.checkMortgage(request);
    }
}
