package org.goodfellas.structure;

import java.util.HashMap;
import java.util.Map;

import org.goodfellas.util.Constants;
import org.goodfellas.util.Utils;

public class FibonacciMinPriorityQueue {
    
    private FibonacciProperties h;
    // this map was created to suppress the problem of swap,
    // when using the heap index to index vertexes.
    private Map<Integer, Integer> vertexHeapMap;

    public FibonacciMinPriorityQueue(Graph graph, Vertex source, Vertex destination) {

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
    }

    public void insert(Vertex vertex) {
        vertex.addSlot(Constants.DEGREE, 0);
        vertex.addSlot(Constants.MARK, false);

        if(this.h.min == null) {
            h.min = vertex;
        } else {
            if(distance(vertex) < distance(h.min)) {
                h.min = vertex;
            }
        }
        putOnRoot(vertex);

        h.n++;
    }

    public Vertex extractMin() {
        if(h.min == null)
            return null;

        Vertex min = h.min;
        Vertex child = child(min);
        Vertex temp = null;
        while(child != null) {
            temp = right(child);
            if(temp.getId() == child. getId()) {
                min.addSlot(Constants.CHILD, null);
                temp.addSlot(Constants.RIGHT, null);
                temp.addSlot(Constants.LEFT, null);
            } else {
                removeFromRoot(temp);
                putOnRoot(temp);
            }
            temp.addSlot(Constants.PARENT, null);
        }

        if(min.getId() == right(min).getId()) {
            h.min = null;
            h.root = null;
        } else {
            removeFromRoot(min);
            consolidate();
        }

        h.n--;

        return min;
    }

    private void putOnRoot(Vertex vertex) {
        if(h.root == null) {
            h.root = vertex;
            h.root.addSlot(Constants.RIGHT, vertex);
            h.root.addSlot(Constants.LEFT, vertex);
        } else {
            Vertex temp = right(h.root);

            h.root.addSlot(Constants.RIGHT, vertex);
            vertex.addSlot(Constants.RIGHT, temp);

            temp.addSlot(Constants.LEFT, vertex);
            vertex.addSlot(Constants.LEFT, h.root);
        }
    }

    private void removeFromRoot(Vertex v) {
        Vertex temp = right(v);
        temp.addSlot(Constants.LEFT, left(v));

        temp = left(v);
        temp.addSlot(Constants.RIGHT, right(v));

        v.addSlot(Constants.LEFT, null);
        v.addSlot(Constants.RIGHT, null);
    }

    private void consolidate() {
        int dn = calculateD(h.n); // TODO - calculate
        Vertex[] array = new Vertex[dn];
        for(int i = 0 ; i < array.length ; i++) {
            array[i] = null;
        }

        Vertex right = h.root;
        do {
            int degree = degree(right);
            while(array[degree] != null) {
                Vertex y = array[degree];
                if(distance(right) > distance(y)) {
                    swap(right, y);
                }
                heapLink(right, y);
                degree = degree + 1;
            }
            array[degree] = right;
            right = right(h.root);
        } while(right.getId() != h.root.getId());
        h.min = null;
        h.root = null;
        for(int i = 0 ; i < array.length ; i++) {
            if(array[i] != null) {
                insert(array[i]);
            }
        }
    }

    private void heapLink(Vertex x, Vertex y) {
        removeFromRoot(y);
        x.addSlot(Constants.CHILD, y);
        x.addSlot(Constants.DEGREE, degree(x) + 1);
        y.addSlot(Constants.MARK, false);
    }

    public boolean isEmpty() {
        return h.n == 0;
    }

    public int getSize() {
        return h.n;
    }

    public Vertex min() {
        return h.min;
    }

    public void decreaseKey(Vertex x, double distance) {
        if (distance > distance(x))
            throw new IllegalArgumentException("Distance can't be greater than the actual distance in v.");

        x.addSlot(Constants.DISTANCE, distance);
        Vertex y = parent(x);
        if (y != null && distance(x) < distance(y)) {
            cut(x, y);
            cascadingCut(y);
        }
        if (distance(x) < distance(h.min))
            h.min = x;
    }

    private void cut(Vertex x, Vertex y) {
        removeChild(x);
        insert(x);
        x.addSlot(Constants.PARENT, null);
        x.addSlot(Constants.MARK, false);
    }

    private void cascadingCut(Vertex y) {
        Vertex z = parent(y);
        if (z != null) {
            if (!marked(y))
                y.addSlot(Constants.MARK, true);
            else {
                cut(y, z);
                cascadingCut(z);
            }
        }
    }

    private void removeChild(Vertex x) {
        Vertex y = parent(x);
        y.addSlot(Constants.CHILD, null);

        y.addSlot(Constants.DEGREE, Math.min(0, degree(y)-1));

        // fix parent pointer to child
        Vertex p = right(x);
        while (p.getId() != x.getId()) {
            y.addSlot(Constants.CHILD, p);
            break;
        }

        // fix linked list pointers
        Vertex l = left(x);
        Vertex r = right(x);
        r.addSlot(Constants.LEFT, l);
        l.addSlot(Constants.RIGHT, r);

        x.addSlot(Constants.LEFT, null);
        x.addSlot(Constants.RIGHT, null);
        x.addSlot(Constants.PARENT, null);
    }

    private boolean marked(Vertex v) {
        return v.getSlot(Constants.MARK, Boolean.class);
    }

    private Vertex child(Vertex v) {
        return v.getSlot(Constants.CHILD, Vertex.class);
    }

    private Vertex parent(Vertex v) {
        return v.getSlot(Constants.PARENT, Vertex.class);
    }

    private Vertex right(Vertex v) {
        return v.getSlot(Constants.RIGHT, Vertex.class);
    }

    private Vertex left(Vertex v) {
        return v.getSlot(Constants.LEFT, Vertex.class);
    }

    private int degree(Vertex v) {
        if(v.getSlot(Constants.DEGREE, Integer.class) == null) {
            return 0;
        } else {
            return v.getSlot(Constants.DEGREE, Integer.class);
        }
    }

    private int calculateD(int n) {
        return (int) Math.log(n);
    }

    private Double distance(Vertex v) {
        return v.getSlot(Constants.ESTIMATIVE, Double.class);
    }

    private void swap(Vertex x, Vertex y) {
        Vertex temp = y;
        y = x;
        x = temp;
    }

    class FibonacciProperties {

        private int n = 0;
        private Vertex min = null;
        private Vertex root = null;

    }
    
}
