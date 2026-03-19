package trig.integration;

import com.example.math.Cos;
import com.example.math.Sin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CosIntegrationTest {

    private static final double EPS = 1e-6;

    @Mock
    private Sin mockSin;  // мок для подмены возвращаемых значений

    @Spy
    private Sin spySin; // spy для проверки реальных вызовов

    private Cos cos;

    @BeforeEach
    void setUp() {
        cos = spy(new Cos(spySin));
        // lenient stub для mockSin, чтобы избежать UnnecessaryStubbing
        lenient().when(mockSin.calculate(anyDouble()))
                .thenAnswer(invocation -> Math.sin((Double) invocation.getArgument(0)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cos_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValuesUsingSpy(double x, double expected) {
        // проверка с использованием spy
        assertEquals(expected, cos.calculate(x), EPS);

        // проверяем, что метод calculate на spySin реально вызвался
        verify(spySin, atLeastOnce()).calculate(anyDouble());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cos_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValuesUsingMock(double x, double expected) {
        // создаём Cos с mockSin
        Cos cosWithMock = new Cos(mockSin);

        // проверка с использованием mockSin
        assertEquals(Math.cos(x), cosWithMock.calculate(x), EPS);

        // проверяем, что метод calculate на mockSin вызвался
        verify(mockSin, atLeastOnce()).calculate(anyDouble());
    }
}