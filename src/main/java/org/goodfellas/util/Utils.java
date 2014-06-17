package org.goodfellas.util;

public class Utils {
    
    // forces the class to be static
    private Utils() { }
    
    public static double euclidianDistance(int x1, int x2, int y1, int y2) {
        return Math.sqrt(Math.pow((x1 - x2), 2.0) + Math.pow((y1 - y2), 2.0));
    }
    
}
