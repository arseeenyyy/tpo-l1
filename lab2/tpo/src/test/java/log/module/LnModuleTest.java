package log.module;

import com.example.math.Ln;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class LnModuleTest {
    
    private Ln ln;

    @BeforeEach
    void setUp() {
        ln = new Ln(1e-10);
    }

    @Test
    void shouldThrowExceptionForNonPositiveX() {
        assertThrows(IllegalArgumentException.class, () -> ln.calculate(0.0));
        assertThrows(IllegalArgumentException.class, () -> ln.calculate(-1.0));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ln_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValues(double x, double expected) {
        assertEquals(expected, ln.calculate(x), 1e-6);
    }
}
