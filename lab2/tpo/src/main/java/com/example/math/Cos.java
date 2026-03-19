package com.example.math;

// cos = sin(x + pi/2)
public class Cos implements MathFunction {
    private final MathFunction sin;

    public Cos(MathFunction sin) {
        this.sin = sin; 
    }

    @Override 
    public double calculate(double x) {
        return sin.calculate(x + Math.PI / 2);
    }
}
