package trig.module;

import com.example.math.Cos;
import com.example.math.Sin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class CosModuleTest {

    private Cos cos;
    private static final double EPS = 1e-10;

    @BeforeEach
    void setUp() {
        Sin sin = new Sin(EPS);
        cos = new Cos(sin);
    }

    @Test
    void shouldHandleBoundaryValues() {
        assertEquals(1.0, cos.calculate(0), EPS);
        assertEquals(-1.0, cos.calculate(Math.PI), 1e-6);
        assertEquals(0.0, cos.calculate(Math.PI / 2), 1e-6);
        assertEquals(0.0, cos.calculate(-Math.PI / 2), 1e-6);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cos_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValues(double x, double expected) {
        assertEquals(expected, cos.calculate(x), EPS);
    }
}
