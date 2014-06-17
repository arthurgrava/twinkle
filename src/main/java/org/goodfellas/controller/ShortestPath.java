package org.goodfellas.controller;

import java.util.List;

import org.goodfellas.structure.Edge;

/**
 * Context for the algorithms
 * 
 * Following strategy pattern: http://en.wikipedia.org/wiki/Strategy_pattern
 * 
 */
public interface ShortestPath {
    
    public void execute();
    
    public List<Edge> shortestPath();
    
}
