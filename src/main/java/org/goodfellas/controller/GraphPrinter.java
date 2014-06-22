package org.goodfellas.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.goodfellas.structure.Edge;
import org.goodfellas.structure.Graph;
import org.goodfellas.structure.Vertex;
import org.goodfellas.util.Constants;

public class GraphPrinter {
    
    public void toJson(Stack<Edge> path, Graph graph) throws IOException {
        final String fileName = "graph.json";
        
        double maxX = 0.0;
        double maxY = 0.0;
        Map<Integer, Vertex> map = graph.getVertices();
        Set<Integer> keys = map.keySet();
        
        for(Integer key : keys) {
            maxX = (maxX > map.get(key).getX()) ? maxX : map.get(key).getX();
            maxY = (maxY > map.get(key).getY()) ? maxY : map.get(key).getY();
        }
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName), false));
        bw.append("var graph = { \"nodes\":[");
        
        Vertex current = null;
        for(Integer key : keys) {
            current = map.get(key);
            double x = current.getX() / maxX * 960.0;
            double y = current.getY() / maxY * 500.0;
            
            bw.append("{\"id\":\"" + current.getId() + "\", \"x\":" + x + ",\"y\":" + (-(y - 500)) + "},");
        }
        
        bw.append("], \"links\":[");
        
        Vertex adjacent = null;
        for(Integer key : keys) {
            current = map.get(key);
            // just to remember from = current and to = adjacent
            for(Edge edge : current.getAdjacent()) {
                adjacent = edge.getTo();
                bw.append("{\"from\":" + current.getId() + ",\"to\":" + adjacent.getId() + ",\"color\":\"gray\"},");
            }
        }
        
        for(Edge edge : path) {
            bw.append("{\"from\":" + edge.getFrom().getId() + ",\"to\":" + edge.getTo().getId() + ",\"color\":\"red\"},");
        }
        
        bw.write("]}");
        bw.flush();
        bw.close();
    }
    
    public void printPath(Stack<Edge> path, Vertex origin, Vertex destination, long timeToRun) {
        Edge edge = null;
        System.out.println("===========================");
        System.out.println(origin.getId() + " " + destination.getId() + "\n");
        for(int i = path.size() - 1 ; i >= 0 ; i--) {
            edge = path.get(i);
            System.out.println(edge.getTo().getId() + " " + edge.getFrom().getId());
        }
        System.out.println(String.format("\n%.1f", destination.getSlot(Constants.DISTANCE, Double.class)));
        System.out.println(timeToRun);
    }
    
}
