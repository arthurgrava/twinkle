package org.goodfellas.controller;

import org.goodfellas.model.Graph;
import org.goodfellas.model.Vertex;
import org.goodfellas.util.Constants;

public class MinPriorityQueue {

    private Vertex[] heap;
    private int size = 0;


    public MinPriorityQueue(Graph graph, Vertex source) {

        heap = new Vertex[graph.getNumVertices()];
        size = graph.getNumVertices();

        for (Vertex v : graph.getVertices().values()) {
            v.addSlot(Constants.PI, null);
            v.addSlot(Constants.DISTANCE, Double.MAX_VALUE);
            heap[v.getId()] = v;
        }

        swap(source.getId(), 0);
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
