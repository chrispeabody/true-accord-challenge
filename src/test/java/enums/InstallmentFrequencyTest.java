package enums;

import org.junit.Test;
import static org.junit.Assert.assertSame;

public class InstallmentFrequencyTest {
    /**
     * Ensure that when converting a string to an InstallmentFrequency, the string "WEEKLY" is converted correctly.
     */
    @Test
    public void valueOf_stringWEEKLYConvertsToEnum() {
        String stringToConvert = "WEEKLY"; // ARRANGE
        InstallmentFrequency installmentFrequency = InstallmentFrequency.valueOf(stringToConvert); // ACT
        assertSame(installmentFrequency, InstallmentFrequency.WEEKLY); // ASSERT
    }

    /**
     * Ensure that when converting a string to an InstallmentFrequency, the string "BI_WEEKLY" is converted correctly.
     */
    @Test
    public void valueOf_stringBIWEEKLYConvertsToEnum() {
        String stringToConvert = "BI_WEEKLY"; // ARRANGE
        InstallmentFrequency installmentFrequency = InstallmentFrequency.valueOf(stringToConvert); // ACT
        assertSame(installmentFrequency, InstallmentFrequency.BI_WEEKLY); // ASSERT
    }

    /**
     * Ensure that when converting a string to an InstallmentFrequency, if capitalization does not match exactly, an
     * exception is thrown.
     */
    @Test(expected = IllegalArgumentException.class) // ASSERT
    public void valueOf_invalidCapitalizationThrowsException() {
        String stringToConvert = "weekly"; // ARRANGE
        InstallmentFrequency installmentFrequency = InstallmentFrequency.valueOf(stringToConvert); // ACT
    }

    /**
     * Ensure that when converting a string to an InstallmentFrequency, if the string is not in the enum and exception
     * is thrown.
     */
    @Test(expected = IllegalArgumentException.class) // ASSERT
    public void valueOf_unrecognizedValueThrowsException() {
        String stringToConvert = "ANNUALLY"; // ARRANGE
        InstallmentFrequency installmentFrequency = InstallmentFrequency.valueOf(stringToConvert); // ACT
    }
}