package utilities;

import data.Debt;
import data.Payment;
import data.PaymentPlan;
import enums.InstallmentFrequency;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class APIDataRetrieverTest {
    /**
     * Ensure that given a valid URL the retriever will return a List of Debt objects, unpacked correctly from the API.
     */
    @Test
    public void GetAllDebts_baseCase() {
        //ARRANGE
        APIDataRetriever apiDataRetriever = new APIDataRetriever("REPLACE_ME"); // TODO: mock endpoint

        // ACT
        List<Debt> debts = apiDataRetriever.GetAllDebts();

        // ASSERT
        assertEquals(2,debts.size());

        assertEquals(Integer.valueOf(10), debts.get(0).getId());
        assertEquals(Double.valueOf(11.87), debts.get(0).getAmount());

        assertEquals(Integer.valueOf(11), debts.get(1).getId());
        assertEquals(Double.valueOf(18.94), debts.get(1).getAmount());

    }

    /**
     * Ensure that given a valid URL the retriever will return a List of PaymentPlan objects, unpacked correctly from
     * the API.
     */
    @Test
    public void GetAllPaymentPlans_baseCase() {
        //ARRANGE
        APIDataRetriever apiDataRetriever = new APIDataRetriever("REPLACE_ME"); // TODO: mock endpoint

        // ACT
        List<PaymentPlan> paymentPlans = apiDataRetriever.GetAllPaymentPlans();

        // ASSERT
        assertEquals(2,paymentPlans.size());

        assertEquals(Integer.valueOf(8), paymentPlans.get(0).getId());
        assertEquals(Integer.valueOf(6), paymentPlans.get(0).getDebtId());
        assertEquals(Double.valueOf(7.5), paymentPlans.get(0).getAmountToPay());
        assertEquals(InstallmentFrequency.WEEKLY, paymentPlans.get(0).getInstallmentFrequency());
        assertEquals(Double.valueOf(3.09), paymentPlans.get(0).getInstallmentAmount());
        assertEquals(new Date(), paymentPlans.get(0).getStartDate()); // TODO: Fill in date

        assertEquals(Integer.valueOf(4), paymentPlans.get(1).getId());
        assertEquals(Integer.valueOf(8), paymentPlans.get(1).getDebtId());
        assertEquals(Double.valueOf(15.16), paymentPlans.get(1).getAmountToPay());
        assertEquals(InstallmentFrequency.WEEKLY, paymentPlans.get(1).getInstallmentFrequency());
        assertEquals(Double.valueOf(23.42), paymentPlans.get(1).getInstallmentAmount());
        assertEquals(new Date(), paymentPlans.get(1).getStartDate()); // TODO: Fill in date
    }

    /**
     * Ensure that given a valid URL the retriever will return a List of Payment objects, unpacked correctly from the
     * API.
     */
    @Test
    public void GetAllPayments_baseCase() {
        //ARRANGE
        APIDataRetriever apiDataRetriever = new APIDataRetriever("REPLACE_ME"); // TODO: mock endpoint

        // ACT
        List<Payment> payments = apiDataRetriever.GetAllPayments();

        // ASSERT
        assertEquals(2,payments.size());

        assertEquals(Integer.valueOf(3), payments.get(0).getPaymentPlanId());
        assertEquals(Double.valueOf(22.11), payments.get(0).getAmount());
        assertEquals(new Date(), payments.get(0).getDate()); // TODO: Fill in date

        assertEquals(Integer.valueOf(42), payments.get(1).getPaymentPlanId());
        assertEquals(Double.valueOf(42.42), payments.get(1).getAmount());
        assertEquals(new Date(), payments.get(1).getDate()); // TODO: Fill in date
    }

    /**
     * Ensure that given an invalid URL the retriever will throw an exception.
     */
    @Test
    public void GetAllDebts_invalidRootUrl() {
        // stub
    }

    /**
     * Ensure that given an invalid URL the retriever will throw an exception.
     */
    @Test
    public void GetAllPaymentPlans_invalidRootUrl() {
        // stub
    }

    /**
     * Ensure that given an invalid URL the retriever will throw an exception.
     */
    @Test
    public void GetAllPayments_invalidRootUrl() {
        // stub
    }
}