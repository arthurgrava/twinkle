package org.goodfellas.operations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.goodfellas.model.Graph;
import org.goodfellas.model.Vertex;

public class GraphLoader {

    private Scanner sc = null;

    public GraphLoader() {
        sc = new Scanner(System.in);
    }

    public GraphLoader(String path) throws FileNotFoundException {
        sc = new Scanner(new File(path));
    }

    public Graph load() throws FileNotFoundException {
        try {
            int numVertices = sc.nextInt();
            int numEdges = sc.nextInt();

            Graph graph = new Graph(numVertices, numEdges);

            int id = -1;
            int x = -1;
            int y = -1;

            for (int i = 0 ; i < numVertices && sc.hasNextInt() ; i++) {
                id = sc.nextInt();
                x = sc.nextInt();
                y = sc.nextInt();

                graph.addVertex(new Vertex(id, x, y));
            }

            Map<Integer, Vertex> map = graph.getVertices();
            Vertex from = null;
            Vertex to = null;

            for(int i = 0 ; i < numEdges && sc.hasNextInt() ; i++) {
                from = map.get(sc.nextInt());
                to = map.get(sc.nextInt());
                graph.addEdge(from, to);
            }

            int option = sc.nextInt();
            graph.addProperty("option", option);

            int pathsToFind = sc.nextInt();
            List<Integer[]> paths = new ArrayList<>(pathsToFind);

            for(int i = 0 ; i < pathsToFind && sc.hasNextInt() ; i++) {
                Integer[] path = new Integer[2];
                path[0] = sc.nextInt();
                path[1] = sc.nextInt();
                paths.add(path);
            }
            
            // TODO - I'm not ok with this solution
            
            graph.addProperty("pathsToFins", paths);
            
            return graph;
        } finally {
            sc.close();
        }
    }

}
