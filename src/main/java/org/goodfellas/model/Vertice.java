package org.goodfellas.model;

import java.util.List;
import java.util.ArrayList;

import org.goodfellas.util.Utils;


public class Vertice {

    private int id;
    private int x;
    private int y;

    private List<Edge> edges;

    public Vertice(int id, int x, int y) {

        this.id = id;
        this.x = x;
        this.y = y;

        this.edges = new ArrayList<Edge>();

    }

    public Vertice(int id, int x, int y, List<Edge> edges) {

        this.id = id;
        this.x = x;
        this.y = y;

        this.edges = edges;

    }


    public void add(Graph graph, Vertice vertice) {

        if (graph == null) throw new IllegalArgumentException("Graph must not be null.");
        if (vertice == null) throw new IllegalArgumentException("Vertice must not be null.");

        double value = Utils.euclideanDistance(this.getX(), vertice.getX(), this.getY(), vertice.getY());

        Edge edge = new Edge(this, vertice, value);

        edges.add(edge);

        graph.addVertice(vertice);

    }

    @Override
    public String toString() {
        String line = "[" + this.getId() + "," + this.getX() + "," + this.getY() + "]";
        if(this.edges.size() > 0) {
            line += " -> [";
            for(Edge e : this.edges) {
                line += e.getVerticeTo().getId() + "(" + e.getValue() + "),";
            }
            line = line.substring(0, line.length() - 1 ) + "]";
        }
        line += "\n";
        return line;
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

}