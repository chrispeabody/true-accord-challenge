package data;

import java.util.Date;

/**
 * This class is meant purely for storing data related to a payment. It has the id of the payment plan it relates to,
 * the amount of USD paid, and the date when the payment occurred.
 */
public class Payment {
    private Integer paymentPlanId;
    private Double amount;
    private Date date;

    public Integer getPaymentPlanId() {
        return paymentPlanId;
    }

    public void setPaymentPlanId(Integer paymentPlanId) {
        this.paymentPlanId = paymentPlanId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}