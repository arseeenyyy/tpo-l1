package function.integration;

import com.example.math.LogSystem;
import com.example.math.MathFunction;
import com.example.math.SystemFunction;
import com.example.math.TrigonometricSystem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;

class SystemIntegrationTest {

    private MathFunction tanMock;
    private MathFunction cscMock;
    private MathFunction cotMock;
    private MathFunction sinMock;

    private MathFunction log2Mock;
    private MathFunction log3Mock;
    private MathFunction log10Mock;
    private MathFunction lnMock;

    private TrigonometricSystem trigSystem;
    private LogSystem logSystem;
    private SystemFunction systemFunction;

    @BeforeEach
    void setUp() {
        // Создаем моки для тригонометрической системы
        tanMock = mock(MathFunction.class);
        cscMock = mock(MathFunction.class);
        cotMock = mock(MathFunction.class);
        sinMock = mock(MathFunction.class);

        trigSystem = new TrigonometricSystem(tanMock, cscMock, cotMock, sinMock, 1e-6);

        // Создаем моки для логарифмической системы
        log2Mock = mock(MathFunction.class);
        log3Mock = mock(MathFunction.class);
        log10Mock = mock(MathFunction.class);
        lnMock = mock(MathFunction.class);

        logSystem = new LogSystem(log2Mock, log3Mock, log10Mock, lnMock);

        // Система, которая использует обе подсистемы
        systemFunction = new SystemFunction(trigSystem, logSystem);
    }

    @Test
    void testSystemUsesTrigonometricForNegative() {
        // Настраиваем моки
        when(tanMock.calculate(-1)).thenReturn(2.0);
        when(cscMock.calculate(-1)).thenReturn(1.0);
        when(cotMock.calculate(-1)).thenReturn(3.0);
        when(sinMock.calculate(-1)).thenReturn(0.5);

        double result = systemFunction.calculate(-1);

        assertTrue(Double.isFinite(result));

        // Проверяем, что вызвались только тригонометрические моки
        verify(tanMock).calculate(-1);
        verify(cscMock).calculate(-1);
        verify(cotMock).calculate(-1);
        verify(sinMock).calculate(-1);

        verifyNoInteractions(log2Mock, log3Mock, log10Mock, lnMock);
    }

    @Test
    void testSystemUsesLogForPositive() {
        // Настраиваем моки
        when(log2Mock.calculate(2)).thenReturn(1.0);
        when(log3Mock.calculate(2)).thenReturn(0.63);
        when(log10Mock.calculate(2)).thenReturn(0.3);
        when(lnMock.calculate(2)).thenReturn(0.69);

        double result = systemFunction.calculate(2);

        assertTrue(Double.isFinite(result));

        // Проверяем, что вызвались только логарифмические моки
        verify(log2Mock).calculate(2);
        verify(log3Mock).calculate(2);
        verify(log10Mock).calculate(2);
        verify(lnMock).calculate(2);

        verifyNoInteractions(tanMock, cscMock, cotMock, sinMock);
    }

    @Test
    void testSystemAtZeroUsesTrigonometric() {
        when(tanMock.calculate(0)).thenReturn(1.0);
        when(cscMock.calculate(0)).thenReturn(2.0);
        when(cotMock.calculate(0)).thenReturn(1.5);
        when(sinMock.calculate(0)).thenReturn(0.5);

        double result = systemFunction.calculate(0);

        assertTrue(Double.isFinite(result));

        verify(tanMock).calculate(0);
        verify(cscMock).calculate(0);
        verify(cotMock).calculate(0);
        verify(sinMock).calculate(0);

        verifyNoInteractions(log2Mock, log3Mock, log10Mock, lnMock);
    }
}