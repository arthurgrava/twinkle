package org.goodfellas;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.goodfellas.algorithm.Dijkstra;
import org.goodfellas.algorithm.Dijkstra.Node;
import org.goodfellas.controller.GraphLoader;
import org.goodfellas.controller.GraphOperations;
import org.goodfellas.model.Edge;
import org.goodfellas.model.Graph;

public class App {
    
    public static void main( String[] args ) {

        String fileName = null;
        boolean performanceTest = false;
        if (args.length < 1 || args.length > 2) {
            System.out.println("To run the program you should give an input.");
            System.exit(0);
        } else if (args.length == 1) {
            fileName = args[0];
        } else if (args.length == 2) {
            fileName = args[0];
            performanceTest = Boolean.parseBoolean(args[1]);
        }

        GraphLoader gl = null;
        try {
            gl = new GraphLoader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file, please provide a valid file path.");
            System.exit(0);
        }

        final GraphOperations go = new GraphOperations();
        Graph graph = gl.load();

        long startTime = 0l;
        long endTime = 0l;
        if (performanceTest)
            startTime = System.currentTimeMillis();

        Dijkstra dij = new Dijkstra(graph);
        dij.execute();
        List<Node> nodes = dij.getPath();

        if (performanceTest) {
            endTime = System.currentTimeMillis();
            System.out.println("Took [" + (endTime - startTime) + "] ms to run Dijkstra algorithm in a Graph with ["
                    + graph.getNumVertices() + "] vertices and [" + graph.getNumEdges() + "] edges.");
            System.exit(0);
        }

        Map<Integer, Node> map = go.getNodeMap(nodes);
        List<Edge> path = go.retrievePath(graph, nodes, map);
        go.printPath(path, map.get(graph.getDestination()), map.get(graph.getOrigin()));

    }

}
