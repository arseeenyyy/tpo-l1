package function.module;

import com.example.math.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SystemFunctionTest {

    private MathFunction trigMock;
    private MathFunction logMock;
    private SystemFunction systemFunction;
    private static final double EPS = 1e-6;

    @BeforeEach
    void setUp() {
        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Csc csc = new Csc(sin, EPS);
        Tan tan = new Tan(sin, cos, EPS);
        Cot cot = new Cot(sin, cos, EPS);
        trigMock = new TrigonometricSystem(tan, csc, cot, sin, EPS);
        Ln ln = new Ln(EPS);
        logMock = new LogSystem(new Logarithm(ln, 2), new Logarithm(ln, 3), new Logarithm(ln, 10), ln);
        systemFunction = new SystemFunction(trigMock, logMock);
    }

    @Test
    void testCalculateNegativeUsesTrigonometric() {
        when(trigMock.calculate(-1)).thenReturn(42.0);
        assertEquals(42.0, systemFunction.calculate(-1));
        verify(trigMock).calculate(-1);
        verifyNoInteractions(logMock);
    }

    @Test
    void testCalculatePositiveUsesLogSystem() {
        when(logMock.calculate(2)).thenReturn(99.0);
        assertEquals(99.0, systemFunction.calculate(2));
        verify(logMock).calculate(2);
        verifyNoInteractions(trigMock);
    }

    @Test
    void testCalculateZeroUsesTrigonometric() {
        when(trigMock.calculate(0)).thenReturn(10.0);
        assertEquals(10.0, systemFunction.calculate(0));
        verify(trigMock).calculate(0);
        verifyNoInteractions(logMock);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/func.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValues(double x, double expected) {
        if (x <= 0) {
            assertEquals(expected, trigMock.calculate(x), EPS);
        } else {
            assertEquals(expected, logMock.calculate(x), EPS);
        }

    }
}
