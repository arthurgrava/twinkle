package org.goodfellas.controller.algorithms;

import java.util.Stack;

import org.goodfellas.controller.ShortestPath;
import org.goodfellas.structure.Edge;
import org.goodfellas.structure.Graph;
import org.goodfellas.structure.MinPriorityQueue;
import org.goodfellas.structure.Vertex;
import org.goodfellas.util.Constants;

public class Dijkstra implements ShortestPath {

    private final Vertex source;
    private final Vertex destination;
    private MinPriorityQueue queue;
    
    public Dijkstra(Graph graph, Vertex source, Vertex destination) {
        queue = new MinPriorityQueue(graph, source);
        this.source = source;
        this.destination = destination;
    }
    
    @Override
    public void execute() {
        while (!queue.isEmpty()) {
            Vertex u = queue.extractMin();
            
            for (Edge e : u.getAdjacent()) {
                // the graph is bidirectional, so it could happen that the adjacent (getTo) is actually u,
                // instead of the adjacent
                Vertex v = (e.getTo().getId() != u.getId()) ? e.getTo() : e.getFrom();
                relax(u, v, e.getDistance());
            }
        }
    }

    @Override
    public Stack<Edge> shortestPath() {
        Stack<Edge> stack = new Stack<Edge>();

        Vertex actual = destination;

        while (actual.getId() != source.getId()) {
            Vertex ant = actual.getSlot(Constants.PI, Vertex.class);

            Edge antToActual = null;
            for (Edge e : actual.getAdjacent()) {
                if (e.getTo().getId() == ant.getId()) {
                    antToActual = e;
                    break;
                }
            }
            stack.push(antToActual);
            actual = ant;
        }

        return stack;
    }

    private void relax(Vertex u, Vertex v, double distanceUV) {
        if (distance(v) > distance(u) + distanceUV) {
            v.addSlot(Constants.DISTANCE, distance(u) + distanceUV);
            v.addSlot(Constants.PI, u);
            queue.decreaseKey(v, distance(u) + distanceUV);
        }
    }

    private Double distance(Vertex v) {
        return v.getSlot(Constants.DISTANCE, Double.class);
    }

}
