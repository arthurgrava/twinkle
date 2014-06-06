package org.goodfellas.model;

import java.util.Map;
import java.util.HashMap;
import java.lang.IllegalArgumentException;

import org.goodfellas.model.Vertice;

public class Graph {

	/* Map containing all the vertices, type <vertice id, vertice obj> */
	private Map<Integer, Vertice> vertices;

	private int numEdges;
	private int numVertices;
	
	private int origin;
	private int destination;

	public Graph() {

		vertices = new HashMap<Integer, Vertice>();
		numEdges = 0;
		numVertices = 0;

	}

	public Graph(Map<Integer, Vertice> vertices, int numEdges) {

		if (numEdges < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
		if (vertices == null) throw new IllegalArgumentException("Vertices must be non null");

		this.vertices = vertices;
		this.numVertices = vertices.size();
		this.numEdges = numEdges;

	}

	public void addVertice(Vertice vertice) {

		vertices.put( vertice.getId(), vertice );
		this.numVertices++;
		this.numEdges += vertice.getEdges().size();

	}

	@Override
	public String toString() {
	    String line = this.getNumVertices() + " vertices\n";
	    for(Integer key : vertices.keySet()) {
	        line += this.vertices.get(key).toString();
	    }
		return line;
	}
	
	public String toHtml() {
	    // TODO - Rafa, here you can convert the graph to html
	    return null;
	}


	public Map<Integer, Vertice> getVertices() {
		return vertices;
	}

	public void setVertices(Map<Integer, Vertice> vertices) {
		this.vertices = vertices;
	}

	public int getNumEdges() {
		return numEdges;
	}

	public void setNumEdges(int numEdges) {
		this.numEdges = numEdges;
	}

	public int getNumVertices() {
		return this.vertices.size();
	}

	public void setNumVertices(int numVertices) {
		this.numVertices = numVertices;
	}

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

}