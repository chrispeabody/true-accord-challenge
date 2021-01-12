package utilities;

import data.Debt;
import data.Payment;
import data.PaymentPlan;
import interfaces.DataRetriever;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a DataRetriever that gets it's data using an HTTP API.
 */
public class APIDataRetriever implements DataRetriever {
    /**
     * This is the base URL that the API calls will extend. It is assumed the leaf portions of the HTTP requests
     * (i.e. 'debts') remain consistent regardless of the root url used.
     */
    private final String rootApiUrl;

    public APIDataRetriever(String rootApiUrl) {
        this.rootApiUrl = rootApiUrl;
    }

    /**
     * Retrieves all debts from the API data source using a HTTP GET request. Debts are retrieved in JSON format, then
     * unpacked into a List of Debt data objects and returned.
     * @return A List of Debt objects representing all of the debts in the data source.
     */
    public List<Debt> GetAllDebts() {
        List<Debt> debts = new ArrayList<>();
        // stub
        return debts;
    }

    /**
     * Retrieves all payment plans from the data source using a HTTP GET request. Payment plans are retrieved in JSON
     * format, then unpacked into a List of PaymentPlan data objects and returned.
     * @return A List of PaymentPlan objects representing all of the payment plans in the data source.
     */
    public List<PaymentPlan> GetAllPaymentPlans() {
        List<PaymentPlan> paymentPlans = new ArrayList<>();
        // stub
        return paymentPlans;
    }

    /**
     * Retrieves all payments from the data source using a HTTP GET request. Payments are retrieved in JSON format, then
     * unpacked into a List of Payment data objects and returned.
     * @return A List of Payment objects representing all of the payments in the data source.
     */
    public List<Payment> GetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        // stub
        return payments;
    }
}