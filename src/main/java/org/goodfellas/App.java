package org.goodfellas;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
import org.goodfellas.controller.algorithms.EuclidianNetworkDijkstra;

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
            
            int option = graph.getProperty(Constants.OPTION, Integer.class);
            
            List<Stack<Edge>> allPaths = new ArrayList<>(paths.size());
            Stack<Edge> path = null;
            
            for(Integer[] combination : paths) {
                shortest = getAlgorithm(
                        option,
                        graph,
                        graph.getVertex(combination[0]),
                        graph.getVertex(combination[1]));
                
                long start = System.currentTimeMillis();
                
                shortest.execute();
                
                long end = System.currentTimeMillis() - start;
                
                path = shortest.shortestPath();
                allPaths.add(path);
                
                graphPrinter.printPath(path, graph.getVertex(combination[0]), graph.getVertex(combination[1]), end);
            }
            
            if(option == 2) {
                graphPrinter.toJson(allPaths, graph);
                try {
                    Desktop.getDesktop().browse(new File("index.html").toURI());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    private static ShortestPath getAlgorithm(int option, Graph graph, Vertex source, Vertex destination) {
        if(option == 3) {
            return new EuclidianNetworkDijkstra(graph, source, destination);
        } else {
            return new Dijkstra(graph, source, destination);
        }
    }

}
