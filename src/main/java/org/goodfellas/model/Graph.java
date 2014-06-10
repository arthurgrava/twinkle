package org.goodfellas.model;

import java.io.BufferedWriter;
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
        String fileName = "graph.json";
        
        String nodes = "";
        String edges = "";
        
        double maxX = 0.0;
        double maxY = 0.0;
        for(Integer key : vertices.keySet()) {
            maxX = (maxX > vertices.get(key).getX()) ? maxX : vertices.get(key).getX();
            maxY = (maxY > vertices.get(key).getY()) ? maxY : vertices.get(key).getY();
        }
        
        Vertex aux = null;
        Vertex conn = null;
        boolean isPath = false;
        for(Integer key : vertices.keySet()) {
            aux = vertices.get(key);
            isPath = path.contains(aux.getId());
            double x = aux.getX() / maxX * 960.0;
            double y = aux.getY() / maxY * 500.0;
            
            // {"name":"0,[6371,1811]","group":2,x:652,y:342,fixed:true}
            if(isPath) {
                nodes += "{\"name\":\"" + aux.getId() + "\", \"group\":9, \"x\":" + y + ",\"y\":" + x + ", \"fixed\":true},";
            } else {
                nodes += "{\"name\":\"" + aux.getId() + "\", \"group\":8, \"x\":" + y + ",\"y\":" + x + ", \"fixed\":true},";
            }
            // {"source":30891,"target":30898,"value":"gray"},
            for(Integer adj : aux.getAdjacent()) {
                conn = vertices.get(adj);
                if(isPath && path.contains(conn.getId())) {
                    edges +=  "{\"source\":" + aux.getId() + ",\"target\":" + conn.getId() + ",\"value\":\"red\"},";
                } else {
                    edges += "{\"source\":" + aux.getId() + ",\"target\":" + conn.getId() + ",\"value\":\"gray\"},";
                }
                conn.removeAdjacent(aux.getId());
            }
        }
        nodes = nodes.substring(0, nodes.length() - 1);
        edges = edges.substring(0, edges.length() - 1);
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName), false));
        bw.write("var graph = { \"nodes\":[" + nodes + "], \"links\":[" + edges + "]}");
        bw.flush();
        bw.close();
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
