package org.goodfellas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.goodfellas.algorithm.Dijkstra.Node;
import org.goodfellas.model.Edge;
import org.goodfellas.model.Graph;
import org.goodfellas.model.Vertice;

public class GraphOperations {

    public void printPath(final List<Edge> path, final Node last) {
        for(int i = path.size() - 1 ; i >= 0 ; i--) {
            Edge e = path.get(i);
            System.out.println(e.getVerticeFrom().getId() + " " + e.getVerticeTo().getId());
        }
        System.out.println();
        System.out.printf("%.1f\n", last.distance); 
    }
    
    public List<Edge> retrievePath(final Graph graph, final List<Node> nodes) {
        List<Edge> path = new ArrayList<Edge>(graph.getNumVertices());
        Map<Integer, Node> map = getNodeMap(nodes);
        Map<Integer, Vertice> vertices = graph.getVertices();
        
        int origin = graph.getOrigin();
        int dest = graph.getDestination();
        
        while(dest != origin) {
            Node node = map.get(dest);
            if(node.pi == null)
                path.add(new Edge(vertices.get(origin), vertices.get(dest), 1));
            else 
                path.add(new Edge(vertices.get(node.pi.id), vertices.get(dest), 1));
            dest = node.pi.id;
        }
        
        return path;
    }

    private Map<Integer, Node> getNodeMap(final List<Node> nodes) {
        Map<Integer, Node> map = new HashMap<Integer, Node>();
        for(Node node : nodes) {
            map.put(node.id, node);
        }
        return map;
    }


}