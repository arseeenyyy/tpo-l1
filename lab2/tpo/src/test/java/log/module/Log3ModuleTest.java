package log.module;

import com.example.math.Ln;
import com.example.math.Logarithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class Log3ModuleTest {

    private Logarithm log3;
    @BeforeEach
    void setUp() {
        Ln ln = new Ln(1e-10);
        log3 = new Logarithm(ln, 3.0);
    }

    @Test
    void shouldThrowExceptionForNonPositiveX() {
        assertThrows(IllegalArgumentException.class, () -> log3.calculate(0.0));
        assertThrows(IllegalArgumentException.class, () -> log3.calculate(-1.0));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/log3_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValues(double x, double expected) {
        assertEquals(expected, log3.calculate(x), 1e-6);
    }
}