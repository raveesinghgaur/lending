package com.lendico.exercise.controller;

import com.lendico.exercise.model.Loan;
import com.lendico.exercise.service.GeneratePlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;


@ExtendWith(MockitoExtension.class)
class GeneratePlanControllerTest {

    @Mock
    GeneratePlanService generatePlanService;

    private GeneratePlanController generatePlanController;

    @BeforeEach
    public void setup() {
        generatePlanController = new GeneratePlanController(generatePlanService);
    }

    @Test
    public void shouldCallGeneratePlanService() {
        Loan loan = new Loan();
        loan.setLoanAmount(new BigDecimal("200"));
        loan.setNominalRate(new BigDecimal("6"));
        loan.setDuration(2);
        loan.setStartDate(LocalDateTime.of(2018, 8, 1, 0, 0, 0));
        Mockito.when(generatePlanService.generatePlan(loan)).thenReturn(new ArrayList<>());
        generatePlanController.generatePlanController(loan);

        Mockito.verify(generatePlanService, Mockito.times(1)).generatePlan(loan);
    }

}