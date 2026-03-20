package trig.integration;

import com.example.math.Sin;
import com.example.math.Cos;
import com.example.math.Cot;
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
class CotIntegrationTest {

    private static final double EPS = 1e-6;

    @Mock
    private Sin mockSin;

    @Mock
    private Cos mockCos;

    private Cot mockCot;

    @Spy
    private Sin spySin = new Sin(1e-10);

    @Spy
    private Cos spyCos = new Cos(spySin);

    private Cot cotSpy;

    @BeforeEach
    void setUp() {
        mockCot = new Cot(mockSin, mockCos, 1e-10);
        cotSpy = spy(new Cot(spySin, spyCos, 1e-10));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cot_sin_cos_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValuesUsingMock(double x, Double cotExpected, double sinValue, double cosValue) {
        if (cotExpected == null || Math.abs(sinValue) < 1e-10) {
            assertThrows(ArithmeticException.class, () -> mockCot.calculate(x));
            return;
        }
        
        when(mockSin.calculate(x)).thenReturn(sinValue);
        when(mockCos.calculate(x)).thenReturn(cosValue);
        
        double result = mockCot.calculate(x);
        
        assertEquals(cotExpected, result, EPS);
        verify(mockSin, atLeastOnce()).calculate(x);
        verify(mockCos, atLeastOnce()).calculate(x);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cot_sin_cos_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValuesUsingSpy(double x, Double expected, double sinValue, double cosValue) {
        if (expected == null || Math.abs(sinValue) < 1e-10) {
            assertThrows(ArithmeticException.class, () -> cotSpy.calculate(x));
            return;
        }
        double result = cotSpy.calculate(x);
        assertEquals(expected, result, EPS);
        verify(spySin, atLeastOnce()).calculate(x);
        verify(spyCos, atLeastOnce()).calculate(x);
    }
}