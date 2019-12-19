package com.lendico.exercise.service;

import com.lendico.exercise.model.Loan;
import com.lendico.exercise.model.RepaymentPlan;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class GeneratePlanService {
    public List<RepaymentPlan> generatePlan(Loan loan) {
        validateInterestAndDuration(loan);
        BigDecimal interestRate = loan.getNominalRate().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        BigDecimal annuity = calculateAnnuity(loan.getLoanAmount(), interestRate, loan.getDuration());
        LocalDate date = loan.getStartDate().toLocalDate();

        List<RepaymentPlan> repaymentPlanList = new ArrayList<>();
        repaymentPlanList.add(mapToRepaymentPlan(loan.getLoanAmount(), annuity, interestRate, date));
        IntStream.rangeClosed(1, loan.getDuration() - 1).forEach(i -> {
            RepaymentPlan prevMonthRepaymentDetails = repaymentPlanList.get(i - 1);
            repaymentPlanList.add(mapToRepaymentPlan(prevMonthRepaymentDetails.getRemainingOutstandingPrincipal(),
                    prevMonthRepaymentDetails.getBorrowerPaymentAmount(), interestRate, date.plusMonths(i)));
        });
        return repaymentPlanList;
    }

    private void validateInterestAndDuration(Loan loan) {
        if (loan.getNominalRate().compareTo(BigDecimal.ZERO) == 0) {
            throw new RuntimeException("Interest rate cannot be zero");
        }
        if (loan.getDuration() == 0) {
            throw new RuntimeException("Duration cannot be zero");
        }

    }

    private RepaymentPlan mapToRepaymentPlan(BigDecimal initialOutstandingPrincipal, BigDecimal annuity, BigDecimal nominalRate, LocalDate date) {
        BigDecimal interest = nominalRate.multiply(BigDecimal.valueOf(30)).multiply(initialOutstandingPrincipal).divide(BigDecimal.valueOf(360), 2, RoundingMode.HALF_UP);
        BigDecimal principal = annuity.subtract(interest).compareTo(initialOutstandingPrincipal) > 0 ? initialOutstandingPrincipal : annuity.subtract(interest);
        BigDecimal remainingOutstandingPrincipal = initialOutstandingPrincipal.subtract(principal);
        BigDecimal borrowerPaymentAmount = principal.add(interest);
        return new RepaymentPlan(borrowerPaymentAmount, date, initialOutstandingPrincipal, interest, principal, remainingOutstandingPrincipal);
    }

    private BigDecimal calculateAnnuity(BigDecimal loanAmount, BigDecimal rate, int duration) {
        BigDecimal ratePerMonth = rate.divide(BigDecimal.valueOf(12), 4, RoundingMode.HALF_UP);
        BigDecimal annuityNumerator = loanAmount.multiply(ratePerMonth);
        BigDecimal annuityDenominator = BigDecimal.ONE.subtract(BigDecimal.ONE.divide(ratePerMonth.add(BigDecimal.ONE).pow(duration), 4, RoundingMode.HALF_UP));
        return annuityNumerator.divide(annuityDenominator, 2, RoundingMode.HALF_UP);
    }
}
