package com.lendico.exercise.service;

import com.lendico.exercise.model.Loan;
import com.lendico.exercise.model.RepaymentPlan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;


class GeneratePlanServiceTest {

    GeneratePlanService generatePlanService;

    @BeforeEach
    public void setup() {
        generatePlanService = new GeneratePlanService();
    }

    @Test
    public void shouldGenerateRepaymentPlanForGivenLoanPayload() {
        Loan loan = new Loan();
        loan.setLoanAmount(new BigDecimal("200"));
        loan.setNominalRate(new BigDecimal("6"));
        loan.setDuration(2);
        loan.setStartDate(LocalDateTime.of(2018, 8, 1, 0, 0, 0));

        RepaymentPlan firstMonthPlan = new RepaymentPlan(new BigDecimal("101.01"), LocalDate.of(2018, 8, 1), new BigDecimal("200"), new BigDecimal("1.00"), new BigDecimal("100.01"), new BigDecimal("99.99"));
        RepaymentPlan secondMonthPlan = new RepaymentPlan(new BigDecimal("100.49"), LocalDate.of(2018, 9, 1), new BigDecimal("99.99"), new BigDecimal("0.50"), new BigDecimal("99.99"), new BigDecimal("0.00"));
        List<RepaymentPlan> result = generatePlanService.generatePlan(loan);
        assertEquals(result.get(0), firstMonthPlan);
        assertEquals(result.get(1), secondMonthPlan);

    }

    @Test
    public void shouldThrowErrorWhenInterestRateIsZero() {
        Loan loan = new Loan();
        loan.setLoanAmount(new BigDecimal("200"));
        loan.setNominalRate(new BigDecimal("0"));
        loan.setDuration(2);
        loan.setStartDate(LocalDateTime.of(2018, 8, 1, 0, 0, 0));
        RuntimeException interestRateException = Assertions.assertThrows(RuntimeException.class, () -> {
            generatePlanService.generatePlan(loan);
        });
        assertEquals(interestRateException.getMessage(), "Interest rate cannot be zero");
    }

    @Test
    public void shouldThrowErrorWhenDurationIsZero() {
        Loan loan = new Loan();
        loan.setLoanAmount(new BigDecimal("200"));
        loan.setNominalRate(new BigDecimal("2.0"));
        loan.setDuration(0);
        loan.setStartDate(LocalDateTime.of(2018, 8, 1, 0, 0, 0));
        RuntimeException durationException = Assertions.assertThrows(RuntimeException.class, () -> {
            generatePlanService.generatePlan(loan);
        });
        assertEquals(durationException.getMessage(), "Duration cannot be zero");
    }

}