package function.integration;

import com.example.math.LogSystem;
import com.example.math.MathFunction;
import com.example.math.SystemFunction;
import com.example.math.TrigonometricSystem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemIntegrationTest {

    @Test
    void testFullSystemPositive() {
        MathFunction logSystem = new LogSystem(
                Math::log,            // log2
                x -> Math.log(x)/Math.log(3), // log3
                Math::log10,          // log10
                Math::log              // ln
        );

        MathFunction trigSystem = new TrigonometricSystem(
                Math::tan,
                x -> 1 / Math.sin(x),
                x -> 1 / Math.tan(x),
                Math::sin,
                1e-6
        );

        MathFunction system = new SystemFunction(trigSystem, logSystem);

        double x = 2.0;
        double result = system.calculate(x);
        assertTrue(Double.isFinite(result));
    }

    @Test
    void testFullSystemNegative() {
        MathFunction logSystem = new LogSystem(
                Math::log,
                x -> Math.log(x)/Math.log(3),
                Math::log10,
                Math::log
        );

        MathFunction trigSystem = new TrigonometricSystem(
                Math::tan,
                x -> 1 / Math.sin(x),
                x -> 1 / Math.tan(x),
                Math::sin,
                1e-6
        );

        MathFunction system = new SystemFunction(trigSystem, logSystem);

        double x = -1.0;
        double result = system.calculate(x);
        assertTrue(Double.isFinite(result));
    }
}
