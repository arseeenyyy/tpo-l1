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

    private static final double EPS = 1e-10;

    @Mock
    private Sin mockSin;

    private Cos mockCos;

    @Spy
    private Sin spySin;

    private Cos cosSpy;

    @BeforeEach
    void setUp() {
        mockCos = new Cos(mockSin);
        cosSpy = spy(new Cos(spySin));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cos_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValuesUsingMock(double x, double expected) {
        double argToSin = x + Math.PI / 2;

        when(mockSin.calculate(argToSin)).thenReturn(expected);
        mockCos = new Cos(mockSin);
        double result = mockCos.calculate(x);

        assertEquals(expected, result, EPS);
        verify(mockSin, times(1)).calculate(argToSin);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cos_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValuesUsingSpy(double x, double expected) {
        assertEquals(expected, cosSpy.calculate(x), EPS);
        verify(spySin, atLeastOnce()).calculate(anyDouble());
    }
}