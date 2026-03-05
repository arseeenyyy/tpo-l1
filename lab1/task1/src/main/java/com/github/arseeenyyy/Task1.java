package com.github.arseeenyyy;

public class Task1 {

    public static double cos(double x) {
        double sumNew, sumOld, sum;
        int i = 0;
        sum = sumNew = 1.0;  
        
        do {
            sumOld = sumNew;
            i += 2;  
            sum = sum * x * x / (i * (i - 1));  
            sum = -sum;  
            sumNew = sumOld + sum;
            
        } while (sumNew != sumOld);  
        
        System.out.println(String.format("Taylor: %f | Math.cos: %f", sumNew, Math.cos(x)));
        return sumNew;
    }
}