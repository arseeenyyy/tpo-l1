package com.example.math;

// tan(x) = cos(x) / sin(x) 
// x != pi * k
public class Cot implements MathFunction {
    private final MathFunction cos;
    private final MathFunction sin;
    private final double epslion;

    public Cot(MathFunction cos, MathFunction sin, double epsilon) {
        this.cos = cos;
        this.sin = sin;
        this.epslion = epsilon;
    }

    @Override
    public double calculate(double x) {
        if (Math.abs(sin.calculate(x)) < epslion) {
            throw new ArithmeticException("cot(x) is undef for sin(x) ~=0");
        }
        return cos.calculate(x) / sin.calculate(x);
    }
}
