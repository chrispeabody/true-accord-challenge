package utilities;

import data.Debt;
import data.DebtInfo;
import data.Payment;
import data.PaymentPlan;
import enums.InstallmentFrequency;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
                "Cannot construct DebtAnalysisService: allDebts must not be null.");
        Objects.requireNonNull(allPaymentPlans,
                "Cannot construct DebtAnalysisService: allPaymentPlans must not be null.");
        Objects.requireNonNull(allPayments,
                "Cannot construct DebtAnalysisService: allPayments must not be null.");

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

        for (Debt debt : allDebts) {
            boolean isInPaymentPlan = findPaymentPlan(debt) != null;
            double remainingAmount = calculateRemainingAmount(debt);
            Date nextPaymentDueDate = isInPaymentPlan ? calculateNextPaymentDueDate(debt) : null;

            DebtInfo debtInfo = new DebtInfo(debt, isInPaymentPlan, remainingAmount, nextPaymentDueDate);
            debtInfos.add(debtInfo);
        }

        return debtInfos;
    }

    /**
     * Finds the payment plan associate with the given Debt, if one exists. The system assumes a debt will never have
     * more than one associated payment plan, so this function will return the id of the first match found.
     * @param debt The debt we want to find the related payment plan for.
     * @return The PaymentPlan the given Debt is associated with. If there is no associated plan, this
     * will return null.
     */
    public PaymentPlan findPaymentPlan(Debt debt) {
        Objects.requireNonNull(debt, "Cannot find payment plan: debt must not be null");

        for (PaymentPlan paymentPlan : this.allPaymentPlans) {
            if (paymentPlan.getDebtId() == debt.getId()) {
                return paymentPlan;
            }
        }

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
        Objects.requireNonNull(debt, "Cannot calculate remaining amount: debt must not be null");

        double remainingAmount = debt.getAmount();

        PaymentPlan paymentPlan = findPaymentPlan(debt);
        if (paymentPlan == null) {
            return remainingAmount;
        }

        for (Payment payment : this.allPayments) {
            if (payment.getPaymentPlanId() == paymentPlan.getId()) {
                remainingAmount -= payment.getAmount();
                if (remainingAmount <= 0) {
                    return 0;
                }
            }
        }

        return remainingAmount;
    }

    /**
     * Determines on what day the next payment will be (or was) expected to be payed according to a given debt.
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
     * @param debt The debt to find the next due date for
     * @return A Date representing the next payment due date for the plan. Null if the associated debt is already paid
     */
    public Date calculateNextPaymentDueDate(Debt debt) {
        Objects.requireNonNull(debt,
                "Cannot calculate nextPaymentDueDate: debt must not be null");
        PaymentPlan paymentPlan = Objects.requireNonNull(findPaymentPlan(debt),
                "Cannot calculate nextPaymentDueDate: debt must have an associated payment plan");
        if(calculateRemainingAmount(debt) == 0) {
            return null;
        }

        List<Payment> relatedPaymentsList = new ArrayList<>();
        for (Payment payment : allPayments) {
            if (payment.getPaymentPlanId() == paymentPlan.getId()) {
                relatedPaymentsList.add(payment);
            }
        }
        Iterator<Payment> relatedPaymentsIterator = relatedPaymentsList.iterator();

        Date currentDate = Objects.requireNonNull(paymentPlan.getStartDate(),
                "Cannot calculate nextPaymentDueDate: payment plan start date must not be null");

        double paidForCurrentDate = 0.0;
        while (true) {
            if (relatedPaymentsIterator.hasNext()) {
                paidForCurrentDate += relatedPaymentsIterator.next().getAmount();
                while (paidForCurrentDate >= paymentPlan.getInstallmentAmount()) {
                    paidForCurrentDate -= paymentPlan.getInstallmentAmount();
                    long millisToAdd = paymentPlan.getInstallmentFrequency() == InstallmentFrequency.WEEKLY
                            ? 604800000 : 1209600000;
                    currentDate.setTime(currentDate.getTime() + millisToAdd);
                }
            } else {
                return currentDate;
            }
        }
    }
}