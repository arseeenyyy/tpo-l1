package trig.module;

import com.example.math.Cos;
import com.example.math.Sin;
import com.example.math.Tan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class TanModuleTest {

    private Tan tan;
    private static final double EPS = 1e-10;

    @BeforeEach
    void setUp() {
        Sin sin = new Sin(EPS);
        Cos cos = new Cos(sin);
        tan = new Tan(sin, cos, EPS);
    }

    @Test
    void shouldHandleBoundaryValues() {
        assertThrows(ArithmeticException.class, () -> tan.calculate(Math.PI / 2));
        assertThrows(ArithmeticException.class, () -> tan.calculate(-Math.PI / 2));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/tan_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValues(double x, double expected) {
        assertEquals(expected, tan.calculate(x), EPS);
    }
}
