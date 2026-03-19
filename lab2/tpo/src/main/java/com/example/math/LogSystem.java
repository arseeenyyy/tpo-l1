package com.example.math;

// (((((log10(x) * log2(x)) + log3(x)) + ln(x)) * ((log3(x) * ln(x)) * log3(x))) * log3(x))

public class LogSystem implements MathFunction {
    private final MathFunction log2;
    private final MathFunction log3;
    private final MathFunction log10;
    private final MathFunction ln;

    public LogSystem(MathFunction log2, MathFunction log3, MathFunction log10, MathFunction ln) {
        this.log2 = log2;
        this.log3 = log3;
        this.log10 = log10;
        this.ln = ln;
    }

    @Override
    public double calculate(double x) {
        if (x <= 0) {
            throw new IllegalArgumentException("log in undef");
        }

        double log2Val = log2.calculate(x);
        double log3Val = log3.calculate(x);
        double log10Val = log10.calculate(x);
        double lnVal = ln.calculate(x);

        // log10(x) * log2(x)
        double step1 = log10Val * log2Val;
        
        // step1 + log3(x)
        double step2 = step1 + log3Val;
        
        // step2 + ln(x)
        double step3 = step2 + lnVal;
        
        // log3(x) * ln(x)
        double step4 = log3Val * lnVal;
        
        // step4 * log3(x)
        double step5 = step4 * log3Val;
        
        // step3 * step5
        double step6 = step3 * step5;
        
        // step6 * log3(x)
        return step6 * log3Val;
    }
}