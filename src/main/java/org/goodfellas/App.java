package org.goodfellas;

import org.goodfellas.controller.GraphLoader;
import org.goodfellas.model.Graph;

/**
 * Hello world!
 *
 */
public class App {
    
    public static void main( String[] args )
    {
        final GraphLoader gl = new GraphLoader();
        Graph graph = gl.loadFromFile();
        System.out.println(graph.toString());
    }
}
