package org.goodfellas.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    
    private Map<Integer, Vertex> vertices = null;
    private Map<String, Object> properties = null;
    private List<Edge> edges = null;
    
    // TODO - setting initial capacity is good for do not allocate more memory than needed
    
    public Graph(int numVertices, int numEdges) {
        vertices = new HashMap<>(numVertices);
        edges = new ArrayList<>(numEdges * 2); // (* 2) bidirectional
        properties = new HashMap<>();
    }
    
    public int getNumVertices() {
        return this.vertices.size();
    }
    
    public int getNumEdges() {
        return this.edges.size();
    }
    
    public void addVertex(Vertex vertex) {
        this.vertices.put(vertex.getId(), vertex);
    }
    
    public Map<Integer, Vertex> getVertices() {
        return this.vertices;
    }
    
    public void addEdge(Vertex from, Vertex to) {
        this.edges.add(from.addAdjacent(to));
        this.edges.add(to.addAdjacent(from));
    }
    
    public void addProperty(String key, Object value) {
        this.properties.put(key, value);
    }
    
    public <T> T removeProperty(String key, Class<T> clazz) {
        return clazz.cast(this.properties.remove(key));
    }
    
    public <T> T getProperty(String key, Class<T> clazz) {
        return clazz.cast(this.properties.get(key));
    }
    
}
