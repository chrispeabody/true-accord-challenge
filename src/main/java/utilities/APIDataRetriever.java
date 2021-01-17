package utilities;

import data.Debt;
import data.Payment;
import data.PaymentPlan;
import enums.InstallmentFrequency;
import interfaces.DataRetriever;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * This class is a DataRetriever that gets it's data using an HTTP API.
 */
public class APIDataRetriever implements DataRetriever {
    /**
     * This is the base URL that the API calls will extend. It is assumed the leaf portions of the HTTP requests
     * (i.e. 'debts') remain consistent regardless of the root url used. Expecting a '/' at the end.
     */
    private final String rootApiUrl;
    private final HttpClient httpClient;
    private final SimpleDateFormat dateFormat;

    public APIDataRetriever(String rootApiUrl) {
        this.rootApiUrl = rootApiUrl;
        this.httpClient = HttpClient.newBuilder().build();

        // Note: The format the endpoint is returning does not match the spec description. The spec says dates will be
        // in ISO 8601, but the dates are being returned in yyyy-MM-dd format. The input here can be changed to ISO 8601
        // if the endpoint is modified to return what the spec describes.
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Since the endpoint is not telling us time zone, we'll assume UTC.
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Retrieves all debts from the API data source using a HTTP GET request. Debts are retrieved in JSON format, then
     * unpacked into a List of Debt data objects and returned.
     * @return A List of Debt objects representing all of the debts in the data source.
     * @throws IOException In httpClient.send if inputs are malformed, though this shouldn't occur here.
     * @throws ConnectException If your internet is down when running this function, or if the root url returns a non
     * state 'OK' code (200).
     * @throws InterruptedException If the HttpRequest is cut off midway through processing.
     * @throws JSONException if the json objects returned by the endpoint are malformed.
     */
    public List<Debt> getAllDebts() throws IOException,  ConnectException, InterruptedException, JSONException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "debts"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new ConnectException();
        }

        JSONArray jsonDebts = new JSONArray(response.body());

        List<Debt> unpackedDebts = new ArrayList<>();
        for (int index = 0; index < jsonDebts.length(); index++) {
            JSONObject jsonDebt = jsonDebts.getJSONObject(index);
            Debt unpackedDebt = new Debt(jsonDebt.getInt("id"), jsonDebt.getDouble("amount"));
            unpackedDebts.add(unpackedDebt);
        }

        return unpackedDebts;
    }

    /**
     * Retrieves all payment plans from the data source using a HTTP GET request. Payment plans are retrieved in JSON
     * format, then unpacked into a List of PaymentPlan data objects and returned.
     * @return A List of PaymentPlan objects representing all of the payment plans in the data source.
     * @throws IOException In httpClient.send if inputs are malformed, though this shouldn't occur here.
     * @throws ConnectException If your internet is down when running this function, or if the root url returns a non
     * status 'OK' code (200).
     * @throws InterruptedException If the HttpRequest is cut off midway through processing.
     * @throws JSONException If the json objects returned by the endpoint are malformed.
     * @throws ParseException If the date in the json is not formatted as expected.
     */
    public List<PaymentPlan> getAllPaymentPlans() throws IOException,  ConnectException, InterruptedException,
            JSONException, ParseException  {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "payment_plans"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new ConnectException();
        }

        JSONArray jsonPaymentPlans = new JSONArray(response.body());

        List<PaymentPlan> unpackedPaymentPlans = new ArrayList<>();
        for (int index = 0; index < jsonPaymentPlans.length(); index++) {
            JSONObject jsonPaymentPlan = jsonPaymentPlans.getJSONObject(index);
            Date startDate = dateFormat.parse(jsonPaymentPlan.getString("start_date"));
            PaymentPlan unpackedDebt = new PaymentPlan(
                    jsonPaymentPlan.getInt("id"),
                    jsonPaymentPlan.getInt("debt_id"),
                    jsonPaymentPlan.getDouble("amount_to_pay"),
                    InstallmentFrequency.valueOf(jsonPaymentPlan.getString("installment_frequency")),
                    jsonPaymentPlan.getDouble("installment_amount"),
                    startDate);
            unpackedPaymentPlans.add(unpackedDebt);
        }

        return unpackedPaymentPlans;
    }

    /**
     * Retrieves all payments from the data source using a HTTP GET request. Payments are retrieved in JSON format, then
     * unpacked into a List of Payment data objects and returned.
     * @return A List of Payment objects representing all of the payments in the data source.
     * @throws IOException In httpClient.send if inputs are malformed, though this shouldn't occur here.
     * @throws ConnectException If your internet is down when running this function, or if the root url returns a non
     * state 'OK' code (200).
     * @throws InterruptedException If the HttpRequest is cut off midway through processing.
     * @throws JSONException if the json objects returned by the endpoint are malformed.
     * @throws ParseException If the date in the json is not formatted as expected.
     */
    public List<Payment> getAllPayments() throws IOException, ConnectException, InterruptedException, JSONException,
            ParseException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "payments"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new ConnectException();
        }

        JSONArray jsonPayments = new JSONArray(response.body());

        List<Payment> unpackedPayments = new ArrayList<>();
        for (int index = 0; index < jsonPayments.length(); index++) {
            JSONObject jsonPayment = jsonPayments.getJSONObject(index);
            Date date = dateFormat.parse(jsonPayment.getString("date"));
            Payment unpackedPayment = new Payment(
                    jsonPayment.getInt("payment_plan_id"),
                    jsonPayment.getDouble("amount"),
                    date);
            unpackedPayments.add(unpackedPayment);
        }

        return unpackedPayments;
    }
}