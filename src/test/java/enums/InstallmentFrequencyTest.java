package enums;

import org.junit.Test;
import static org.junit.Assert.assertSame;

public class InstallmentFrequencyTest {
    @Test
    public void whenGivenStringWEEKLY_thenConvertsToEnum() {
        String stringToConvert = "WEEKLY";
        InstallmentFrequency installmentFrequency = InstallmentFrequency.valueOf(stringToConvert);
        assertSame(installmentFrequency, InstallmentFrequency.WEEKLY);
    }

    @Test
    public void whenGivenStringBI_WEEKLY_thenConvertsToEnum() {
        String stringToConvert = "BI_WEEKLY";
        InstallmentFrequency installmentFrequency = InstallmentFrequency.valueOf(stringToConvert);
        assertSame(installmentFrequency, InstallmentFrequency.BI_WEEKLY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGivenInvalidCapitolization_thenThrowsException() {
        String stringToConvert = "WeEKlY";
        InstallmentFrequency installmentFrequency = InstallmentFrequency.valueOf(stringToConvert);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGivenUnrecognizedString_thenThrowsException() {
        String stringToConvert = "ANNUALLY";
        InstallmentFrequency installmentFrequency = InstallmentFrequency.valueOf(stringToConvert);
    }
}