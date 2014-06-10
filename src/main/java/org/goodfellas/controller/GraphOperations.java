package org.goodfellas.controller;

import java.util.List;
import java.util.Map;

import org.goodfellas.model.Vertex;

public class GraphOperations {
    
    public void print(final List<Integer> path, final int origin, final int destination, final Map<Integer, Vertex> vertices) {
        String line = "";
        
        int prev = -1;
        int actual = -1;
        for(int i = path.size() - 2 ; i >= 0 ; i--) {
            prev = path.get(i + 1);
            actual = path.get(i);
            
            line += prev + " " + actual + "\n";
        }
        
        line += "\n";
        
        double dist = vertices.get(destination).getDistance();
        line += String.format("%.1f\n", dist);
        
        if(actual == destination) {
            System.out.println(line);
        } else {
            System.out.println("There is no path between " + origin + " and " + destination);
        }
    }
    

}