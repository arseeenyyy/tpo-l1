package com.example.math;

// csc = 1 / sin(x)
// x != pi * k
public class Csc implements MathFunction {
    private final Sin sin;
    private final double epslion;

    public Csc(Sin sin, double epsilon) {
        this.sin = sin;
        this.epslion = epsilon;
    }

    public double calculate(double x) {
        if (Math.abs(sin.calculate(x)) < epslion) {
            throw new ArithmeticException("csc(x) is undef for sin(x) ~= 0");
        }
        return 1.0 / sin.calculate(x);
    }
}