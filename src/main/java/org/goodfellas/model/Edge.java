package org.goodfellas.model;

import org.goodfellas.util.Utils;

public class Edge {
    
    private double distance;
    private Vertex from;
    private Vertex to;
    
    // TODO - add some properties too?
    
    public Edge(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;
        this.distance = Utils.euclidianDistance(to.getX(), from.getX(), to.getY(), from.getY());
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Vertex getFrom() {
        return from;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public Vertex getTo() {
        return to;
    }

    public void setTo(Vertex to) {
        this.to = to;
    }
    
    @Override
    public String toString() {
        return from.getId() + " -- " + this.distance + " -> " + to.getId();
    }
}
