package org.goodfellas.structure;

import org.goodfellas.structure.Graph;
import org.goodfellas.structure.Vertex;
import org.goodfellas.util.Constants;

import java.util.HashMap;
import java.util.Map;

public class MinPriorityQueue {

    private Vertex[] heap;
    private int heapSize = 0;
    // this map was created to suppress the problem of swap,
    // when using the heap index to index vertexes.
    private Map<Integer, Integer> vertexHeapMap;

    public MinPriorityQueue(Graph graph, Vertex source) {

        heap = new Vertex[graph.getNumVertices()];
        heapSize = graph.getNumVertices();
        vertexHeapMap = new HashMap<Integer, Integer>(graph.getNumVertices());

        for (Vertex v : graph.getVertices().values()) {
            v.addSlot(Constants.PI, null);
            v.addSlot(Constants.DISTANCE, Double.MAX_VALUE);
            heap[v.getId()] = v;
            vertexHeapMap.put(v.getId(), v.getId());
        }
        
        source.addSlot(Constants.DISTANCE, 0.0);

        swap(source.getId(), 0);
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int getSize() {
        return heapSize;
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

    public void decreaseKey(Vertex v, double distance) {

        v.addSlot(Constants.DISTANCE, distance);

        int actualIndex = vertexHeapMap.get(v.getId());
        int parentIndex = getParent( vertexHeapMap.get(v.getId()) );
        while (actualIndex > 0 && distance(heap[parentIndex]) > distance(heap[actualIndex])) {
            swap(actualIndex, parentIndex);
            actualIndex = parentIndex;
            parentIndex = getParent(actualIndex);
        }

    }

    private void minHeapify(int i) {
        int l = left(i);
        int r = right(i);
        int minIndex = -1;

        if (l < heapSize - 1 && distance(heap[l]) < distance(heap[i]))
            minIndex = l;
        else
            minIndex = i;

        if (r < heapSize - 1 && distance(heap[r]) < distance(heap[minIndex]))
            minIndex = r;

        if (minIndex != i) {
            swap(i, minIndex);
            minHeapify(minIndex);
        }
    }

    private int left(int i) {
        return 2*i + 1;
    }

    private int right(int i) {
        return 2*i + 2;
    }

    private int getParent(int i) {
        return (int) Math.floor( ((double) (i - 1) ) / 2.0 );
    }

    private Double distance(Vertex v) {
        return v.getSlot(Constants.DISTANCE, Double.class);
    }

    private void swap(int i, int j) {
        Vertex aux = heap[i];
        heap[i] = heap[j];
        heap[j] = aux;

        vertexHeapMap.put(heap[i].getId(), i);
        vertexHeapMap.put(heap[j].getId(), j);
    }

}