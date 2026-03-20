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

    private static final double EPS = 1e-10;

    @Mock
    private Sin mockSin;

    @Spy
    private Sin spySin;

    @Mock
    private Cos mockCos;

    private Cos cos;

    private Tan tan;

    @BeforeEach
    void setUp() {
        cos = spy(new Cos(spySin));
        tan = spy(new Tan(spySin, cos, EPS));

        lenient().when(mockSin.calculate(anyDouble()))
                .thenAnswer(invocation -> Math.sin((Double) invocation.getArgument(0)));
        lenient().when(mockCos.calculate(anyDouble()))
                .thenAnswer(invocation -> Math.cos((Double) invocation.getArgument(0)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/tan_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValuesUsingSpy(double x, double expected) {
        assertEquals(expected, tan.calculate(x), EPS);
        verify(spySin, atLeastOnce()).calculate(x);
        verify(cos, atLeastOnce()).calculate(x);
    }
}