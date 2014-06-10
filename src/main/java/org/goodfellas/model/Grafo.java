package org.goodfellas.model;

import java.util.HashMap;
import java.util.Map;

public class Grafo {
    
    private Map<Integer, Vertex> vertices;
    private int numVertices;
    private int numEdges;
    private int origin;
    private int destination;
    
    public Grafo(int nV, int nE) {
        this.numVertices = nV;
        this.numEdges = nE;
        this.vertices = new HashMap<Integer, Vertex>();
    }
    
    public void connect(int vOne, int vTwo) {
        Vertex one = this.vertices.get(vOne);
        Vertex two = this.vertices.get(vTwo);
        one.addAdjacent(two);
        two.addAdjacent(one);
    }
    
    public void addVertex(int id, int x, int y) {
        Vertex v = new Vertex(id, x, y);
        this.vertices.put(id, v);
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public Map<Integer, Vertex> getVertices() {
        return vertices;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public int getNumEdges() {
        return numEdges;
    }
    
}
