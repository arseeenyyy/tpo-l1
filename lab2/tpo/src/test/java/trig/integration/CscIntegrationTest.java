package trig.integration;

import com.example.math.Csc;
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
class CscIntegrationTest {

    private static final double EPS = 1e-6;

    @Mock
    private Sin mockSin;

    @Spy
    private Sin spySin;

    private Csc csc;

    @BeforeEach
    void setUp() {
        csc = spy(new Csc(spySin, EPS));
        lenient().when(mockSin.calculate(anyDouble()))
                .thenAnswer(invocation -> Math.sin((Double) invocation.getArgument(0)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csc_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValuesUsingSpy(double x, double expected) {
        if (Double.isNaN(expected)) {
            assertThrows(ArithmeticException.class, () -> csc.calculate(x));
        } else {
            assertEquals(expected, csc.calculate(x), EPS);
        }

         verify(spySin, atLeastOnce()).calculate(x);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csc_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValuesUsingMock(double x, double expected) {
        Csc cscWithMock = new Csc(mockSin, 1e-10);

        if (Double.isNaN(expected)) {
            when(mockSin.calculate(x)).thenReturn(0.0); // чтобы вызвать исключение
            assertThrows(ArithmeticException.class, () -> cscWithMock.calculate(x));
        } else {
            assertEquals(expected, cscWithMock.calculate(x), EPS);
        }

        verify(mockSin, atLeastOnce()).calculate(x);
    }
}