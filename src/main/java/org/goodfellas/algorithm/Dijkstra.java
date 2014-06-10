package org.goodfellas.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.goodfellas.model.Edge;
import org.goodfellas.model.Graph;
import org.goodfellas.model.Vertice;
import org.goodfellas.util.Utils;

public class Dijkstra {
    
    private Graph graph = null;
    private Node[] queue;
    private Map<Integer, Node> nodes = null;
    private List<Integer> s = null;
    int ini =  0;
    
    public Dijkstra(final Graph graph) {
        this.graph = graph;
        nodes = new HashMap<Integer, Node>();
    }
    
    public void execute() {
        initialize();
        s = new ArrayList<Integer>(graph.getNumVertices());
        while(ini < queue.length) {
            Node u = retrieveMinimum();
            ini++;
            s.add(u.id);
            List<Edge> adj = graph.getVertices().get(u.id).getEdges();
            for(int i = 0 ; i < u.adjacent.length ; i++) {
                double distUV = adj.get(i).getValue();
                relax(u, this.nodes.get(u.adjacent[i]), distUV);
            }
        }
    }
    
    private void buildMinHeapify() {
        int i = (queue.length - ini) / 2;
        while(i >= 0) {
            minHeapify(i, queue.length);
            i--;
        }
    }
    
    private void minHeapify(int i, int size) {
        int left = 2 * i;
        int right = (2 * i) + 1;
        int small = i;
        if(left < size && this.queue[left].distance < this.queue[small].distance) {
            small = left;
        }
        if(right < size && this.queue[right].distance < this.queue[small].distance) {
            small = right;
        }
        if(small != i) {
            Node temp = new Node(this.queue[small].id, this.queue[small].distance, this.queue[small].pi, this.queue[small].adjacent);
            this.queue[small] = this.queue[i];
            this.queue[i] = temp;
            this.nodes.put(temp.id, temp);
            minHeapify(small, size);
//            Node temp = new Node(this.queue[small].id, this.queue[small].distance, this.queue[small].pi);
//            this.queue[small] = this.queue[i];
//            this.queue[i] = temp;
//            minHeapify(small, size);
        }
    }

    private Node retrieveMinimum() {
        buildMinHeapify();
        return queue[ini];
    }

    private void initialize() {
        this.queue = new Node[graph.getNumVertices()];
        
        Map<Integer, Vertice> vertices = graph.getVertices();
        Set<Integer> keys = vertices.keySet();
        
        for(Integer key : keys) {
            Vertice v = vertices.get(key);
            int[] adjacent = getAdjacent(v);
            Node node = new Node(v.getId(), Double.MAX_VALUE, -1, adjacent);
            this.queue[key] = node;
            this.nodes.put(v.getId(), node);
        }
//        
//        for(int i = 0 ; i < keys.length ; i++) {
//            Vertice v = vertices.get(keys[i]);
//            int[] adjacent = getAdjacent(v);
//            Node node = new Node(v.getId(), Double.MAX_VALUE, -1, adjacent);
//            this.queue[keys[i]] = node;
//            this.nodes.put(v.getId(), node);
//        }
        
        this.queue[graph.getOrigin()].distance = 0.0;
    }
    
    private int[] getAdjacent(Vertice v) {
        List<Edge> e = v.getEdges();
        int adj[] = new int[e.size()];
        for(int i = 0 ; i < e.size() ; i++) {
            adj[i] = e.get(i).getVerticeTo().getId();
        }
        return adj;
    }

    private void relax(Node u, Node v, Double distUV) {
        if(v.distance > (u.distance + distUV) || (v.distance == Double.MAX_VALUE && u.distance == Double.MAX_VALUE)) {
            v.distance = (u.distance + distUV);
            v.pi = u.id;
        }
    }
    
    public List<Node> getPath() {
        List<Node> array = new ArrayList<Node>(s.size());
        for(Integer i : s) {
            array.add(this.nodes.get(i));
        }
        return array;
    }
    
    public class Node {
        
        public int id;
        public int pi;
        public double distance;
        public int[] adjacent;
        
        public Node(int id, double distance, int pi, int[] adjacent) {
            this.id = id;
            this.distance = distance;
            this.pi = pi;
            this.adjacent = Utils.copyArray(adjacent);
        }
        
        public Node copy(Node node) {
            return new Node(node.id, node.distance, node.pi, node.adjacent);
        }
    }
}
