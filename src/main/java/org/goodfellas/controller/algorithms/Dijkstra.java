package org.goodfellas.controller.algorithms;

import java.util.List;

import org.goodfellas.controller.MinPriorityQueue;
import org.goodfellas.controller.ShortestPath;
import org.goodfellas.structure.Edge;
import org.goodfellas.structure.Graph;
import org.goodfellas.structure.Vertex;

public class Dijkstra implements ShortestPath {
    
    private Graph graph = null;
    private MinPriorityQueue queue = null;
    
    public Dijkstra(Graph graph, Vertex source) {
        this.graph = graph;
        queue = new MinPriorityQueue(graph, source);
    }
    
    @Override
    public void execute() {
        // TODO 
    }

    @Override
    public List<Edge> shortestPath() {
        // TODO
        return null;
    }

}
