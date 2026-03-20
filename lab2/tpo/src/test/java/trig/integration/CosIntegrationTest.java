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

    @Spy
    private Sin spySin;

    private Cos cos;

    @BeforeEach
    void setUp() {
        cos = spy(new Cos(spySin));
        lenient().when(mockSin.calculate(anyDouble()))
                .thenAnswer(invocation -> Math.sin((Double) invocation.getArgument(0)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cos_reference.csv", numLinesToSkip = 1)
    void shouldMatchReferenceValuesUsingSpy(double x, double expected) {
        assertEquals(expected, cos.calculate(x), EPS);
        verify(spySin, atLeastOnce()).calculate(anyDouble());
    }
}