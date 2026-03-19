package trig.integration;

import com.example.math.Cos;
import com.example.math.Sin;
import com.example.math.Tan;
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
class TanIntegrationTest {

    private static final double EPS = 1e-6;

    @Mock
    private Sin mockSin;   // мок для подмены значений

    @Spy
    private Sin spySin;  // spy для проверки реальных вызовов

    @Mock
    private Cos mockCos;   // мок для подмены значений

    private Cos cos;    // spy для проверки реальных вызовов (создаётся вручную)

    private Tan tan;

    @BeforeEach
    void setUp() {
        cos = spy(new Cos(spySin));
        tan = spy(new Tan(spySin, cos, EPS));
        // Настраиваем lenient stub для mock, чтобы избежать UnnecessaryStubbing
        lenient().when(mockSin.calculate(anyDouble()))
                .thenAnswer(invocation -> Math.sin((Double) invocation.getArgument(0)));
        lenient().when(mockCos.calculate(anyDouble()))
                .thenAnswer(invocation -> Math.cos((Double) invocation.getArgument(0)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/tan_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValuesUsingSpy(double x, double expected) {
        if (Double.isNaN(expected)) {
            assertThrows(ArithmeticException.class, () -> tan.calculate(x));
        } else {
            assertEquals(expected, tan.calculate(x), EPS);
        }

        // Проверяем вызовы spy
        verify(spySin, atLeastOnce()).calculate(x);
        verify(cos, atLeastOnce()).calculate(x);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/tan_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValuesUsingMock(double x, double expected) {
        // Tan с mock бинами
        Tan tanWithMock = new Tan(mockSin, mockCos, 1e-10);

        if (Double.isNaN(expected)) {
            when(mockCos.calculate(x)).thenReturn(0.0);  // чтобы вызвать исключение
            when(mockSin.calculate(x)).thenReturn(1.0);
            assertThrows(ArithmeticException.class, () -> tanWithMock.calculate(x));
        } else {
            assertEquals(expected, tanWithMock.calculate(x), EPS);
        }

        // Проверяем вызовы mock
        verify(mockSin, atLeastOnce()).calculate(x);
        verify(mockCos, atLeastOnce()).calculate(x);
    }
}