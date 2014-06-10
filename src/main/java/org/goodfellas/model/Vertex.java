package org.goodfellas.model;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    
    private int id;
    private int x;
    private int y;
    private int pi = -1;
    private double distance;
    private List<Integer> adjacent;
    
    public Vertex(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.distance = Double.MAX_VALUE;
        this.adjacent = new ArrayList<Integer>();
    }
    
    public void addAdjacent(Vertex vertex) {
        adjacent.add(vertex.getId());
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<Integer> getAdjacent() {
        return adjacent;
    }

    public void setAdjacent(List<Integer> adjacent) {
        this.adjacent = adjacent;
    }

    public int getPi() {
        return pi;
    }

    public void setPi(int pi) {
        this.pi = pi;
    }
    
    public String toString() {
        return this.id + "(" + this.distance + ")";
    }
}
