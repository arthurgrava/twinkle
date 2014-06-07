package org.goodfellas;

import java.util.List;
import java.util.Map;

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

        long startTime = System.currentTimeMillis();

        Dijkstra dij = new Dijkstra(graph);
        dij.execute();
        List<Node> nodes = dij.getPath();

        long endTime = System.currentTimeMillis();

        System.out.println("Took [" + (endTime - startTime) + "] to run Dijkstra algorithm.");

        Map<Integer, Node> map = go.getNodeMap(nodes);
        List<Edge> path = go.retrievePath(graph, nodes, map);
        go.printPath(path, map.get(graph.getDestination()), map.get(graph.getOrigin()));

    }
}
