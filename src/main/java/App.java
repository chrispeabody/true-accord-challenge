import data.Debt;
import data.DebtInfo;
import data.Payment;
import data.PaymentPlan;
import utilities.APIDataRetriever;
import utilities.DebtAnalysisService;

import java.util.List;

public class App {
    public static void main(String[] args) {
        APIDataRetriever apiDataRetriever = new APIDataRetriever(
                "https://my-json-server.typicode.com/druska/trueaccord-mock-payments-api/");

        List<Debt> allDebts = apiDataRetriever.getAllDebts();
        List<PaymentPlan> allPaymentPlans = apiDataRetriever.getAllPaymentPlans();
        List<Payment> allPayments = apiDataRetriever.getAllPayments();

        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);
        List<DebtInfo> debtInfos = debtAnalysisService.generateDebtInfos();

        for (DebtInfo debtInfo : debtInfos) {
            debtInfo.print();
        }
    }
}
