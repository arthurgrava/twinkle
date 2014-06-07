package org.goodfellas.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.goodfellas.model.Edge;
import org.goodfellas.model.Graph;
import org.goodfellas.model.Vertice;

public class Dijkstra {
    
    private Graph graph = null;
    private Node[] queue;
    private List<Dijkstra.Node> s = null;
    int ini =  0;
    
    public Dijkstra(final Graph graph) {
        this.graph = graph;
    }
    
    public void execute() {
        initialize();
        s = new ArrayList<Dijkstra.Node>();
        while(ini < queue.length) {
            Node u = retrieveMinimum();
            ini++;
            s.add(u.copy());
            List<Edge> adj = graph.getVertices().get(u.id).getEdges();
            for(int i = 0 ; i < u.adjacent.length ; i++) {
                double distUV = adj.get(i).getValue();
                relax(u, u.adjacent[i], distUV);
            }
        }
    }
    
    private void getAdjacent(Node u) {
        Vertice ver = graph.getVertices().get(u.id);
        List<Edge> e = ver.getEdges();
        u.adjacent = new Node[e.size()];
        for(int i = 0 ; i < e.size() ; i++) {
            u.adjacent[i] = queue[e.get(i).getVerticeTo().getId()];
        }
    }

    private void buildMinHeapify() {
        int i = (queue.length - ini) / 2;
        while(i >= 0) {
            minHeapify(i, queue.length);
            i--;
        }
    }
    
    private void minHeapify(int i, int m) {
        int esq = 2 * i;
        int dir = (2 * i) + 1;
        int menor = -1;
        if(esq < m && queue[esq].distance < queue[i].distance) {
            menor = esq;
        } else {
            menor = i;
        }
        if(dir < m && queue[dir].distance < queue[menor].distance) {
            menor = dir;
        }
        if(menor != i) {
            Node temp = queue[menor];
            queue[menor] = queue[i];
            queue[i] = temp;
            minHeapify(menor, m);
        }
    }

    private Node retrieveMinimum() {
        buildMinHeapify();
        return queue[ini];
    }

    private void initialize() {
        queue = new Node[graph.getNumVertices()];
        for(int i = 0 ; i < queue.length ; i++) {
            queue[i] = new Node(i, Double.MAX_VALUE, null);
        }
        queue[graph.getOrigin()].distance = 0.0;
        for(int i = 0 ; i < queue.length ; i++) {
            getAdjacent(queue[i]);
        }
    }
    
    private void relax(Node u, Node v, Double distUV) {
        if(v.distance > (u.distance + distUV)) {
            v.distance = (u.distance + distUV);
            v.pi = u;
        }
    }
    
    public List<Node> getPath() {
        return s;
    }
    
    public class Node {
        
        Node(int id, double distance, Node pi) {
            this.id = id;
            this.distance = distance;
            this.pi = pi;
        }
        
        Node copy () {
            return new Node(this.id, this.distance, this.pi);
        }
        
        public double distance;
        public Node pi;
        public int id;
        public Node[] adjacent;
        
        public String toString() {
            if(pi == null)
                return "[(" + id + ")," + distance + "]";
            else
                return "[(" + id + ")," + distance + "," + pi.id + "]";
        }
    }
}
