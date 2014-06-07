package org.goodfellas.model;

public class Edge {

	private Vertice verticeFrom;
	private Vertice verticeTo;
	private double value;

	public Edge(Vertice verticeFrom, Vertice verticeTo, double value) {

		this.verticeFrom = verticeFrom;
		this.verticeTo = verticeTo;
		this.value = value;

	}
	
	public String toString() {
	    return this.verticeFrom.toString() + " - [" + this.value + "] -> " + this.verticeTo.toString();
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Vertice getVerticeFrom() {
		return verticeFrom;
	}

	public void setVerticeFrom(Vertice verticeFrom) {
		this.verticeFrom = verticeFrom;
	}

	public Vertice getVerticeTo() {
		return verticeTo;
	}

	public void setVerticeTo(Vertice verticeTo) {
		this.verticeTo = verticeTo;
	}

}