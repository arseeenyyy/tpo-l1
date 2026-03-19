package function.module;

import com.example.math.MathFunction;
import com.example.math.SystemFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SystemFunctionTest {

    private MathFunction trigMock;
    private MathFunction logMock;
    private SystemFunction systemFunction;

    @BeforeEach
    void setUp() {
        trigMock = mock(MathFunction.class);
        logMock = mock(MathFunction.class);
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
}
