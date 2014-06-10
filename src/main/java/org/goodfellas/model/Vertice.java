package org.goodfellas.model;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.goodfellas.util.Utils;

public class Vertice {

    private int id;
    private int x;
    private int y;
    
    private double distance;

    private List<Edge> edges;
    Map<Integer, Vertice> connections;

    public Vertice(int id, int x, int y) {

        this.id = id;
        this.x = x;
        this.y = y;

        this.edges = new ArrayList<Edge>();
        this.connections = new HashMap<Integer, Vertice>();
    }


    public void add(Graph graph, Vertice vertice) {

        if (graph == null) throw new IllegalArgumentException("Graph must not be null.");
        if (vertice == null) throw new IllegalArgumentException("Vertice must not be null.");

        double value = Utils.euclideanDistance(this.getX(), vertice.getX(), this.getY(), vertice.getY());

        Edge edge = new Edge(this, vertice, value);
//        Edge edge2 = new Edge(vertice, this, value);
        
        edges.add(edge);
//        vertice.getEdges().add(edge2);

        graph.addVertice(vertice);

    }
    
    public Vertice[] getAdjacent() {
        Vertice[] adj = new Vertice[this.edges.size()];
        for(int i = 0 ; i < this.edges.size() ; i++) {
            adj[i] = this.edges.get(i).getVerticeTo();
        }
        return adj;
    }
    
    @Override
    public String toString() {
        return "[" + this.getId() + "(" + this.getX() + "," + this.getY() + ")]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void addEdge(Edge edge) {
        if(!connections.containsKey(edge.getVerticeTo().getId())) {
            this.edges.add(edge);
            connections.put(edge.getVerticeTo().getId(), edge.getVerticeTo());
        }
    }

}