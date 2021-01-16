package enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    @Test
    public void valueOf_invalidCapitalizationThrowsException() {
        // ARRANGE
        String stringToConvert = "weekly";

        // ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            // ACT
            InstallmentFrequency installmentFrequency = InstallmentFrequency.valueOf(stringToConvert);
        });
    }

    /**
     * Ensure that when converting a string to an InstallmentFrequency, if the string is not in the enum and exception
     * is thrown.
     */
    @Test
    public void valueOf_unrecognizedValueThrowsException() {
        // ARRANGE
        String stringToConvert = "ANNUALLY";

        // ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            // ACT
            InstallmentFrequency installmentFrequency = InstallmentFrequency.valueOf(stringToConvert);
        });
    }
}