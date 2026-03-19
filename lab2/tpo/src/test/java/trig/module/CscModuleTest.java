package trig.module;

import com.example.math.Csc;
import com.example.math.Sin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class CscModuleTest {

    private Csc csc;
    private static final double EPS = 1e-10;

    @BeforeEach
    void setUp() {
        Sin sin = new Sin(EPS);
        csc = new Csc(sin, EPS);
    }

    @Test
    void shouldHandleBoundaryValues() {
        // undefined
        assertThrows(ArithmeticException.class, () -> csc.calculate(0));
        assertThrows(ArithmeticException.class, () -> csc.calculate(Math.PI));
        assertThrows(ArithmeticException.class, () -> csc.calculate(-Math.PI));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csc_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValues(double x, double expected) {
        assertEquals(expected, csc.calculate(x), EPS);
    }
}
