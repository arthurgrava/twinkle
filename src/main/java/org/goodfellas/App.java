package org.goodfellas;

import java.util.List;

import org.goodfellas.algorithm.Dijkstra;
import org.goodfellas.algorithm.Dijkstra.Node;
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
        Dijkstra dij = new Dijkstra(graph);
        dij.execute();
        List<Node> e = dij.getPath();
        for(Node node : e) {
            if (node.pi != null)
                System.out.println(node.id + " " + node.pi.id + " " + node.distance);
            else
                System.out.println(node.id + " " + node.distance);
        }
    }
}
