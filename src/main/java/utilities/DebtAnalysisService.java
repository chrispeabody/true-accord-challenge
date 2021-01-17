package utilities;

import data.Debt;
import data.DebtInfo;
import data.Payment;
import data.PaymentPlan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * This service is responsible for calculating all of the metadata for a set of debts, payment plans, and payments. This
 * service is initialized with those objects, and all calls to it will operate on what it was given. The sets are not
 * mutable after constructed. Helper utilities are exposed for flexibility since they are useful on their own.
 */
public class DebtAnalysisService {
    List<Debt> allDebts;
    List<PaymentPlan> allPaymentPlans;
    List<Payment> allPayments;

    public DebtAnalysisService(List<Debt> allDebts,
                               List<PaymentPlan> allPaymentPlans,
                               List<Payment> allPayments) {
        Objects.requireNonNull(allDebts,
                "Cannot construct DebtAnalysisService: allDebts must not be null");
        Objects.requireNonNull(allPaymentPlans,
                "Cannot construct DebtAnalysisService: allPaymentPlans must not be null");
        Objects.requireNonNull(allPayments,
                "Cannot construct DebtAnalysisService: allPayments must not be null");

        this.allDebts = allDebts;
        this.allPaymentPlans = allPaymentPlans;
        this.allPayments = allPayments;
    }

    /**
     * This generates a list of DebtInfo metadata objects describing the debts, payment plans, and payments in the
     * system. Each DebtInfo will pertain to a single debt, and include the debt itself, whether it is in a payment
     * plan, how much is still unpaid on the debt, and when the next payment is due.
     * @return A List of DebtInfo metadata objects containing the Debt, if it isInPaymentPlan, the remainingAmount, and
     * the nextPaymentDueDate.
     */
    public List<DebtInfo> generateDebtInfos() {

        List<DebtInfo> debtInfos = new ArrayList<>();

        // stub
        // for each debt
        //      make a debtInfo object
        //      calculate if it's in a payment plan
        //      calculate the remaining amount
        //      if it's in a payment plan, calculate the next due date
        //      put all this stuff on the debtInfo
        // compile a list of all these debtInfo

        return debtInfos;
    }

    /**
     * Finds the payment plan associate with the given Debt, if one exists. The system assumes a debt will never have
     * more than one associated payment plan, so this function will return the id of the first match found.
     * @param debt The debt we want to find the related payment plan for.
     * @return The Integer id of the PaymentPlan the given Debt is associated with. If there is no associated plan, this
     * will return null.
     */
    public Integer findPaymentPlan(Debt debt) {
        // null check debt
        //
        // stub
        // get id from debt
        // loop through list of payment plans
        // check if any have that debt id
        // if one does, return the id of that payment plan

        return null;
    }

    /**
     * Determines the remaining amount of money, if any, that needs to be payed for a particular debt. Since a debt
     * requires a payment plan to have any payments, this will return the full, original debt amount if no payment plan
     * is found. The remaining amount is calculated by taking the original debt amount and subtracting the value of
     * each payment that has been made. This is agnostic of the amountToPay and installmentAmounts of the payment plan
     * because those could be less reliable than looking at the literal payments that have occurred. If the amount paid
     * exceeds the original debt amount, the remainingAmount will be 0.
     * @param debt The Debt for which we need to find the remaining amount.
     * @return A double representing the amount in USD that still needs to be paid on a debt.
     */
    public double calculateRemainingAmount(Debt debt) {
        //double remainingAmount = debt.getAmount();

        // null check debt
        //
        // stub
        // find the payment plan associated with the debt. if one doesn't exist, return the full amount
        //
        // loop through all payments
        // if the payment has an id related to this debt's plan, subtract it from the total
        // if the final amount is below zero, set it back to 0
        // return the final total

        return -1;
    }

    /**
     * Determines on what day the next payment will be (or was) expected to be payed according to a given paymentPlan.
     *
     * The next payment date is determined with the following assumptions:
     * - The first payment date in the schedule is the start date of the plan
     * - WEEKLY payment plans have a payment date every 7 days after the start date
     * - BI_WEEKLY payment plans have a payment date every 14 days after the start date
     * - The time that a payment occurs does not affect the days that payments are expected going forward
     * - The next payment date will correspond to the oldest unpaid payment date in the schedule, even if it is past due
     * - When a payment is received, it contributes to the earliest unpaid payment date
     * - The expected installment amount on a payment date must be met in order for that day to be considered paid. The
     * next time a payment is received, it will go toward filling out any deficits on previous payment days before being
     * applied to the next
     * - If a payment exceeds the expected installment amount for a payment date, the surplus will go toward paying the
     * next payment date
     * - If a debt is completely paid, the nextPaymentDueDate will be null
     *
     * @param paymentPlan The PaymentPlan to find the next payment day of
     * @return A Date representing the next payment due date for the plan. Null if the associated debt is already paid
     */
    public Date calculateNextPaymentDueDate(PaymentPlan paymentPlan) {
        Date nextPaymentDueDate = new Date();

        // null check paymentPlan
        // stub
        //
        // check the remainingAmount. If it's 0, then just return null
        //
        // starting with the paymentplan start date, keep track of the current due date
        // keep track of a currently paid amount for this date
        // while we haven't found the answer yet
        //      if there are more payments
        //          add the payment to the today's amount
        //          if it's higher than the installment amount
        //              subtract the installment amount from the running daily total
        //              find the next due date based on weekly or bi_weekly added to current date
        //      else (no more payments)
        //          this is the due date! return

        return nextPaymentDueDate;
    }
}