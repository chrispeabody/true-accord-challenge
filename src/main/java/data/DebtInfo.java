package data;

import java.util.Date;

/**
 * This class is responsible for storing metadata about debts. Besides a function which can print the metadata, this
 * class is purely for data storage, and does no operations on the data.
 * debt                 - The debt this metadata is for.
 * isInPaymentPlan      - Metadata identifying whether or not this debt has a payment plan associated with it.
 * remainingAmount      - The remaining amount (in USD) that must be paid to pay off this debt.
 * nextPaymentDueDate   - The next date on which a payment is due, provided this debt has a payment plan.
 */
public class DebtInfo {
    private Debt debt;
    private Boolean isInPaymentPlan;
    private Double remainingAmount;
    private Date nextPaymentDueDate;

    public Debt getDebt() {
        return debt;
    }

    public void setDebt(Debt debt) {
        this.debt = debt;
    }

    public Boolean getIsInPaymentPlan() {
        return isInPaymentPlan;
    }

    public void setIsInPaymentPlan(Boolean is_in_payment_plan) {
        this.isInPaymentPlan = is_in_payment_plan;
    }

    public Double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Double remaining_amount) {
        this.remainingAmount = remaining_amount;
    }

    public Date getNextPaymentDueDate() {
        return nextPaymentDueDate;
    }

    public void setNextPaymentDueDate(Date next_payment_due_date) {
        this.nextPaymentDueDate = next_payment_due_date;
    }

    /**
     * Prints the data contained in this DebtInfo to System.out. Prints it in one line and returns.
     */
    public void print() {
        //stub
    }
}