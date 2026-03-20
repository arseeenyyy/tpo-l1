package trig.integration;

import com.example.math.Sin;
import com.example.math.Cos;
import com.example.math.Tan;
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
class TanIntegrationTest {

    private static final double EPS = 1e-6;

    @Mock
    private Sin mockSin;

    @Mock
    private Cos mockCos;

    private Tan mockTan;

    @Spy
    private Sin spySin = new Sin(1e-10);

    @Spy
    private Cos spyCos = new Cos(spySin);

    private Tan tanSpy;

    @BeforeEach
    void setUp() {
        mockTan = new Tan(mockSin, mockCos, 1e-10);
        tanSpy = spy(new Tan(spySin, spyCos, 1e-10));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/tan_sin_cos_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValuesUsingMock(double x, double tanExpected, double sinValue, double cosValue) {
        if (Double.isNaN(tanExpected) || Math.abs(cosValue) < 1e-10) {
            assertThrows(ArithmeticException.class, () -> mockTan.calculate(x));
            return;
        }
        
        when(mockSin.calculate(x)).thenReturn(sinValue);
        when(mockCos.calculate(x)).thenReturn(cosValue);
        
        double result = mockTan.calculate(x);
        
        assertEquals(tanExpected, result, EPS);
        verify(mockSin, atLeastOnce()).calculate(x);
        verify(mockCos, atLeastOnce()).calculate(x);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/tan_sin_cos_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValuesUsingSpy(double x, double expected, double sinValue, double cosValue) {
        if (Double.isNaN(expected) || Math.abs(cosValue) < 1e-10) {
            assertThrows(ArithmeticException.class, () -> tanSpy.calculate(x));
            return;
        }
        
        double result = tanSpy.calculate(x);
        assertEquals(expected, result, EPS);
        verify(spySin, atLeastOnce()).calculate(x);
        verify(spyCos, atLeastOnce()).calculate(x);
    }
}