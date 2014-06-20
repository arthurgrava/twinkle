package org.goodfellas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

import org.goodfellas.structure.Edge;
import org.goodfellas.structure.Graph;
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
            Stack<Edge> path = new Stack<Edge>();
            for(Integer[] combination : paths) {
                shortest = new Dijkstra(graph, graph.getVertex(combination[0]), graph.getVertex(combination[1]));
                //shortest.execute();
                //path = shortest.shortestPath();
                path.add(new Edge(graph.getVertex(0), graph.getVertex(1)));
                graphPrinter.printPath(new Stack<Edge>(), graph.getVertex(combination[0]));
                graphPrinter.toJson(path, graph);
            }

            System.out.println("Implementation soon");
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

}
