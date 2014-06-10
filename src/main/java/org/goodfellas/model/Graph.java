package org.goodfellas.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    
    private Map<Integer, Vertex> vertices;
    private int numVertices;
    private int numEdges;
    private int origin;
    private int destination;
    
    public Graph(int nV, int nE) {
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
    
    public void toHtml(List<Integer> path) throws IOException {
        String fileName = "graph";
        String fileExtensions = ".json";
        
        File file = new File(fileName + fileExtensions);
        FileWriter fw = new FileWriter(file, false);
        PrintWriter pw = new PrintWriter(fw);
        
        String printedVertices=
                "var graph = {"+"\n"+
                        "\"nodes\":["+"\n";
        String printedEdges=
                "],"+"\n"+
                        "\"links\":["+"\n";
        
        boolean [] vPath = new boolean[this.vertices.size()];
        
        
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
