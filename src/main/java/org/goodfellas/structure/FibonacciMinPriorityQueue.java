package org.goodfellas.structure;

import org.goodfellas.util.Constants;
import org.goodfellas.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class FibonacciMinPriorityQueue {

    private int heapSize = -1;
    private Vertex min = null;
    private double phi = (1.0 + Math.sqrt(5) / 2.0);

    public FibonacciMinPriorityQueue(Graph graph, Vertex source, Vertex destination) {
        this.heapSize = 0;

        for (Vertex v : graph.getVertices().values()) {
            v.addSlot(Constants.PI, null);
            v.addSlot(Constants.DISTANCE, Double.MAX_VALUE);
            v.addSlot(Constants.ESTIMATIVE, Double.MAX_VALUE);
            if(v.getId() != source.getId()) {
                insert(v);
            }
        }

        source.addSlot(Constants.ESTIMATIVE, Utils.euclidianDistance(source, destination));
        source.addSlot(Constants.DISTANCE, 0.0);
        insert(source);
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public void insert(Vertex node) {
        if (min != null) {
            setLeft(node, min);
            setRight(node, right(min));
            setRight(min, node);
            setLeft(right(node), node);

            if (distance(node) < distance(min)) {
                min = node;
            }
        } else {
            min = node;
            setLeft(min, min);
            setRight(min, min);
        }

        heapSize++;
    }

    public Vertex extractMin() {
        Vertex z = min;

        if (z != null) {
            int numChild = degree(z);
            Vertex x = child(z);
            Vertex curr;

            while (numChild > 0) {
                curr = right(x);

                setRight(left(x), right(x));
                setLeft(right(x), left(x));

                setLeft(x, min);
                setRight(x, right(min));
                setRight(min, x);
                setLeft(right(x), x);

                setParent(x, null);
                x = curr;
                numChild--;
            }

            setRight(left(z), right(z));
            setLeft(right(z), left(z));

            if (z.getId() == right(z).getId()) {
                min = null;
            } else {
                min = right(z);
                consolidate();
            }

            heapSize--;
        }

        return z;
    }

    private void consolidate() {
        int arraySize = getD();

        List<Vertex> A = new ArrayList<>(arraySize);
        for (int i = 0; i < arraySize; i++) {
            A.add(null);
        }

        int numRoots = 0;
        Vertex x = min;

        if (x != null) {
            numRoots++;
            x = right(x);

            while (x.getId() != min.getId()) {
                numRoots++;
                x = right(x);
            }
        }

        while (numRoots > 0) {
            int d = degree(x);
            Vertex next = right(x);

            if(d >= arraySize)
                d = arraySize - 1;

            Vertex y = A.get(d);
            while (y != null && d < arraySize) {
                y = A.get(d);

                if(y == null)
                    break;

                if (distance(x) > distance(y)) {
                    Vertex temp = y;
                    y = x;
                    x = temp;
                }

                heapLink(y, x);

                A.set(d, null);
                d++;
            }

            if(d == arraySize)
                d--;

            A.set(d, x);

            x = next;
            numRoots--;
        }

        min = null;

        for (int i = 0; i < arraySize; i++) {
            Vertex y = A.get(i);
            if (y == null) {
                continue;
            }

            if (min != null) {
                setRight(left(y), right(y));
                setLeft(right(y), left(y));

                setLeft(y, min);
                setRight(y, right(min));
                setRight(min, y);
                setLeft(right(y), y);

                if (distance(y) < distance(min)) {
                    min = y;
                }
            } else {
                min = y;
            }
        }
    }


    private void heapLink(Vertex y, Vertex x) {
        setRight(left(y), right(y));
        setLeft(right(y), left(y));

        setParent(y, x);

        if (child(x) == null) {
            setChild(x, y);
            setRight(y, y);
            setLeft(y, y);
        } else {
            setLeft(y, child(x));
            setRight(y, right(child(x)));
            setRight(child(x), y);
            setLeft(right(y), y);
        }

        setDegree(x, degree(x) + 1);

        setMarked(y, false);
    }

    public void decreaseKey(Vertex x, double distance) {
        if (distance > distance(x)) {
            throw new IllegalArgumentException("decreaseKey() got larger key value");
        }

        setDistance(x, distance);

        Vertex y = parent(x);

        if ((y != null) && (distance(x) < distance(y))) {
            cut(x, y);
            cascadingCut(y);
        }

        if (distance(x) < distance(min)) {
            min = x;
        }
    }

    private void cut(Vertex x, Vertex y) {
        setRight(left(x), right(x));
        setLeft(right(x), left(x));
        setDegree(y, degree(y)-1);

        if (child(y).getId() == x.getId())
            setChild(y, right(x));

        if (degree(y) == 0)
            setChild(y, null);

        // add x to root list of heap
        setLeft(x, min);
        setRight(x, right(min));
        setRight(min, x);
        setLeft(right(x), x);

        setParent(x, null);

        setMarked(x, false);
    }

    private void cascadingCut(Vertex y) {
        Vertex z = parent(y);

        if (z != null) {
            if (!marked(y)) {
                setMarked(y, true);
            } else {
                cut(y, z);
                cascadingCut(z);
            }
        }
    }

    private int getD() {
        return ((int) Math.floor(Math.log(heapSize) / Math.log(phi))) + 1;
    }

    private Vertex right(Vertex v) {
        return v.getSlot(Constants.RIGHT, Vertex.class);
    }

    private Vertex left(Vertex v) {
        return v.getSlot(Constants.LEFT, Vertex.class);
    }

    private Vertex parent(Vertex v) {
        return v.getSlot(Constants.PARENT, Vertex.class);
    }

    private Vertex child(Vertex v) {
        return v.getSlot(Constants.CHILD, Vertex.class);
    }

    private boolean marked(Vertex v) {
        Boolean mark = v.getSlot(Constants.MARK, Boolean.class);
        return mark == null ? false : mark;
    }

    private double distance(Vertex v) {
        Double dist = v.getSlot(Constants.ESTIMATIVE, Double.class);
        return dist == null ? Double.MAX_VALUE : dist;
    }

    private int degree(Vertex v) {
        Integer d = v.getSlot(Constants.DEGREE, Integer.class);
        return d == null ? 0 : d;
    }


    private void setRight(Vertex v, Vertex r) {
        v.addSlot(Constants.RIGHT, r);
    }

    private void setLeft(Vertex v, Vertex l) {
        v.addSlot(Constants.LEFT, l);
    }

    private void setParent(Vertex v, Vertex p) {
        v.addSlot(Constants.PARENT, p);
    }

    private void setChild(Vertex v, Vertex c) {
        v.addSlot(Constants.CHILD, c);
    }

    private void setMarked(Vertex v, boolean m) {
        v.addSlot(Constants.MARK, m);
    }

    private void setDistance(Vertex v, double d) {
        v.addSlot(Constants.ESTIMATIVE, d);
    }

    private void setDegree(Vertex v, int degree) {
        v.addSlot(Constants.DEGREE, degree);
    }

}
