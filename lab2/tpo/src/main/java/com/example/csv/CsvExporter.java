package com.example.csv;

import com.example.math.MathFunction;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public class CsvExporter {
    public void export(MathFunction function, double from, double to, double step, Path out, String separator) throws IOException {
        if (step <= 0) {
            throw new IllegalArgumentException("Step must be positive");
        }
        
        try (BufferedWriter writer = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {
            // header
            writer.write("X");
            writer.write(separator);
            writer.write("Result");
            writer.newLine();
            
            double x = from;
            if (from <= to) {
                while (x <= to) {
                    writeLine(writer, x, function, separator);
                    x += step;
                }
            } else {
                while (x >= to) {
                    writeLine(writer, x, function, separator);
                    x -= step;
                }
            }
        }
    }
    
    private void writeLine(BufferedWriter writer, double x, MathFunction function, String separator) throws IOException {
        double y;
        try {
            y = function.calculate(x);
        } catch (Exception e) {
            y = Double.NaN;  
        }
        
        writer.write(Double.toString(x));
        writer.write(separator);
        writer.write(Double.toString(y));
        writer.newLine();
    }
}