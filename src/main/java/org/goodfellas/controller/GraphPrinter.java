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
        
        Vertex from = null;
        Vertex to = null;
        double fromX = -1.0;
        double fromY = -1.0;
        double toX = -1.0;
        double toY = -1.0;

        bw.append("var graph = { \"edges\":[");

        for(Integer key : keys) {
            from = map.get(key);
            fromX = from.getX() / maxX * 960.0;
            fromY = from.getY() / maxY * 500.0;

            // just to remember from = current and to = adjacent
            // {"from":{"x": 0, "y": 3},"to":{ "x": 0, "y": 150.0}}
            for(Edge edge : from.getAdjacent()) {
                to = edge.getTo();
                toX = to.getX() / maxX * 960.0;
                toY = to.getY() / maxY * 500.0;
                bw.append("{\"from\":{\"x\": " + fromX + ", \"y\": " + (-(fromY - 500)) + "},\"to\":{ \"x\": " + toX + ", \"y\": " + (-(toY - 500)) + "}},");
            }
        }
        
        bw.append("],\"path\":[");
        
        for(Edge edge : path) {
            from = edge.getFrom();
            to = edge.getTo();
            
            fromX = from.getX() / maxX * 960.0;
            fromY = from.getY() / maxY * 500.0;
            toX = to.getX() / maxX * 960.0;
            toY = to.getY() / maxY * 500.0;
            
            bw.append("{\"from\":{\"x\": " + fromX + ", \"y\": " + (-(fromY - 500)) + "},\"to\":{ \"x\": " + toX + ", \"y\": " + (-(toY - 500)) + "}},");
        }

        bw.write("],\"colors\":{\"edges\": \"rgba(20, 7, 71, 0.7)\", \"path\": \"rgba(216, 43, 34, 0.4)\", \"vertices\": \"rgba(20, 7, 71, 0.7)\"}}");
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
