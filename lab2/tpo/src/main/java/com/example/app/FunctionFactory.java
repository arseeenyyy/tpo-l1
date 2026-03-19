package com.example.app;

import com.example.math.*;

public class FunctionFactory {
    private final double epsilon;

    public FunctionFactory(double epsilon) {
        this.epsilon = epsilon;
    }

    public MathFunction build(String name) {
        Sin sin = new Sin(epsilon);
        Ln ln = new Ln(epsilon);
        
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin, cos, epsilon);
        Cot cot = new Cot(sin, cos, epsilon);
        Csc csc = new Csc(sin, epsilon);
        
        Logarithm log2 = new Logarithm(ln, 2.0);
        Logarithm log3 = new Logarithm(ln, 3.0);
        Logarithm log10 = new Logarithm(ln, 10.0);
        
        TrigonometricSystem trigSystem = new TrigonometricSystem(tan, csc, cot, sin, epsilon);
        LogSystem logSystem = new LogSystem(log2, log3, log10, ln);
        SystemFunction system = new SystemFunction(trigSystem, logSystem);

        return switch (name.toLowerCase()) {
            case "sin" -> sin;
            case "cos" -> cos;
            case "tan" -> tan;
            case "cot" -> cot;
            case "csc" -> csc;
            case "ln" -> ln;
            case "log2" -> log2;
            case "log3" -> log3;
            case "log10" -> log10;
            case "trig" -> trigSystem;
            case "log" -> logSystem;
            case "system" -> system;
            default -> throw new IllegalArgumentException("Unknown function: " + name);
        };
    }
}