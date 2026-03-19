package com.example.math;

// tan(x) = cos(x) / sin(x) 
// x != pi * k
public class Cot implements MathFunction {
    private final Cos cos;
    private final Sin sin;
    private final double epslion;

    public Cot(Sin sin, Cos cos, double epsilon) {
        this.sin = sin;
        this.cos = cos;
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
