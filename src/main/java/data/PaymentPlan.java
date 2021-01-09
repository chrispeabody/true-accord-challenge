package data;

import enums.InstallmentFrequency;

import java.util.Date;

/**
 * This class is meant purely for storing data related to a payment plan.
 * id                   - An identifier for this payment plan.
 * debtI                - The identifier of the debt this payment plan is related to.
 * amountToPay          - The total USD this plan aims to pay off once completed.
 * installmentFrequency - How often the payments in this plan occur, WEEKLY or BI_WEEKLY
 * startDate            - The date this payment plan went (or goes) into effect.
 */
public class PaymentPlan {
    private Integer id;
    private Integer debtId;
    private Double amountToPay;
    private InstallmentFrequency installmentFrequency;
    private Double installmentAmount;
    private Date startDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDebtId() {
        return debtId;
    }

    public void setDebtId(Integer debtId) {
        this.debtId = debtId;
    }

    public Double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(Double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public InstallmentFrequency getInstallmentFrequency() {
        return installmentFrequency;
    }

    public void setInstallmentFrequency(InstallmentFrequency installmentFrequency) {
        this.installmentFrequency = installmentFrequency;
    }

    public Double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(Double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}