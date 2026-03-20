package function.integration;

import com.example.math.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SystemIntegrationTest {

    @Mock
    private TrigonometricSystem trigSystem;

    @Mock
    private LogSystem logSystem;

    private static final double EPS = 1e-6;

    @ParameterizedTest
    @CsvFileSource(resources = "/func.csv", numLinesToSkip = 1)
    void testSystemFunction(double x, double expected) {

        if (x <= 0) {
            lenient().when(trigSystem.calculate(x)).thenReturn(expected);
        } else {
            lenient().when(logSystem.calculate(x)).thenReturn(expected);
        }

        SystemFunction systemFunction = new SystemFunction(trigSystem, logSystem);
        double result = systemFunction.calculate(x);

        assertEquals(expected, result, 1e-6);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/func.csv", numLinesToSkip = 1)
    void spyTestSystemFunction(double x, double expected) {
        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Csc csc = new Csc(sin, EPS);
        Tan tan = new Tan(sin, cos, EPS);
        Cot cot = new Cot(sin, cos, EPS);
        MathFunction trig = new TrigonometricSystem(tan, csc, cot, sin, EPS);
        Ln ln = new Ln(EPS);
        MathFunction log = new LogSystem(new Logarithm(ln, 2), new Logarithm(ln, 3), new Logarithm(ln, 10), ln);
        SystemFunction systemFunction = new SystemFunction(trig, log);

        double result = systemFunction.calculate(x);
        assertEquals(expected, result, 1e-6);
    }
}