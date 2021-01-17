package utilities;

import data.Debt;
import data.DebtInfo;
import data.Payment;
import data.PaymentPlan;

import enums.InstallmentFrequency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DebtAnalysisServiceTest {
    @Test
    public void constructor_nullAllDebts() {
        // ARRANGE
        List<Debt> allDebts = null;
        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        List<Payment> allPayments = new ArrayList<>();

        // ACT && ASSERT
        assertThrows(NullPointerException.class, () -> {
            DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);
        });
    }

    @Test
    public void constructor_nullAllPaymentPlans() {
        // ARRANGE
        List<Debt> allDebts = new ArrayList<>();
        List<PaymentPlan> allPaymentPlans = null;
        List<Payment> allPayments = new ArrayList<>();

        // ACT && ASSERT
        assertThrows(NullPointerException.class, () -> {
            DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);
        });
    }

    @Test
    public void constructor_nullAllPayments() {
        // ARRANGE
        List<Debt> allDebts = new ArrayList<>();
        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        List<Payment> allPayments = null;

        // ACT && ASSERT
        assertThrows(NullPointerException.class, () -> {
            DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);
        });
    }

    @Test
    public void findPaymentPlans_oneExists() {
        // ARRANGE
        Debt debt = new Debt(42, 3.14);

        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(debt);
        List<PaymentPlan> allPaymentPlans = Arrays.asList(
                new PaymentPlan(1,
                        12,
                        3.14,
                        InstallmentFrequency.WEEKLY,
                        4.0,
                        new Date()),
                new PaymentPlan(2,
                        42,
                        3.14,
                        InstallmentFrequency.WEEKLY,
                        4.0,
                        new Date()),
                new PaymentPlan(3,
                        32,
                        3.14,
                        InstallmentFrequency.WEEKLY,
                        4.0,
                        new Date())
        );
        List<Payment> allPayments = new ArrayList<>();
        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertEquals(Integer.valueOf(2), debtAnalysisService.findPaymentPlan(debt));
    }

    @Test
    public void findPaymentPlans_twoExist() {
        // ARRANGE
        Debt debt = new Debt(42, 3.14);

        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(debt);
        List<PaymentPlan> allPaymentPlans = Arrays.asList(
                new PaymentPlan(1,
                        12,
                        3.14,
                        InstallmentFrequency.WEEKLY,
                        4.0,
                        new Date()),
                new PaymentPlan(2,
                        32,
                        3.14,
                        InstallmentFrequency.WEEKLY,
                        4.0,
                        new Date()),
                new PaymentPlan(3,
                        42,
                        3.14,
                        InstallmentFrequency.WEEKLY,
                        4.0,
                        new Date()),
                new PaymentPlan(3,
                        42,
                        3.14,
                        InstallmentFrequency.WEEKLY,
                        4.0,
                        new Date())
        );
        List<Payment> allPayments = new ArrayList<>();
        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertEquals(Integer.valueOf(3), debtAnalysisService.findPaymentPlan(debt));
    }

    @Test
    public void findPaymentPlans_noneExist() {
        // ARRANGE
        Debt debt = new Debt(42, 3.14);

        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(debt);
        List<PaymentPlan> allPaymentPlans = Arrays.asList(
                new PaymentPlan(1,
                        12,
                        3.14,
                        InstallmentFrequency.WEEKLY,
                        4.0,
                        new Date()),
                new PaymentPlan(2,
                        22,
                        3.14,
                        InstallmentFrequency.WEEKLY,
                        4.0,
                        new Date()),
                new PaymentPlan(3,
                        32,
                        3.14,
                        InstallmentFrequency.WEEKLY,
                        4.0,
                        new Date())
        );
        List<Payment> allPayments = new ArrayList<>();
        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertEquals(Integer.valueOf(2), debtAnalysisService.findPaymentPlan(debt));
    }

    @Test
    public void findPaymentPlans_nullDebt() {
        // ARRANGE
        Debt debt = null;
        List<Debt> allDebts = new ArrayList<>();
        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        List<Payment> allPayments = new ArrayList<>();
        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertThrows(NullPointerException.class, () -> {
            Integer planId = debtAnalysisService.findPaymentPlan(debt);
        });
    }

    @Test
    public void calculateRemainingAmount_noPaymentPlan() {
        // ARRANGE
        Debt debt = new Debt(42, 1000.00);
        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(debt);
        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        allPaymentPlans.add(new PaymentPlan(1,
                        12,
                        1000.00,
                        InstallmentFrequency.WEEKLY,
                        4.0,
                        new Date()));

        List<Payment> allPayments = new ArrayList<>();
        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertEquals(1000.00, debtAnalysisService.calculateRemainingAmount(debt));
    }

    @Test
    public void calculateRemainingAmount_baseCase() {
        // ARRANGE
        Debt debt = new Debt(42, 1000.00);
        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(debt);
        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        allPaymentPlans.add(new PaymentPlan(1,
                42,
                1000.00,
                InstallmentFrequency.WEEKLY,
                4.0,
                new Date()));
        List<Payment> allPayments = new ArrayList<>();
        allPayments.add(new Payment(1, 200.00, new Date(1605675600000L))); // Date is 11/18/2020
        allPayments.add(new Payment(1, 300.00, new Date(1606107600000L))); // Date is 11/23/2020
        allPayments.add(new Payment(1, 300.00, new Date(1608872400000L))); // Date is 12/25/2020
        allPayments.add(new Payment(2, 400.00, new Date(1607662800000L))); // unrelated payment
        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertEquals(200.00, debtAnalysisService.calculateRemainingAmount(debt));
    }

    @Test
    public void calculateRemainingAmount_completelyPaid() {
        // ARRANGE
        Debt debt = new Debt(42, 1000.00);
        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(debt);
        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        allPaymentPlans.add(new PaymentPlan(1,
                42,
                1000.00,
                InstallmentFrequency.WEEKLY,
                4.0,
                new Date()));
        List<Payment> allPayments = new ArrayList<>();
        allPayments.add(new Payment(1, 250.00, new Date(1605675600000L))); // Date is 11/18/2020
        allPayments.add(new Payment(1, 350.00, new Date(1606107600000L))); // Date is 11/23/2020
        allPayments.add(new Payment(1, 400.00, new Date(1608872400000L))); // Date is 12/25/2020
        allPayments.add(new Payment(2, 400.00, new Date(1607662800000L))); // unrelated payment
        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertEquals(0, debtAnalysisService.calculateRemainingAmount(debt));
    }

    @Test
    public void calculateRemainingAmount_overpaid() {
        // ARRANGE
        Debt debt = new Debt(42, 1000.00);
        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(debt);
        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        allPaymentPlans.add(new PaymentPlan(1,
                42,
                1000.00,
                InstallmentFrequency.WEEKLY,
                4.0,
                new Date()));
        List<Payment> allPayments = new ArrayList<>();
        allPayments.add(new Payment(1, 500.00, new Date(1605675600000L))); // Date is 11/18/2020
        allPayments.add(new Payment(1, 500.00, new Date(1606107600000L))); // Date is 11/23/2020
        allPayments.add(new Payment(1, 500.00, new Date(1608872400000L))); // Date is 12/25/2020
        allPayments.add(new Payment(2, 400.00, new Date(1607662800000L))); // unrelated payment
        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertEquals(0, debtAnalysisService.calculateRemainingAmount(debt));
    }

    @Test
    public void calculateRemainingAmount_nullDebt() {
        // ARRANGE
        Debt debt = null;
        List<Debt> allDebts = new ArrayList<>();
        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        allPaymentPlans.add(new PaymentPlan(1,
                42,
                1000.00,
                InstallmentFrequency.WEEKLY,
                4.0,
                new Date()));
        List<Payment> allPayments = new ArrayList<>();
        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertThrows(NullPointerException.class, () -> {
            Double remainingAmount = debtAnalysisService.calculateRemainingAmount(debt);
        });
    }

    @Test
    public void calculateNextPaymentDueDate_weeklyBaseCase() {
        // ARRANGE
        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(new Debt(17, 1200.00));

        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        PaymentPlan paymentPlan = new PaymentPlan(6,
                17,
                1200.00,
                InstallmentFrequency.WEEKLY,
                300.00,
                new Date(1603429200000L)); // 10/23/2020
        allPaymentPlans.add(paymentPlan);
        List<Payment> allPayments = new ArrayList<>();

        allPayments.add(new Payment(6, 300.00, new Date(1603429200000L))); // 10/23/2020
        allPayments.add(new Payment(6, 300.00, new Date(1604034000000L))); // 10/30/2020
        allPayments.add(new Payment(6, 300.00, new Date(1604638800000L))); // 11/6/2020

        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertEquals(new Date(1605243600000L), // 11/13/2020
                debtAnalysisService.calculateNextPaymentDueDate(paymentPlan));
    }

    @Test
    public void calculateNextPaymentDueDate_biweeklyBaseCase() {
        // ARRANGE
        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(new Debt(17, 1200.00));

        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        PaymentPlan paymentPlan = new PaymentPlan(6,
                17,
                1200.00,
                InstallmentFrequency.BI_WEEKLY,
                300.00,
                new Date(1603429200000L)); // 10/23/2020
        allPaymentPlans.add(paymentPlan);
        List<Payment> allPayments = new ArrayList<>();

        allPayments.add(new Payment(6, 300.00, new Date(1603429200000L))); // 10/23/2020
        allPayments.add(new Payment(6, 300.00, new Date(1604638800000L))); // 11/6/2020
        allPayments.add(new Payment(6, 300.00, new Date(1605848400000L))); // 11/20/2020

        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertEquals(new Date(1607058000000L), // 12/4/2020
                debtAnalysisService.calculateNextPaymentDueDate(paymentPlan));
    }

    @Test
    public void calculateNextPaymentDueDate_alreadyPaid() {
        // ARRANGE
        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(new Debt(17, 1200.00));

        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        PaymentPlan paymentPlan = new PaymentPlan(6,
                17,
                1200.00,
                InstallmentFrequency.BI_WEEKLY,
                300.00,
                new Date(1603429200000L)); // 10/23/2020
        allPaymentPlans.add(paymentPlan);
        List<Payment> allPayments = new ArrayList<>();

        allPayments.add(new Payment(6, 300.00, new Date(1603429200000L))); // 10/23/2020
        allPayments.add(new Payment(6, 300.00, new Date(1604638800000L))); // 11/6/2020
        allPayments.add(new Payment(6, 300.00, new Date(1605848400000L))); // 11/20/2020
        allPayments.add(new Payment(6, 300.00, new Date(1607058000000L))); // 12/4/2020

        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertNull(debtAnalysisService.calculateNextPaymentDueDate(paymentPlan));
    }

    @Test
    public void calculateNextPaymentDueDate_overpaid() {
        // ARRANGE
        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(new Debt(17, 1200.00));

        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        PaymentPlan paymentPlan = new PaymentPlan(6,
                17,
                1200.00,
                InstallmentFrequency.BI_WEEKLY,
                300.00,
                new Date(1603429200000L)); // 10/23/2020
        allPaymentPlans.add(paymentPlan);
        List<Payment> allPayments = new ArrayList<>();

        allPayments.add(new Payment(6, 400.00, new Date(1603429200000L))); // 10/23/2020
        allPayments.add(new Payment(6, 400.00, new Date(1604638800000L))); // 11/6/2020
        allPayments.add(new Payment(6, 400.00, new Date(1605848400000L))); // 11/20/2020
        allPayments.add(new Payment(6, 400.00, new Date(1607058000000L))); // 12/4/2020

        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertNull(debtAnalysisService.calculateNextPaymentDueDate(paymentPlan));
    }

    @Test
    public void calculateNextPaymentDueDate_latePayments() {
        // ARRANGE
        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(new Debt(17, 1200.00));

        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        PaymentPlan paymentPlan = new PaymentPlan(6,
                17,
                1200.00,
                InstallmentFrequency.BI_WEEKLY,
                300.00,
                new Date(1603429200000L)); // 10/23/2020
        allPaymentPlans.add(paymentPlan);
        List<Payment> allPayments = new ArrayList<>();

        allPayments.add(new Payment(6, 300.00, new Date(1603602000000L))); // 10/25/2020
        allPayments.add(new Payment(6, 300.00, new Date(1604725200000L))); // 11/6/2020
        allPayments.add(new Payment(6, 300.00, new Date(1606366800000L))); // 11/26/2020

        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertEquals(new Date(1607058000000L), // 12/4/2020
                debtAnalysisService.calculateNextPaymentDueDate(paymentPlan));
    }

    @Test
    public void calculateNextPaymentDueDate_noPayments() {
        // ARRANGE
        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(new Debt(17, 1200.00));

        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        PaymentPlan paymentPlan = new PaymentPlan(6,
                17,
                1200.00,
                InstallmentFrequency.BI_WEEKLY,
                300.00,
                new Date(1603429200000L)); // 10/23/2020
        allPaymentPlans.add(paymentPlan);
        List<Payment> allPayments = new ArrayList<>();

        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertEquals(new Date(1603429200000L), // 10/23/2020
                debtAnalysisService.calculateNextPaymentDueDate(paymentPlan));
    }

    @Test
    public void calculateNextPaymentDueDate_earlyPayments() {
        // ARRANGE
        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(new Debt(17, 1200.00));

        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        PaymentPlan paymentPlan = new PaymentPlan(6,
                17,
                1200.00,
                InstallmentFrequency.BI_WEEKLY,
                300.00,
                new Date(1603429200000L)); // 10/23/2020
        allPaymentPlans.add(paymentPlan);
        List<Payment> allPayments = new ArrayList<>();

        allPayments.add(new Payment(6, 300.00, new Date(1603429200000L))); // 10/23/2020
        allPayments.add(new Payment(6, 300.00, new Date(1604034000000L))); // 10/30/2020
        allPayments.add(new Payment(6, 300.00, new Date(1605675600000L))); // 11/18/2020

        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertEquals(new Date(1607058000000L), // 12/4/2020
                debtAnalysisService.calculateNextPaymentDueDate(paymentPlan));
    }

    @Test
    public void calculateNextPaymentDueDate_insufficientPayments() {
        // ARRANGE
        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(new Debt(17, 1200.00));

        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        PaymentPlan paymentPlan = new PaymentPlan(6,
                17,
                1200.00,
                InstallmentFrequency.WEEKLY,
                300.00,
                new Date(1603429200000L)); // 10/23/2020
        allPaymentPlans.add(paymentPlan);
        List<Payment> allPayments = new ArrayList<>();

        allPayments.add(new Payment(6, 300.00, new Date(1603429200000L))); // 10/23/2020
        allPayments.add(new Payment(6, 100.00, new Date(1604034000000L))); // 10/30/2020
        allPayments.add(new Payment(6, 300.00, new Date(1604638800000L))); // 11/6/2020

        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertEquals(new Date(1604638800000L), // 11/6/2020
                debtAnalysisService.calculateNextPaymentDueDate(paymentPlan));
    }

    @Test
    public void calculateNextPaymentDueDate_surplusPayment() {
        // ARRANGE
        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(new Debt(17, 1200.00));

        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        PaymentPlan paymentPlan = new PaymentPlan(6,
                17,
                1200.00,
                InstallmentFrequency.WEEKLY,
                300.00,
                new Date(1603429200000L)); // 10/23/2020
        allPaymentPlans.add(paymentPlan);
        List<Payment> allPayments = new ArrayList<>();

        allPayments.add(new Payment(6, 600.00, new Date(1603429200000L))); // 10/23/2020
        allPayments.add(new Payment(6, 300.00, new Date(1604034000000L))); // 10/30/2020

        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertEquals(new Date(1605243600000L), // 11/13/2020
                debtAnalysisService.calculateNextPaymentDueDate(paymentPlan));
    }

    @Test
    public void calculateNextPaymentDueDate_nullPaymentPlan() {
        // ARRANGE
        List<Debt> allDebts = new ArrayList<>();
        List<PaymentPlan> allPaymentPlans = new ArrayList<>();
        allPaymentPlans.add(new PaymentPlan(1,
                42,
                1000.00,
                InstallmentFrequency.WEEKLY,
                4.0,
                new Date()));
        PaymentPlan nullPaymentPlan = null;
        List<Payment> allPayments = new ArrayList<>();
        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        // ACT && ASSERT
        assertThrows(NullPointerException.class, () -> {
            Date dueDate = debtAnalysisService.calculateNextPaymentDueDate(nullPaymentPlan);
        });
    }

    @Test
    public void generateDebtInfos_variedCases() {
        // ARRANGE
        List<Debt> allDebts = new ArrayList<>();
        allDebts.add(new Debt(4, 800.00));
        allDebts.add(new Debt(17, 1200.00));
        allDebts.add(new Debt(50, 1000.00));

        List<PaymentPlan> allPaymentPlans = Arrays.asList(
                new PaymentPlan(1,
                        4,
                        800.00,
                        InstallmentFrequency.WEEKLY,
                        200.0,
                        new Date(1603429200000L)), // 10/23/2020
                new PaymentPlan(2,
                        17,
                        1200.00,
                        InstallmentFrequency.BI_WEEKLY,
                        400.00,
                        new Date(1597899600000L)) // 8/20/2020
        );
        List<Payment> allPayments = new ArrayList<>();

        allPayments.add(new Payment(1, 200.00, new Date(1603429200000L))); // 10/23/2020
        allPayments.add(new Payment(1, 200.00, new Date(1604034000000L))); // 10/30/2020
        allPayments.add(new Payment(1, 200.00, new Date(1604638800000L))); // 11/6/2020
        allPayments.add(new Payment(1, 200.00, new Date(1605243600000L))); // 11/6/2020, fully paid

        allPayments.add(new Payment(2, 400.00, new Date(1598072400000L))); // 8/22/2020
        allPayments.add(new Payment(2, 500.00, new Date(1599109200000L))); // 9/3/2020

        DebtAnalysisService debtAnalysisService = new DebtAnalysisService(allDebts, allPaymentPlans, allPayments);

        DebtInfo expectedDebtInfo1 = new DebtInfo(new Debt(4, 800.00),
                true,
                0.00,
                null); // Done being paid
        DebtInfo expectedDebtInfo2 = new DebtInfo(new Debt(17, 1200.00),
                true,
                300.00,
                new Date(1600318800000L)); // 9/17/2020
        DebtInfo expectedDebtInfo3 = new DebtInfo(new Debt(50, 1000.00),
                false,
                1000.00,
                null);

        // ACT
        List<DebtInfo> debtInfos = debtAnalysisService.generateDebtInfos();

        // ASSERT
        assertEquals(expectedDebtInfo1, debtInfos.get(0));
        assertEquals(expectedDebtInfo2, debtInfos.get(1));
        assertEquals(expectedDebtInfo3, debtInfos.get(2));
    }
}