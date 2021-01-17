package data;

import enums.InstallmentFrequency;

import java.util.Date;
import java.util.Objects;

/**
 * This class is meant purely for storing data related to a payment plan.
 * id                   - An identifier for this payment plan.
 * debtI                - The identifier of the debt this payment plan is related to.
 * amountToPay          - The total USD this plan aims to pay off once completed.
 * installmentFrequency - How often the payments in this plan occur, WEEKLY or BI_WEEKLY
 * startDate            - The date this payment plan went (or goes) into effect.
 */
public class PaymentPlan {
    private final int id;
    private final int debtId;
    private final double amountToPay;
    private final InstallmentFrequency installmentFrequency;
    private final double installmentAmount;
    private final Date startDate;

    public PaymentPlan(int id,
                       int debtId,
                       double amountToPay,
                       InstallmentFrequency installmentFrequency,
                       double installmentAmount,
                       Date startDate) {
        this.id = id;
        this.debtId = debtId;
        this.amountToPay = amountToPay;
        this.installmentFrequency = Objects.requireNonNull(installmentFrequency,
                "Cannot construct PaymentPlan: installmentFrequency must not be null");
        this.installmentAmount = installmentAmount;
        this.startDate = Objects.requireNonNull(startDate,
                "Cannot construct PaymentPlan: startDate must not be null");
    }

    public int getId() {
        return id;
    }

    public int getDebtId() {
        return debtId;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public InstallmentFrequency getInstallmentFrequency() {
        return installmentFrequency;
    }

    public double getInstallmentAmount() {
        return installmentAmount;
    }

    public Date getStartDate() {
        return startDate;
    }
}