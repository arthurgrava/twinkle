package org.goodfellas.structure;

import org.goodfellas.structure.Graph;
import org.goodfellas.structure.Vertex;
import org.goodfellas.util.Constants;

public class MinPriorityQueue {

    private Vertex[] heap;
    private int heapSize = 0;


    public MinPriorityQueue(Graph graph, Vertex source) {

        heap = new Vertex[graph.getNumVertices()];
        heapSize = graph.getNumVertices();

        for (Vertex v : graph.getVertices().values()) {
            v.addSlot(Constants.PI, null);
            v.addSlot(Constants.DISTANCE, Double.MAX_VALUE);
            heap[v.getId()] = v;
        }

        swap(source.getId(), 0);
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int getSize() {
        return heapSize;
    }

    public void minHeapify(int i) {
        int l = left(i);
        int r = right(i);
        int minIndex = -1;

        if (l < heapSize && distance(heap[l]) < distance(heap[i]))
            minIndex = l;
        else
            minIndex = i;

        if (r < heapSize && distance(heap[r]) < distance(heap[minIndex]))
            minIndex = r;

        if (minIndex != i) {
            swap(i, minIndex);
            minHeapify(minIndex);
        }
    }

    private int left(int i) {
        return 2*i;
    }

    private int right(int i) {
        return 2*i + 1;
    }

    public Vertex extractMin() {
        Vertex min = min();
        swap(0, heapSize - 1);
        heapSize--;

        minHeapify(0);

        return min;
    }

    public Vertex min() {
        return heap[0];
    }

    public void decreaseKey(Vertex v, Double distance) {

        v.addSlot(Constants.DISTANCE, distance);

        int actualIndex = v.getId();
        int parentIndex = getParent(v.getId());
        while (actualIndex > 0 && distance(heap[actualIndex]) < distance(heap[parentIndex])) {
            swap(actualIndex, parentIndex);
            actualIndex = parentIndex;
            parentIndex = getParent(actualIndex);
        }

    }

    private int getParent(int i) {
        return (int) Math.floor( ((double) i ) / 2.0 );
    }

    private Double distance(Vertex v) {
        return v.getSlot(Constants.DISTANCE, Double.class);
    }

    private void swap(int i, int j) {
        Vertex aux = heap[i];
        heap[i] = heap[j];
        heap[j] = aux;
    }

}
