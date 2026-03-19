package trig.integration;

import com.example.math.Cos;
import com.example.math.Cot;
import com.example.math.Sin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CotIntegrationTest {

    private static final double EPS = 1e-6;

    @Mock
    private Sin mockSin;

    @Spy
    private Sin spySin;

    @Mock
    private Cos mockCos;

    private Cos cos;

    private Cot cot;

    @BeforeEach
    void setUp() {
        cos = spy(new Cos(spySin));
        cot = spy(new Cot(spySin, cos, EPS));

        lenient().when(mockSin.calculate(anyDouble()))
                .thenAnswer(invocation -> Math.sin((Double) invocation.getArgument(0)));
        lenient().when(mockCos.calculate(anyDouble()))
                .thenAnswer(invocation -> Math.cos((Double) invocation.getArgument(0)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cot_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValuesUsingSpy(double x, Double expected) {
        if (expected.isNaN()) {
            assertThrows(ArithmeticException.class, () -> cot.calculate(x));
        } else {
            assertEquals(expected, cot.calculate(x), EPS);
        }

        verify(spySin, atLeastOnce()).calculate(x);
        verify(cos, atLeastOnce()).calculate(x);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cot_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValuesUsingMock(double x, Double expected) {
        Cot cotWithMock = new Cot(mockSin, mockCos, 1e-10);

        if (expected.isNaN()) {
            when(mockCos.calculate(x)).thenReturn(0.0);
            when(mockSin.calculate(x)).thenReturn(1.0); // чтобы вызвать исключение
            assertThrows(ArithmeticException.class, () -> cotWithMock.calculate(x));
        } else {
            assertEquals(expected, cotWithMock.calculate(x), EPS);
        }

        verify(mockSin, atLeastOnce()).calculate(x);
        verify(mockCos, atLeastOnce()).calculate(x);
    }
}