package log.module;

import com.example.math.Ln;
import com.example.math.Logarithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class Log2ModuleTest {
    
    private Logarithm log2;

    @BeforeEach
    void setUp() {
        Ln ln = new Ln(1e-10);
        log2 = new Logarithm(ln, 2.0);
    }

    @Test
    void shouldThrowExceptionForNonPositiveX() {
        assertThrows(IllegalArgumentException.class, () -> log2.calculate(0.0));
        assertThrows(IllegalArgumentException.class, () -> log2.calculate(-1.0));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/log2_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValues(double x, double expected) {
        assertEquals(expected, log2.calculate(x), 1e-6);
    }
}