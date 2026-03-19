package com.example.math;

public class SystemFunction implements MathFunction {
    private final MathFunction trigonometricSystem;
    private final MathFunction logSystem;

    public SystemFunction(MathFunction trigonometricSystem, MathFunction logSystem) {
        this.trigonometricSystem = trigonometricSystem;
        this.logSystem = logSystem;
    }

    @Override
    public double calculate(double x) {
        if (x <= 0) {
            return trigonometricSystem.calculate(x);
        } else {
            return logSystem.calculate(x);
        }
    }
}