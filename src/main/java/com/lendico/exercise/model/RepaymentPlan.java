package com.lendico.exercise.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class RepaymentPlan {
    BigDecimal borrowerPaymentAmount;
    LocalDate date;
    BigDecimal initialOutstandingPrinciple;
    BigDecimal interest;
    BigDecimal principal;
    BigDecimal remainingOutstandingPrincipal;

    public RepaymentPlan(BigDecimal borrowerPaymentAmount, LocalDate date, BigDecimal initialOutstandingPrinciple, BigDecimal interest, BigDecimal principal, BigDecimal remainingOutstandingPrincipal) {
        this.borrowerPaymentAmount = borrowerPaymentAmount;
        this.date = date;
        this.initialOutstandingPrinciple = initialOutstandingPrinciple;
        this.interest = interest;
        this.principal = principal;
        this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
    }

    public BigDecimal getBorrowerPaymentAmount() {
        return borrowerPaymentAmount;
    }

    public BigDecimal getRemainingOutstandingPrincipal() {
        return remainingOutstandingPrincipal;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getInitialOutstandingPrinciple() {
        return initialOutstandingPrinciple;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepaymentPlan)) return false;
        RepaymentPlan that = (RepaymentPlan) o;
        return Objects.equals(getBorrowerPaymentAmount(), that.getBorrowerPaymentAmount()) &&
                Objects.equals(date, that.date) &&
                Objects.equals(initialOutstandingPrinciple, that.initialOutstandingPrinciple) &&
                Objects.equals(interest, that.interest) &&
                Objects.equals(principal, that.principal) &&
                Objects.equals(getRemainingOutstandingPrincipal(), that.getRemainingOutstandingPrincipal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBorrowerPaymentAmount(), date, initialOutstandingPrinciple, interest, principal, getRemainingOutstandingPrincipal());
    }
}
