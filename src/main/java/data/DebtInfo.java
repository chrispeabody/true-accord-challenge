package data;

import java.util.Date;
import java.util.Objects;

/**
 * This class is responsible for storing metadata about debts. Besides a function which can print the metadata, this
 * class is purely for data storage, and does no operations on the data.
 * debt                 - The debt this metadata is for.
 * isInPaymentPlan      - Metadata identifying whether or not this debt has a payment plan associated with it.
 * remainingAmount      - The remaining amount (in USD) that must be paid to pay off this debt.
 * nextPaymentDueDate   - The next date on which a payment is due, provided this debt has a payment plan.
 */
public class DebtInfo {
    private final Debt debt;
    private final boolean isInPaymentPlan;
    private final double remainingAmount;
    private final Date nextPaymentDueDate; // nullable

    public DebtInfo(Debt debt, boolean isInPaymentPlan, double remainingAmount, Date nextPaymentDueDate) {
        this.debt = Objects.requireNonNull(debt,
                "Cannot construct DebtInfo: debt must not be null");
        this.isInPaymentPlan = isInPaymentPlan;
        this.remainingAmount = remainingAmount;
        this.nextPaymentDueDate = nextPaymentDueDate;
    }

    public Debt getDebt() {
        return debt;
    }

    public boolean getIsInPaymentPlan() {
        return isInPaymentPlan;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public Date getNextPaymentDueDate() {
        return nextPaymentDueDate;
    }

    /**
     * Prints the data contained in this DebtInfo to System.out. Prints it in one line and returns.
     */
    public void print() {
        //stub
    }
}