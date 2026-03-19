package com.example;

import com.example.app.FunctionFactory;
import com.example.csv.CsvExporter;
import com.example.math.MathFunction;

import java.nio.file.Path;


public class Main {
    public static void main(String[] args) {
        if (args.length < 6) {
            printUsage();
            return;
        }

        try {
            String functionName = args[0];
            double from = Double.parseDouble(args[1]);
            double to = Double.parseDouble(args[2]);
            double step = Double.parseDouble(args[3]);
            double epsilon = Double.parseDouble(args[4]);
            Path out = Path.of(args[5]);
            String separator = args.length >= 7 ? args[6] : ";";

            FunctionFactory factory = new FunctionFactory(epsilon);
            MathFunction function = factory.build(functionName);
            
            CsvExporter exporter = new CsvExporter();
            exporter.export(function, from, to, step, out, separator);
            
            System.out.println("results exported to: " + out.toAbsolutePath());
            
        } catch (Exception e) {
            System.err.println("error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printUsage() {
        System.out.println("Usage: <function> <from> <to> <step> <epsilon> <out> [separator]");
        System.out.println("Functions: sin, cos, tan, cot, csc, ln, log2, log3, log10, trig, log, system");
        System.out.println("Example: sin -5 5 0.1 1e-10 result.csv ;");
    }
}