package org.goodfellas.structure;

import org.goodfellas.util.Constants;
import org.goodfellas.util.Utils;

import java.util.ArrayList;

public class FibonacciMinPriorityQueue {

    private Vertex min = null;
    private int size = -1;
    private final double phi = (1.0 + Math.sqrt(5)) / 2.0;

    public FibonacciMinPriorityQueue(Graph graph, Vertex source, Vertex destination) {
        size = 0;

        for (Vertex v : graph.getVertices().values()) {
            v.addSlot(Constants.PI, null);
            v.addSlot(Constants.DISTANCE, Double.MAX_VALUE);
            v.addSlot(Constants.ESTIMATIVE, Double.MAX_VALUE);
            if(v.getId() != source.getId())
                insert(v);
        }

        source.addSlot(Constants.ESTIMATIVE, Utils.euclidianDistance(source, destination));
        source.addSlot(Constants.DISTANCE, 0.0);
        insert(source);
    }

    public void insert(Vertex v) {
        if(min != null) {
            connect(min, v);
            if(distance(min) > distance(v)) {
                min = v;
            }
        } else {
            min = v;
            min.addSlot(Constants.RIGHT, min);
            min.addSlot(Constants.LEFT, min);
        }
        size++;
    }

    private void connect(Vertex v, Vertex w) {
        w.addSlot(Constants.LEFT, v);
        w.addSlot(Constants.RIGHT, right(v));
        right(v).addSlot(Constants.LEFT, w);
        v.addSlot(Constants.RIGHT, w);
    }

    private void disconnect(Vertex v) {
        //1 <--> 0 <--> 5 => 1 <--> 5 ... 0
        if(v == null) {
            System.out.println("null");
            return;
        }
        left(v).addSlot(Constants.RIGHT, right(v));
        right(v).addSlot(Constants.LEFT, left(v));
    }

    private void addToRoot(Vertex v) {
        if(min != null) {
            connect(min, v);
        }
    }

    public Vertex extractMin() {
        Vertex minimum = this.min;

        if(minimum == null) {
            System.out.print("a");
        }

        if(minimum != null) {
            int children = degree(minimum);
            Vertex child = child(minimum);
            Vertex temp;
            while(children > 0) {
                temp = right(child);
                disconnect(child);
                addToRoot(child);
                child.addSlot(Constants.PARENT, null);
                child = temp;
                children--;
            }
        }
        // removes minimum from root
        disconnect(minimum);

        if(minimum == null || right(minimum).getId() == minimum.getId()) {
            this.min = null;
        } else {
            this.min = right(minimum);
            consolidate();
        }

        size--;

        return minimum;
    }

    public void decreaseKey(Vertex x, double distance) {
        if (distance > distance(x))
            throw new IllegalArgumentException("Distance can't be greater than the actual distance in v.");

        x.addSlot(Constants.ESTIMATIVE, distance);
        Vertex y = parent(x);
        if (y != null && distance(x) < distance(y)) {
            cut(x, y);
            cascadingCut(y);
        }
        if(this.min == null || distance(x) == null || distance(this.min) == null) {
            System.out.println();
        }
        if (distance(x) < distance(this.min))
            this.min = x;
    }

    private void removeChild(Vertex x) {
        Vertex y = parent(x);
        y.addSlot(Constants.CHILD, null);

        y.addSlot(Constants.DEGREE, Math.min(0, degree(y)-1));

        // fix parent pointer to child
        if (right(x).getId() != x.getId()) {
            y.addSlot(Constants.CHILD, right(x));
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

    private void cut(Vertex x, Vertex y) {
//        removeChild(x);
        if (right(x).getId() != x.getId()) {
            y.addSlot(Constants.CHILD, right(x));
        }
        disconnect(x);
        y.addSlot(Constants.DEGREE, Math.min(0, degree(y)-1));

        if(degree(y) == 0) {
            y.addSlot(Constants.CHILD, null);
        }

        addToRoot(x);

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

    private void consolidate() {
        int arraySize = calculateD();
        ArrayList<Vertex> array = new ArrayList<>(arraySize);
        for(int i = 0 ; i < arraySize ; i++) {
            array.add(null);
        }

        // find how many roots I will iterate
        int roots = 0;
        Vertex root = this.min;
        if(root != null) {
            roots++;
            root = right(root);
            while(root.getId() != this.min.getId()) {
                roots++;
                root = right(root);
            }
        }

        while(roots > 0) {
            int degree = degree(root);
            if(degree <= -1) {
                degree = 0;
            }
            Vertex y = array.get(degree);

            while(y != null) {
                if(distance(root) > distance(y)) {
                    exchange(root, y);
                }
                heapLink(root, y);
                array.set(degree, null);
                degree++;
//                if(degree < arraySize) {
                y = array.get(degree);
//                } else {
//                    y = null;
//                }
            }
            array.set(degree, root);
            root = right(root);
            roots--;
        }

        this.min = null;
        for(int i = 0 ; i < arraySize ; i++) {
            Vertex y = array.get(i);
            if(y != null) {
                if(this.min != null) {
                    disconnect(y);
//                    Vertex parent = parent(y);
//                    if(parent != null && child(parent).getId() == y.getId()) {
//                        if(right(y) != null && right(y).getId() != y.getId()) {
//                            parent.addSlot(Constants.CHILD, right(y));
//                        } else {
//                            parent.addSlot(Constants.CHILD, null);
//                        }
//                    }
                    y.addSlot(Constants.PARENT, null);
                    addToRoot(y);
                    if(distance(y) < distance(this.min)) {
                        this.min = y;
                    }
                } else {
                    this.min = y;
                }
            }
        }
        if(this.min == null) {
            System.out.println();
        }
    }

    private void heapLink(Vertex x, Vertex y) {
        disconnect(y);
        y.addSlot(Constants.PARENT, x);
        if(child(x) == null) {
            x.addSlot(Constants.CHILD, y);
            y.addSlot(Constants.LEFT, y);
            y.addSlot(Constants.RIGHT, y);
        } else {
            connect(child(x), y);
        }

        x.addSlot(Constants.DEGREE, degree(x) + 1);
        x.addSlot(Constants.MARK, false);
    }

    private void exchange(Vertex x, Vertex y) {
        Vertex temp = y;
        y = x;
        x = temp;
    }

    // page 523 Cormen - 3rd edition (chapter 19.4 - Bounding the maximum degree)
    private int calculateD() {
        return ((int) (Math.log(size) / Math.log(this.phi))) + 1;
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

    private Double distance(Vertex v) {
        return v.getSlot(Constants.ESTIMATIVE, Double.class);
    }

    private int degree(Vertex v) {
        if(v.getSlot(Constants.DEGREE, Integer.class) == null) {
            return 0;
        } else {
            return v.getSlot(Constants.DEGREE, Integer.class);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
