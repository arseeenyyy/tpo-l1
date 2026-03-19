package com.example.math;

public class Sin implements MathFunction {
    private final double epsilon;

    public Sin(double epsilon) {
        if (epsilon <= 0) {
            throw new IllegalArgumentException("epsilon must be > 0");
        }
        this.epsilon = epsilon;
    }

    @Override 
    public double calculate(double x) {
        double normalized = normalizeAngle(x);

        double term = normalized; // first term of series

        double sum = term;
        int n = 1;
        while (Math.abs(term) > epsilon) {
            // -x^2/((2n)*(2n+1))
            term *= -normalized * normalized / ((2 * n) * (2 * n + 1));
            sum += term;
            n ++;

            if (n > 10000) break;
        }
        return sum;
    }

    private double normalizeAngle(double x) {
        double normalized = x % (2 * Math.PI);

        if (normalized > Math.PI) {
            normalized -= (2 * Math.PI);
        } else if (normalized < - Math.PI) {
            normalized += (2 * Math.PI);
        }
        return normalized;
    }
}
