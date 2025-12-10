# Mortgage Backend Assessment

This project is a small backend application built in Java using Spring Boot.  
The goal is to provide a simple service that can return predefined interest rates and check whether a mortgage is feasible based on a few basic rules.
There is no database behind it. Everything is kept in memory because the assignment explicitly asked for it.

## What the application does

### 1. Retrieve available interest rates  
**GET `/api/interest-rates`**

This returns a list of interest rates.  
Each record includes:

- a maturity period (in years)
- the annual interest rate
- the timestamp when the value was loaded

These values are created automatically on startup.

### 2. Check whether a mortgage is feasible  
**POST `/api/mortgage-check`**

The service receives something like:

```json
{
  "salaryIncome": 50000,
  "loanValue": 120000,
  "realEstateValue": 180000,
  "matPeriod": 20
}
```
The response should be
```json
{
  "feasible": true,
  "monthlyCosts": 727.14
}
```

### Business rules

A mortgage is considered not feasible if:
loanValue > 4 × income
loanValue > homeValue

If the requested maturity period does not match any available interest rate
the service returns:
```json
{
  "feasible": false,
  "monthlyCosts": 0
}
```
### How the monthly payment is calculated
The monthly mortgage payment is based on the standard amortization formula.
Since I do not work regularly with financial formulas, I requested assistance from an LLM specifically for understanding the mathematical structure of the formula.
Please NOTE: The code itself was written and adapted by me, but the formula explanation was assisted by the model due to the financial nature of the calculation.
```json
payment = P × ( r / (1 - (1 + r)^(-n)) )
```
Where:

P = principal (loan amount)
r = monthly interest rate (annualRate / 12)
n = total number of months
The calculation method also supports the case of a 0% interest rate, even though none of the predefined interest rates use it. 
In that scenario, the formula reduces to principal / months.
This avoids division issues and matches real financial behavior.

### Initial interest rates
These are loaded in memory using @PostConstruct:
```json
[
  { "maturityPeriod": 10, "interestRate": 0.035 },
  { "maturityPeriod": 20, "interestRate": 0.040 },
  { "maturityPeriod": 30, "interestRate": 0.042 }
]
```
They can easily be replaced with a proper database if needed.

### Input validation

Before applying any mortgage rule, the service checks:
income must not be negative or 0
loanValue must not be negative or 0
realEstateValue must not be negative or 0
maturity period must be greater than 0
Invalid input results in a custom BadRequestException, which returns a 400 error.

### Code structure
The project is organized into clear layers:

Controller → handles HTTP requests
Service → contains all business logic and validations
Repository → provides access to the in-memory interest rates
Model → request/response objects and interest rate model
Exception → custom exceptions for error handling

### Running the tests
There are several JUnit tests (using Mockito where needed) that validate:

business rule failures
a successful mortgage scenario
behavior when interest rate is missing
the amortization formula

### Technologies used

Java 17
Spring Boot (Web)
JUnit 5
Mockito
BigDecimal for accurate financial calculations
