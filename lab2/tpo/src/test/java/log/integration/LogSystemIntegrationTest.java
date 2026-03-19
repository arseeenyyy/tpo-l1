package log.integration;

import com.example.math.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogSystemIntegrationTest {

    private static final double EPS = 1e-10;

    @Mock
    private MathFunction mockLog2;
    
    @Mock
    private MathFunction mockLog3;
    
    @Mock
    private MathFunction mockLog10;
    
    @Mock
    private MathFunction mockLn;

    @Spy
    private Ln spyLn = new Ln(EPS);

    @Test
    void shouldCallAllDependenciesWithSpy() {
        Logarithm log2 = new Logarithm(spyLn, 2.0);
        Logarithm log3 = new Logarithm(spyLn, 3.0);
        Logarithm log10 = new Logarithm(spyLn, 10.0);
        
        LogSystem logSystem = new LogSystem(log2, log3, log10, spyLn);
        
        logSystem.calculate(5.0);
        
        verify(spyLn, atLeastOnce()).calculate(anyDouble());
    }

    @Test
    void shouldCalculateCorrectlyWithMocks() {
        double x = 5.0;
        
        when(mockLog2.calculate(x)).thenReturn(2.321928);  // log2(5)
        when(mockLog3.calculate(x)).thenReturn(1.464973);  // log3(5)
        when(mockLog10.calculate(x)).thenReturn(0.698970); // log10(5)
        when(mockLn.calculate(x)).thenReturn(1.609438);    // ln(5)

        LogSystem logSystem = new LogSystem(mockLog2, mockLog3, mockLog10, mockLn);
        
        double result = logSystem.calculate(x);
        
        double expected = 23.76; 
        
        assertEquals(expected, result, 1e-2);
        
        verify(mockLog2).calculate(x);
        verify(mockLog3).calculate(x);
        verify(mockLog10).calculate(x);
        verify(mockLn).calculate(x);
    }
}