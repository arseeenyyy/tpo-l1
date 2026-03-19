package com.example.math;

// tan(x) = sin(x) / cos(x) 
// x != pi/2 + p*k
public class Tan implements MathFunction{
    private final MathFunction sin;
    private final MathFunction cos;
    private final double epsilon;

    public Tan(MathFunction sin, MathFunction cos, double epsilon) {
        this.sin = sin;
        this.cos = cos;
        this.epsilon = epsilon;
    }

    @Override 
    public double calculate(double x) {
        if (Math.abs(cos.calculate(x)) < epsilon) {
            throw new ArithmeticException("tan(x) is undef for cos(x) ~= 0");
        }
        return sin.calculate(x) / cos.calculate(x);
    }
}
