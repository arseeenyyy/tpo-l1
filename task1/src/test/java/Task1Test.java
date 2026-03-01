import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.github.arseeenyyy.Task1;

public class Task1Test {
    
    private static final double EPS = 1e-6;
    
    @Test
    void testZero() {
        assertEquals(1.0, Task1.cos(0), EPS);
    }
    
    @Test
    void testSmallValues() {
        double x = 0.001;
        assertEquals(Math.cos(x), Task1.cos(x), EPS);
    }
    
    @Test
    void testTypicalValues() {
        double[] values = {
            Math.PI / 6,   // 30 
            Math.PI / 4,   // 45  
            Math.PI / 3,   // 60  
            Math.PI / 2,   // 90  
            Math.PI,       // 180 
            2 * Math.PI    // 360 
        };
        
        for (double x : values) {
            assertEquals(Math.cos(x), Task1.cos(x), 1e-9);
        }
    }
    
    @Test
    void testNegativeValues() {
        double x = 1.3;
        assertEquals(Task1.cos(x), Task1.cos(-x), EPS);
    }
    
    @Test
    void testLargeValues() {
        double x = 20;
        assertEquals(Math.cos(x), Task1.cos(x), EPS);
    }
    
    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.8, 1.2, 1.8, 2.2})
    void testAdditionalAngles(double x) {
        assertEquals(Math.cos(x), Task1.cos(x), EPS);
    }
    
    @ParameterizedTest
    @CsvSource({
        "0.5236, 0.8660",
        "0.7854, 0.7071",
        "1.0472, 0.5"
    })
    void testSpecialAngles(double x, double expected) {
        assertEquals(expected, Task1.cos(x), 0.0001);
    }
}