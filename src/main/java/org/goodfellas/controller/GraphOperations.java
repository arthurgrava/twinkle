package org.goodfellas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.goodfellas.algorithm.Dijkstra.Node;
import org.goodfellas.model.Edge;
import org.goodfellas.model.Graph;
import org.goodfellas.model.Vertex;
import org.goodfellas.model.Vertice;

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
    
    public void printPath(final List<Edge> path, final Node destination, final Node origin) {
        if(!path.isEmpty()) {
            if(path.get(path.size() - 1).getVerticeFrom().getId() != origin.id) {
                System.out.println("There is no path between " + origin.id + " and " + destination.id);
                return;
            }
            for(int i = path.size() - 1 ; i >= 0 ; i--) {
                Edge e = path.get(i);
                System.out.println(e.getVerticeFrom().getId() + " " + e.getVerticeTo().getId());
            }
            System.out.println();
            System.out.printf("%.1f\n", destination.distance);
        } else {
            System.out.println("There is no path between " + origin.id + " and " + destination.id);
        }
    }
    
    public List<Edge> retrievePath(final Graph graph, final List<Node> nodes, final Map<Integer, Node> map) {
        List<Edge> path = new ArrayList<Edge>(graph.getNumVertices());
        Map<Integer, Vertice> vertices = graph.getVertices();
        
        int origin = graph.getOrigin();
        int dest = graph.getDestination();
        
        while(dest != origin) {
            Node node = map.get(dest);
            if(node.pi == -1) {
                dest = origin;
            } else { 
                path.add(new Edge(vertices.get(node.pi), vertices.get(dest), 1));
                dest = node.pi;
            }
        }
        
        return path;
    }

    public Map<Integer, Node> getNodeMap(final List<Node> nodes) {
        Map<Integer, Node> map = new HashMap<Integer, Node>();
        for(Node node : nodes) {
            map.put(node.id, node);
        }
        return map;
    }


}