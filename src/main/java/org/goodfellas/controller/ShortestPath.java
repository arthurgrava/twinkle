package org.goodfellas.controller;

import java.util.Stack;

import org.goodfellas.structure.Edge;

/**
 * Context for the algorithms
 * 
 * Following strategy pattern: http://en.wikipedia.org/wiki/Strategy_pattern
 * 
 */
public interface ShortestPath {
    
    public void execute();
    
    public Stack<Edge> shortestPath();
    
}
