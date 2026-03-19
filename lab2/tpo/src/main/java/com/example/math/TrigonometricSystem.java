package com.example.math;

// (((((((tan(x) - csc(x)) * cot(x)) - csc(x)) * (tan(x)^3))^3)^3) * ((sin(x)^2) - csc(x)))

public class TrigonometricSystem implements MathFunction {
    private final MathFunction tan;
    private final MathFunction csc;
    private final MathFunction cot;
    private final MathFunction sin;
    private final double epsilon;

    public TrigonometricSystem(MathFunction tan, MathFunction csc, MathFunction cot, MathFunction sin, double epsilon) {
        this.tan = tan;
        this.csc = csc;
        this.cot = cot;
        this.sin = sin;
        this.epsilon = epsilon;
    }

    @Override
    public double calculate(double x) {
        double tanVal = tan.calculate(x);
        double cscVal = csc.calculate(x);
        double cotVal = cot.calculate(x);
        double sinVal = sin.calculate(x);

        if (Math.abs(tanVal) < epsilon) {
            throw new ArithmeticException("tan is undef");
        }

        // (tan(x) - csc(x)) * cot(x)
        double step1 = (tanVal - cscVal) * cotVal;
        
        // step1 - csc(x)
        double step2 = step1 - cscVal;
        
        // tan(x)^3
        double tanCubed = Math.pow(tanVal, 3);
        
        // step2 * tan(x)^3
        double step4 = step2 * tanCubed;
        
        // (step4)^3
        double step5 = Math.pow(step4, 3);
        
        // (step5)^3
        double step6 = Math.pow(step5, 3);
        
        // sin(x)^2 - csc(x)
        double sinSquared = Math.pow(sinVal, 2);
        double step7 = sinSquared - cscVal;
        
        return step6 * step7;
    }
}