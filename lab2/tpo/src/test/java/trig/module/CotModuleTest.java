package trig.module;

import com.example.math.Cos;
import com.example.math.Cot;
import com.example.math.Sin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class CotModuleTest {

    private Cot cot;
    private static final double EPS = 1e-10;

    @BeforeEach
    void setUp() {
        Sin sin = new Sin(EPS);
        Cos cos = new Cos(sin);
        cot = new Cot(sin, cos, EPS);
    }

    @Test
    void shouldHandleBoundaryValues() {
        // undefined
        assertThrows(ArithmeticException.class, () -> cot.calculate(0));
        assertThrows(ArithmeticException.class, () -> cot.calculate(Math.PI));
        assertThrows(ArithmeticException.class, () -> cot.calculate(-Math.PI));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cot_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValues(double x, Double expected) {
        assertEquals(expected, cot.calculate(x), EPS);
    }
}
