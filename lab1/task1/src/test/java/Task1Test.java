import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.github.arseeenyyy.Task1;

public class Task1Test {
    
    @ParameterizedTest
    @CsvFileSource(resources = "/reference_values.csv", numLinesToSkip = 1)
    void testReferenceValues(double x, double expected) {
        assertEquals(expected, Task1.cos(x), Math.E);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/periodic_values_minus2pi.csv", numLinesToSkip = 1)
    void testPeriodicPlus2Pi(double x, double expected) {
        assertEquals(expected, Task1.cos(x), Math.E);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/periodic_values_plus2pi.csv", numLinesToSkip = 1)
    void testPeriodicMinus2Pi(double x, double expected) {
        assertEquals(expected, Task1.cos(x), Math.E);
    }
}