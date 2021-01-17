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
        final Debt debt = new Debt(17, 37.50);

        DebtInfo debtInfo = new DebtInfo(debt,
                true,
                11.18,
                new Date(1634965200000L)); // 10/23/2021 at 5:00

        // ACT
        debtInfo.print();

        // ASSERT
        assertEquals("id: 17, amount: 37.5, is_in_payment_plan: true, remaining_amount: 11.18, " +
                "next_payment_due_date: 2021-10-23T05:00:00Z", outputStreamCaptor.toString().trim());
    }

    /**
     * Ensures that when a DebtInfo is has no payment plan, it prints null for next_payment_due_date.
     */
    @Test
    public void print_DebtInfoWithNoPaymentPlan() {
        // ARRANGE
        final Debt debt = new Debt(18, 13.37);

        DebtInfo debtInfo = new DebtInfo(debt,
                false,
                12.17,
                null);

        // ACT
        debtInfo.print();

        // ASSERT
        assertEquals("id: 18, amount: 13.37, is_in_payment_plan: false, remaining_amount: 12.17, " +
                "next_payment_due_date: null", outputStreamCaptor.toString().trim());
    }
}