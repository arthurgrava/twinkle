package org.goodfellas.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.goodfellas.model.Grafo;
import org.goodfellas.model.Vertex;
import org.goodfellas.util.Utils;

public class Algorithm {
    
    private Grafo g = null;
    private Map<Integer, Vertex> vertices = null;
    private List<Integer> s = null;
    private Vertex[] queue = null;
    private int size = 0;
    
    public Algorithm(Grafo g) {
        this.g = g;
        this.vertices = g.getVertices();
        this.s = new ArrayList<Integer>(g.getNumVertices());
        this.queue = new Vertex[g.getNumVertices()];
    }
    
    public List<Integer> getPath() {
        List<Integer> path =  new ArrayList<Integer>();
        Vertex last = vertices.get(g.getDestination());
        while(last.getPi() != -1) {
            path.add(last.getId());
            last = vertices.get(last.getPi());
        }
        path.add(g.getOrigin());
        return path;
    }
    
    public void execute() {
        initialize();
        while(size > 0) {
            Vertex u = vertices.get(retrieveMinimum());
            moveToEnd(u.getId());
            s.add(u.getId());
            for(Integer key : u.getAdjacent()) {
                double distUV = Utils.euclideanDistance(u.getX(), vertices.get(key).getX(), u.getY(), vertices.get(key).getY());
                if(vertices.get(key).getDistance() > (u.getDistance() + distUV)) {
                    vertices.get(key).setDistance(u.getDistance() + distUV);
                    vertices.get(key).setPi(u.getId());
                }
            }
            size--;
            if(u.getId() == g.getDestination()) {
                return;
            }
        }
    }

    private void moveToEnd(int id) {
        Vertex temp = vertices.get(queue[size].getId());
        Vertex end = vertices.get(id);
        
        queue[0] = temp;
        queue[size] = end;
    }

    private int retrieveMinimum() {
        heapify();
        return queue[0].getId();
    }
    
    private void heapify() {
        int index = size / 2;
        while(index >= 0) {
            minHeapify(index, size);
            index--;
        }
    }
    
    private void minHeapify(int index, int size) {
        int left = 2 * index;
        int right = (2 * index) + 1;
        int small = index;
        
        if(left < size && queue[left].getDistance() < queue[small].getDistance()) {
            small = left;
        }
        if(right < size && queue[right].getDistance() < this.queue[small].getDistance()) {
            small = right;
        }
        
        if(small != index) {
            queue[index] = vertices.get(queue[small].getId());
            queue[small] = vertices.get(queue[index].getId());
            minHeapify(small, size);
        }
    }

    private void initialize() {
        for(int i = 0 ; i < this.queue.length ; i++) {
            this.queue[i] = this.vertices.get(i);
        }
        this.vertices.get(g.getOrigin()).setDistance(0.0);
        size = queue.length - 1;
    }
    
}
