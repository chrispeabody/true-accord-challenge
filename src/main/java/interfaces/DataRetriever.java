package interfaces;

import data.Debt;
import data.Payment;
import data.PaymentPlan;
import java.util.List;

/**
 * This interface is responsible for retrieving data from a data source and populating objects. The exact data source
 * and method of retrieval will vary based on which implementation is used.
 */
public interface DataRetriever {
    /**
     * Retrieves all off the debts from the data source.
     * @return A List of Debt objects representing all of the debts in the data source.
     * @throws Exception The specific exceptions that are thrown may vary per implementation.
     */
    List<Debt> getAllDebts() throws Exception;

    /**
     * Retrieves all off the payment plans from the data source.
     * @return A List of PaymentPlan objects representing all of the payment plans in the data source.
     * @throws Exception The specific exceptions that are thrown may vary per implementation.
     */
    List<PaymentPlan> getAllPaymentPlans() throws Exception;

    /**
     * Retrieves all off the payments from the data source.
     * @return A List of Payment objects representing all of the payments in the data source.
     * @throws Exception The specific exceptions that are thrown may vary per implementation.
     */
    List<Payment> getAllPayments() throws Exception;
}