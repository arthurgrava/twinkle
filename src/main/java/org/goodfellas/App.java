package org.goodfellas;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.goodfellas.algorithm.Algorithm;
import org.goodfellas.algorithm.Dijkstra;
import org.goodfellas.algorithm.Dijkstra.Node;
import org.goodfellas.controller.GraphLoader;
import org.goodfellas.controller.GraphOperations;
import org.goodfellas.model.Edge;
import org.goodfellas.model.Grafo;
import org.goodfellas.model.Graph;

public class App {
    
    public static void main( String[] args ) {
        String fileName = null;
        boolean performanceTest = false;
        if (args.length < 1 || args.length > 2) {
            System.out.println("To run the program you should give an input as follows:");
            System.out.println("java -jar twinkle.jar [FILE_PATH] or java -jar twinkle.jar [FILE_PATH] [true|false]");
            System.exit(1);
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
            System.out.println("Could not find the file, please provide a valid input file path.");
            System.exit(1);
        }

        final GraphOperations go = new GraphOperations();
        Grafo graph = gl.loadTwo();

        long startTime = 0l;
        long endTime = 0l;
        if (performanceTest)
            startTime = System.currentTimeMillis();

        Algorithm alg =  new Algorithm(graph);
        alg.execute();
        go.print(alg.getPath(), graph.getOrigin(), graph.getDestination(), graph.getVertices());
        
        if (performanceTest) {
          endTime = System.currentTimeMillis();
          System.out.println("Took [" + (endTime - startTime) + "] ms to run Dijkstra algorithm in a Graph with ["
                  + graph.getNumVertices() + "] vertices and [" + graph.getNumEdges() + "] edges.");
          System.exit(0);
      }

//      Map<Integer, Node> map = go.getNodeMap(nodes);
//      List<Edge> path = go.retrievePath(graph, nodes, map);
//      go.printPath(path, map.get(graph.getDestination()), map.get(graph.getOrigin()));
//
//      try {
//          graph.toHtml(path);
//          Desktop.getDesktop().browse(new File("index.html").toURI());
//      } catch (IOException e) {
//          System.out.println("Could not generate the HTML file, please contact the devs.");
//          System.exit(1);
//      }

      System.exit(0);

    }

}
