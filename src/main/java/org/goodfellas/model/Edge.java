package org.goodfellas.model;

public class Edge {

	private Vertice from;
	private Vertice to;
	private double value;

	public Edge(Vertice verticeFrom, Vertice verticeTo, double value) {

		this.from = verticeFrom;
		this.to = verticeTo;
		this.value = value;

	}
	
	public String toString() {
	    return this.from.toString() + " - [" + this.value + "] -> " + this.to.toString();
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Vertice getVerticeFrom() {
		return from;
	}

	public void setVerticeFrom(Vertice verticeFrom) {
		this.from = verticeFrom;
	}

	public Vertice getVerticeTo() {
		return to;
	}

	public void setVerticeTo(Vertice verticeTo) {
		this.to = verticeTo;
	}

}