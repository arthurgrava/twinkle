package org.goodfellas.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import org.goodfellas.model.Grafo;
import org.goodfellas.model.Graph;
import org.goodfellas.model.Vertice;

public class GraphLoader {

    private Graph graph;
    private Scanner sc;

    /*
        If nothing is passed to the constructor, it
        initializes a scanner from System.in
     */
    public GraphLoader() {
        sc = new Scanner(System.in);
    }

    public GraphLoader(String pathToFile) throws FileNotFoundException {
        sc = new Scanner(new File(pathToFile));
    }
    
    public Grafo loadTwo() {
        final int n = sc.nextInt();
        final int m = sc.nextInt();
        
        Grafo g = new Grafo(n, m);
        
        for(int i = 0 ; i < n && sc.hasNextInt() ; i++) {
            int id = sc.nextInt();
            int x = sc.nextInt();
            int y = sc.nextInt();
            g.addVertex(id, x, y);
        }
        
        for(int i = 0 ; i < m && sc.hasNextInt() ; i++) {
            int one = sc.nextInt();
            int two = sc.nextInt();
            g.connect(one, two);
        }
        
        g.setOrigin(sc.nextInt());
        g.setDestination(sc.nextInt());
        
        sc.close();
        
        return g;
    }
    
    public Graph load() {
        final int n = sc.nextInt();
        final int m = sc.nextInt();
        
        graph = new Graph();
        graph.setNumEdges(m);
        
        for(int i = 0 ; i < n && sc.hasNextInt() ; i++) {
            int id = sc.nextInt();
            int x = sc.nextInt();
            int y = sc.nextInt();
            Vertice vertice = new Vertice(id, x, y);
            graph.addVertice(vertice);
        }
        
        Map<Integer, Vertice> map = graph.getVertices();
        
        for(int i = 0 ; i < m && sc.hasNextInt() ; i++) {
            Vertice one = map.get(sc.nextInt());
            Vertice two = map.get(sc.nextInt());
            graph.connect(one, two);
        }
        
        graph.setOrigin(sc.nextInt());
        graph.setDestination(sc.nextInt());
        
        sc.close();
        
        return graph;
    }

}