import data.Debt;
import data.DebtInfo;
import data.Payment;
import data.PaymentPlan;
import org.json.JSONException;
import utilities.APIDataRetriever;
import utilities.DebtAnalysisService;

import java.net.ConnectException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        APIDataRetriever apiDataRetriever = new APIDataRetriever(
                "https://my-json-server.typicode.com/druska/trueaccord-mock-payments-api/");

        List<Debt> allDebts = new ArrayList<>();
        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        List<Payment> allPayments = new ArrayList<>();

        try {
            allDebts = apiDataRetriever.getAllDebts();
            allPaymentPlans = apiDataRetriever.getAllPaymentPlans();
            allPayments = apiDataRetriever.getAllPayments();
        } catch (ConnectException e) {
            System.out.println("Error executing application: Could not connect to the database URL.");
        } catch (InterruptedException e) {
            System.out.println("Error executing application: Process interrupted mid-way through running. Try running" +
                    " it again.");
        } catch (ParseException | JSONException e) {
            System.out.println("Error executing application: Data received from database is malformed and could not " +
                    "be parsed.");
        } catch (Exception e) { // A catch-all for exceptions
            System.out.println("Error executing application, receive exception with the following message: "
                    + e.getMessage());
        }

        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);
        List<DebtInfo> debtInfos = debtAnalysisService.generateDebtInfos();

        for (DebtInfo debtInfo : debtInfos) {
            debtInfo.print();
        }
    }
}
