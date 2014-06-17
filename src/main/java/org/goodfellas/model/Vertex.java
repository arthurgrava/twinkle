package org.goodfellas.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a node of the graph
 */
public class Vertex {
    
    private int id;
    private int x;
    private int y;
    
    private Map<String, Object> slots;
    private List<Edge> adjacent;
    
    public Vertex(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.slots = new HashMap<>();
        this.adjacent = new ArrayList<>();
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

    public Map<String, Object> getSlots() {
        return slots;
    }
    
    public void addSlot(String key, Object value) {
        this.slots.put(key, value);
    }
    
    public <T> T getSlot(String key, Class<T> clazz) {
        return clazz.cast(this.slots.get(key));
    }
    
    protected Edge addAdjacent(Vertex to) {
        Edge edge = new Edge(this, to);
        this.adjacent.add(edge);
        return edge;
    }
    
    public List<Edge> getAdjacent() {
        return adjacent;
    }
    
    @Override
    public String toString() {
        return this.getId() + " (" + this.getX() + ", " + this.getY() + ")";
    }
}
