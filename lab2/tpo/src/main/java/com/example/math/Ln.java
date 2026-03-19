package com.example.math;

public class Ln implements MathFunction {
    private final double epslion;

    public Ln(double epsilon) {
        if (epsilon <= 0) {
            throw new IllegalArgumentException("epsilon must be > 0");
        }
        this.epslion = epsilon;
    }

    public double calculate(double x) {
        if (x <= 0) {
            throw new IllegalArgumentException("ln(x) is defined only for x > 0");
        }

        double y = (x - 1) / (x + 1);
        double y2 = Math.pow(y, 2);

        double term = y;
        double sum = 0;
        int n = 0;

        while (Math.abs(term) > epslion) {
            sum += term / (2 * n + 1);
            term *= y2;
            n ++;
            if (n > 1000000) break;
        }
        return 2 * sum;
    }
}
