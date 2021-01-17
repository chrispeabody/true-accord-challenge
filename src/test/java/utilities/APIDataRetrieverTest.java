package utilities;

import data.Debt;
import data.Payment;
import data.PaymentPlan;
import enums.InstallmentFrequency;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

public class APIDataRetrieverTest {
    /**
     * Ensure that given a valid URL the retriever will return a List of Debt objects, unpacked correctly from the API.
     */
    @Test
    public void getAllDebts_baseCase() {
        //ARRANGE
        APIDataRetriever apiDataRetriever = new APIDataRetriever("REPLACE_ME"); // TODO: mock endpoint

        // ACT
        List<Debt> debts = apiDataRetriever.getAllDebts();

        // ASSERT
        assertEquals(2,debts.size());

        assertEquals(10, debts.get(0).getId());
        assertEquals(11.87, debts.get(0).getAmount());

        assertEquals(11, debts.get(1).getId());
        assertEquals(18.94, debts.get(1).getAmount());
    }

    /**
     * Ensure that given a valid URL the retriever will return a List of PaymentPlan objects, unpacked correctly from
     * the API.
     */
    @Test
    public void getAllPaymentPlans_baseCase() {
        //ARRANGE
        final APIDataRetriever apiDataRetriever = new APIDataRetriever("REPLACE_ME"); // TODO: mock endpoint

        // ACT
        final List<PaymentPlan> paymentPlans = apiDataRetriever.getAllPaymentPlans();

        // ASSERT
        assertEquals(2,paymentPlans.size());

        assertEquals(8, paymentPlans.get(0).getId());
        assertEquals(6, paymentPlans.get(0).getDebtId());
        assertEquals(7.5, paymentPlans.get(0).getAmountToPay());
        assertEquals(InstallmentFrequency.WEEKLY, paymentPlans.get(0).getInstallmentFrequency());
        assertEquals(3.09, paymentPlans.get(0).getInstallmentAmount());
        assertEquals(new Date(), paymentPlans.get(0).getStartDate()); // TODO: Fill in date

        assertEquals(4, paymentPlans.get(1).getId());
        assertEquals(8, paymentPlans.get(1).getDebtId());
        assertEquals(15.16, paymentPlans.get(1).getAmountToPay());
        assertEquals(InstallmentFrequency.WEEKLY, paymentPlans.get(1).getInstallmentFrequency());
        assertEquals(23.42, paymentPlans.get(1).getInstallmentAmount());
        assertEquals(new Date(), paymentPlans.get(1).getStartDate()); // TODO: Fill in date
    }

    /**
     * Ensure that given a valid URL the retriever will return a List of Payment objects, unpacked correctly from the
     * API.
     */
    @Test
    public void getAllPayments_baseCase() {
        //ARRANGE
        final APIDataRetriever apiDataRetriever = new APIDataRetriever("REPLACE_ME"); // TODO: mock endpoint

        // ACT
        final List<Payment> payments = apiDataRetriever.getAllPayments();

        // ASSERT
        assertEquals(2,payments.size());

        assertEquals(3, payments.get(0).getPaymentPlanId());
        assertEquals(22.11, payments.get(0).getAmount());
        assertEquals(new Date(), payments.get(0).getDate()); // TODO: Fill in date

        assertEquals(42, payments.get(1).getPaymentPlanId());
        assertEquals(42.42, payments.get(1).getAmount());
        assertEquals(new Date(), payments.get(1).getDate()); // TODO: Fill in date
    }

    /**
     * Ensure that given an invalid URL the retriever will throw an exception.
     */
    @Test
    public void getAllDebts_invalidRootUrl() {
        // stub
    }

    /**
     * Ensure that given an invalid URL the retriever will throw an exception.
     */
    @Test
    public void getAllPaymentPlans_invalidRootUrl() {
        // stub
    }

    /**
     * Ensure that given an invalid URL the retriever will throw an exception.
     */
    @Test
    public void getAllPayments_invalidRootUrl() {
        // stub
    }
}