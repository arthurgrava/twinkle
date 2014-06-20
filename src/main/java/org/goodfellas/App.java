package org.goodfellas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

import org.goodfellas.structure.Edge;
import org.goodfellas.structure.Graph;
import org.goodfellas.structure.Vertex;
import org.goodfellas.util.Constants;
import org.goodfellas.controller.GraphLoader;
import org.goodfellas.controller.GraphPrinter;
import org.goodfellas.controller.ShortestPath;
import org.goodfellas.controller.algorithms.Dijkstra;

public class App {

    @SuppressWarnings("unchecked")
    public static void main( String[] args ) throws IOException {

        try {
            if(args.length < 1) {
                System.out.println("One parameter is required: pathToFile:");
                System.out.println("\t java -jar twinkle.jar /tmp/input.txt");
                System.exit(1);
            }

            GraphLoader gl;
            gl = new GraphLoader(args[0]);
            Graph graph = gl.load();
            GraphPrinter graphPrinter = new GraphPrinter();

            ShortestPath shortest = null;
            List<Integer[]> paths = graph.getProperty(Constants.PATHS, List.class);
            Stack<Edge> path = null;
            
            for(Integer[] combination : paths) {
                shortest = getAlgorithm(graph.getProperty(Constants.OPTION, Integer.class), graph, graph.getVertex(combination[0]), graph.getVertex(combination[1]));
                
                long start = System.currentTimeMillis();
                
                shortest.execute();
                path = shortest.shortestPath();
                
                long end = System.currentTimeMillis() - start;
                
                graphPrinter.printPath(path, graph.getVertex(combination[0]), graph.getVertex(combination[1]), end);
                graphPrinter.toJson(path, graph);
            }

            System.out.println("Implementation soon");
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    private static ShortestPath getAlgorithm(Integer option, Graph graph, Vertex source, Vertex destination) {
        // TODO - change it and create a switch to pick an algorithm
        return new Dijkstra(graph, source, destination);
    }

}
