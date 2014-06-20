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
        
        String nodes = "";
        String edges = "";
        
        double maxX = 0.0;
        double maxY = 0.0;
        Map<Integer, Vertex> map = graph.getVertices();
        Set<Integer> keys = map.keySet();
        
        for(Integer key : keys) {
            maxX = (maxX > map.get(key).getX()) ? maxX : map.get(key).getX();
            maxY = (maxY > map.get(key).getY()) ? maxY : map.get(key).getY();
        }
        
        Vertex current = null;
        Vertex adjacent = null;
        for(Integer key : keys) {
            current = map.get(key);
            double x = current.getX() / maxX * 960.0;
            double y = current.getY() / maxY * 500.0;
            
            nodes += "{\"name\":\"" + current.getId() + "\", \"group\":1, \"x\":" + y + ",\"y\":" + x + ", \"fixed\":true},";
            // just to remember from = current and to = adjacent
            for(Edge edge : current.getAdjacent()) {
                adjacent = edge.getTo();
                edges += "{\"source\":" + current.getId() + ",\"target\":" + adjacent.getId() + ",\"value\":\"gray\"},";
            }
        }
        
        for(Edge edge : path) {
            edges += "{\"source\":" + edge.getFrom().getId() + ",\"target\":" + edge.getTo().getId() + ",\"value\":\"red\"},";
        }
        
        nodes = nodes.substring(0, nodes.length() - 1);
        edges = edges.substring(0, edges.length() - 1);
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName), false));
        bw.write("var graph = { \"nodes\":[" + nodes + "], \"links\":[" + edges + "]}");
        bw.flush();
        bw.close();
    }
    
    public void printPath(Stack<Edge> path, Vertex destination) {
        Edge edge = null;
        for(int i = path.size() - 1 ; i >= 0 ; i--) {
            edge = path.get(i);
            System.out.println(edge.getFrom().getId() + " " + edge.getTo().getId());
        }
        System.out.println(destination.getSlot(Constants.PI, Double.class));
    }
    
}
