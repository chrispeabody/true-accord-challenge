package data;

import java.util.Date;
import java.util.Objects;

/**
 * This class is meant purely for storing data related to a payment. It has the id of the payment plan it relates to,
 * the amount of USD paid, and the date when the payment occurred.
 */
public class Payment {
    private final int paymentPlanId;
    private final double amount;
    private final Date date;

    public Payment(int paymentPlanId, double amount, Date date) {
        this.paymentPlanId = paymentPlanId;
        this.amount = amount;
        this.date = Objects.requireNonNull(date,
                "Cannot construct Payment: date must not be null");
    }

    public int getPaymentPlanId() {
        return paymentPlanId;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
}