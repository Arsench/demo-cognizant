package com.mortgage.controller;


import com.mortgage.dto.request.MortgageCheckRequestDto;
import com.mortgage.dto.response.MortgageCheckResponseDto;
import com.mortgage.model.InterestRate;
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
    public MortgageCheckResponseDto check(@RequestBody MortgageCheckRequestDto requestDto)  {
        return mortgageCheckService.checkMortgage(requestDto);
    }
}
