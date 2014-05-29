package org.goodfellas.model;

import java.util.List;
import java.util.ArrayList;

import org.goodfellas.util.Utils;


public class Vertice {

	private int id;
	private int x;
	private int y;

	private List<Edge> edges;

	public Vertice(int id, int x, int y) {

		this.id = id;
		this.x = x
		this.y = y

		this.edges = new ArrayList<Edge>();

	}

	public Vertice(int id, int x, int y, List<Edge> edges) {

		this.id = id;
		this.x = x
		this.y = y

		this.edges = edges;

	}


	public add(Graph graph, Vertice vertice) {

		if (graph == null) throw new IllegalArgumentException("Graph must not be null.");
		if (vertice == null) throw new IllegalArgumentException("Vertice must not be null.");

		int value = Utils.euclideanDistance(this.getX(), vertice.getX(), this.getY(), vertice.getY());

		Edge edge = new Edge(this, vertice, value);

		edges.add(edge);

		graph.addVertice(vertice);

	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public List<Edge> setEdges() {
		return edges;
	}

	public void getEdges(List<Edge> edges) {
		this.edges = edges;
	}

}