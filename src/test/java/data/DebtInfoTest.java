package data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

public class DebtInfoTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    /**
     * Runs before each test to set our system output to a text stream we can look at.
     */
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /**
     * Run after each test to set our system outback back to normal.
     */
    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    /**
     * Ensures that when a DebtInfo is fully initialized with data it prints fully.
     */
    @Test
    public void print_fullyInitializedDebtInfo() {
        // ARRANGE
        Debt debt = new Debt();
        debt.setId(17);
        debt.setAmount(37.50);

        DebtInfo debtInfo = new DebtInfo();
        debtInfo.setDebt(debt);
        debtInfo.setIsInPaymentPlan(true);
        debtInfo.setRemainingAmount(11.18);
        debtInfo.setNextPaymentDueDate(new Date(1634965200000L)); // 10/23/2021 at 5:00

        // ACT
        debtInfo.print();

        // ASSERT
        assertEquals("id: 17, amount: 37.50, is_in_payment_plan: true, remaining_amount: 11.18, " +
                "next_payment_due_date: 2021-10-23T05:00:00Z", outputStreamCaptor.toString().trim());
    }

    /**
     * Ensures that when a DebtInfo is has no payment plan, it prints null for next_payment_due_date.
     */
    @Test
    public void print_DebtInfoWithNoPaymentPlan() {
        // ARRANGE
        Debt debt = new Debt();
        debt.setId(18);
        debt.setAmount(13.17);

        DebtInfo debtInfo = new DebtInfo();
        debtInfo.setDebt(debt);
        debtInfo.setIsInPaymentPlan(false);
        debtInfo.setRemainingAmount(12.17);
        debtInfo.setNextPaymentDueDate(null);

        // ACT
        debtInfo.print();

        // ASSERT
        assertEquals("id: 18, amount: 13.37, is_in_payment_plan: true, remaining_amount: 12.27, " +
                "next_payment_due_date: null", outputStreamCaptor.toString().trim());
    }

    /**
     * Ensures that if a DebtInfo is printed with a missing Debt, it throws an IllegalStateException.
     */
    @Test
    public void print_DebtInfoWithMissingDebt() {
        // ARRANGE
        DebtInfo debtInfo = new DebtInfo();
        debtInfo.setIsInPaymentPlan(false);
        debtInfo.setRemainingAmount(12.17);
        debtInfo.setNextPaymentDueDate(null);

        // ACT && ASSERT
        assertThrows(IllegalStateException.class, debtInfo::print);
    }

    /**
     * Ensures that if a DebtInfo is printed with a Debt missing an id, it throws an IllegalStateException.
     */
    @Test
    public void print_DebtInfoWithDebtMissingId() {
        // ARRANGE
        Debt debt = new Debt();
        debt.setAmount(13.17);

        DebtInfo debtInfo = new DebtInfo();
        debtInfo.setDebt(debt);
        debtInfo.setIsInPaymentPlan(false);
        debtInfo.setRemainingAmount(12.17);
        debtInfo.setNextPaymentDueDate(null);

        // ACT && ASSERT
        assertThrows(IllegalStateException.class, debtInfo::print);
    }

    /**
     * Ensures that if a DebtInfo is printed with a Debt missing an amount, it throws an IllegalStateException.
     */
    @Test
    public void print_DebtInfoWithDebtMissingAmount() {
        // ARRANGE
        Debt debt = new Debt();
        debt.setId(15);

        DebtInfo debtInfo = new DebtInfo();
        debtInfo.setDebt(debt);
        debtInfo.setIsInPaymentPlan(false);
        debtInfo.setRemainingAmount(12.17);
        debtInfo.setNextPaymentDueDate(null);

        // ACT && ASSERT
        assertThrows(IllegalStateException.class, debtInfo::print);
    }

    /**
     * Ensures that if a DebtInfo is printed while missing isInPaymentPlan, it throws an IllegalStateException.
     */
    @Test
    public void print_DebtInfoMissingIsInPaymentPlan() {
        // ARRANGE
        Debt debt = new Debt();
        debt.setId(15);
        debt.setAmount(13.17);

        DebtInfo debtInfo = new DebtInfo();
        debtInfo.setDebt(debt);
        debtInfo.setRemainingAmount(12.17);
        debtInfo.setNextPaymentDueDate(null);

        // ACT && ASSERT
        assertThrows(IllegalStateException.class, debtInfo::print);
    }

    /**
     * Ensures that if a DebtInfo is printed while missing remainingAmount, it throws an IllegalStateException.
     */
    @Test
    public void print_DebtInfoMissingRemainingAmount() {
        // ARRANGE
        Debt debt = new Debt();
        debt.setId(15);
        debt.setAmount(13.17);

        DebtInfo debtInfo = new DebtInfo();
        debtInfo.setDebt(debt);
        debtInfo.setIsInPaymentPlan(false);
        debtInfo.setNextPaymentDueDate(null);

        // ACT && ASSERT
        assertThrows(IllegalStateException.class, debtInfo::print);
    }
}