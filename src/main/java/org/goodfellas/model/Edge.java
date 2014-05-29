package org.goodfellas.model;

import java.util.Map;
import java.util.HashMap;


public class Edge {

	private Vertice verticeFrom;
	private Vertice verticeTo;
	private int value;

	public Edge(Vertice verticeFrom, Vertice verticeTo, int value) {

		this.verticeFrom = verticeFrom;
		this.verticeTo = verticeTo;
		this.value = value;

	}


	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
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