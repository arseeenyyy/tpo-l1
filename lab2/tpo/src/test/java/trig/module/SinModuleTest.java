package trig.module;

import com.example.math.Sin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class SinModuleTest {

    private Sin sin;
    private static final double EPS = 1e-10;

    @BeforeEach
    void setUp() {
        sin = new Sin(EPS);
    }

    @Test
    void shouldHandleBoundaryValues() {
        assertEquals(0.0, sin.calculate(0), EPS);
        assertEquals(0.0, sin.calculate(Math.PI), 1e-6);
        assertEquals(1.0, sin.calculate(Math.PI / 2), 1e-6);
        assertEquals(-1.0, sin.calculate(-Math.PI / 2), 1e-6);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/sin_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValues(double x, double expected) {
        assertEquals(expected, sin.calculate(x), EPS);
    }
}
