package org.goodfellas;

import java.util.List;

import org.goodfellas.algorithm.Dijkstra;
import org.goodfellas.algorithm.Dijkstra.Node;
import org.goodfellas.controller.GraphLoader;
import org.goodfellas.controller.GraphOperations;
import org.goodfellas.model.Edge;
import org.goodfellas.model.Graph;

/**
 * Hello world!
 *
 */
public class App {
    
    public static void main( String[] args )
    {
        final GraphLoader gl = new GraphLoader();
        final GraphOperations go = new GraphOperations();
        
        Graph graph = gl.loadFromFile();
        Dijkstra dij = new Dijkstra(graph);
        dij.execute();
        List<Node> nodes = dij.getPath();
        
        List<Edge> path = go.retrievePath(graph, nodes);
        go.printPath(path, nodes.get(nodes.size() - 1));
    }
}
