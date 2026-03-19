package com.example.math;

// log_a(x) = ln(x) / ln(a) 
public class Logarithm implements MathFunction {
    private final MathFunction ln;
    private final double base;
    private final double lnbase;

    public Logarithm(MathFunction ln, double base) {
        if (base <= 0 || base == 1.0) {
            throw new IllegalArgumentException("log base must be > 0 and != 1");
        }
        this.ln = ln;
        this.base = base;
        this.lnbase = ln.calculate(base);
    }

    @Override
    public double calculate(double x) {
        if (x <= 0) {
            throw new IllegalArgumentException("log argument must be > 0");
        }
        return ln.calculate(x) / lnbase;
    }
    
}
