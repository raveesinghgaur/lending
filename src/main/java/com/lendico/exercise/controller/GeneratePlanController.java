package com.lendico.exercise.controller;

import com.lendico.exercise.model.Loan;
import com.lendico.exercise.model.RepaymentPlan;
import com.lendico.exercise.service.GeneratePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GeneratePlanController {
    private final GeneratePlanService generatePlanService;

    @Autowired
    public GeneratePlanController(GeneratePlanService generatePlanService) {
        this.generatePlanService = generatePlanService;
    }

    @PostMapping("/loan/generate-plan")
    public List<RepaymentPlan> generatePlanController(@Valid @RequestBody Loan loan) {
        List<RepaymentPlan> repaymentPlanList = generatePlanService.generatePlan(loan);
        return repaymentPlanList;
    }
}
