package org.goodfellas.structure;

import java.util.HashMap;
import java.util.Map;

import org.goodfellas.util.Constants;
import org.goodfellas.util.Utils;

public class FibonacciMinPriorityQueue {
    
    private FibonacciProperties h;
    private int heapSize = 0;
    // this map was created to suppress the problem of swap,
    // when using the heap index to index vertexes.
    private Map<Integer, Integer> vertexHeapMap;

    public FibonacciMinPriorityQueue(Graph graph, Vertex source, Vertex destination) {

        heapSize = graph.getNumVertices();
        vertexHeapMap = new HashMap<Integer, Integer>(graph.getNumVertices());
        h = new FibonacciProperties();

        source.addSlot(Constants.ESTIMATIVE, Utils.euclidianDistance(source, destination));
        source.addSlot(Constants.DISTANCE, 0.0);
        insert(source);

        for (Vertex v : graph.getVertices().values()) {
            v.addSlot(Constants.PI, null);
            v.addSlot(Constants.DISTANCE, Double.MAX_VALUE);
            v.addSlot(Constants.ESTIMATIVE, Double.MAX_VALUE);
            vertexHeapMap.put(v.getId(), v.getId());
            if(v.getId() != source.getId())
                insert(v);
        }

        swap(source.getId(), 0);
    }

    public void insert(Vertex vertex) {
        vertex.addSlot(Constants.DEGREE, 0);
        vertex.addSlot(Constants.MARK, false);

        if(this.h.min == null) {
            h.min = vertex;
        } else {
            if(vertex.getSlot(Constants.ESTIMATIVE, Double.class) < h.min.getSlot(Constants.ESTIMATIVE, Double.class)) {
                h.min = vertex;
            }
        }
        putOnRoot(vertex);

        h.n++;
    }

    private void putOnRoot(Vertex vertex) {
        if(h.root == null) {
            h.root = vertex;
            h.root.addSlot(Constants.RIGHT, vertex);
            h.root.addSlot(Constants.LEFT, vertex);
        } else {
            Vertex temp = h.root.getSlot(Constants.RIGHT, Vertex.class);

            h.root.addSlot(Constants.RIGHT, vertex);
            vertex.addSlot(Constants.RIGHT, temp);

            temp.addSlot(Constants.LEFT, vertex);
            vertex.addSlot(Constants.LEFT, h.root);
        }
    }


    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int getSize() {
        return heapSize;
    }

    public Vertex extractMin() {
        // TODO

        return null;
    }

    public Vertex min() {
        return h.min;
    }

    public void decreaseKey(Vertex v, double distance) {

        // TODO

    }

    private Double distance(Vertex v) {
        return v.getSlot(Constants.ESTIMATIVE, Double.class);
    }

    private void swap(int i, int j) {
        // TODO
    }

    class FibonacciProperties {

        private int n = 0;
        private Vertex min = null;
        private Vertex root = null;

    }
    
}
