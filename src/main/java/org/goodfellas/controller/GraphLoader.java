package org.goodfellas.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.goodfellas.model.Graph;

public class GraphLoader {

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
    
    public Graph load() {
        final int n = sc.nextInt();
        final int m = sc.nextInt();
        
        Graph g = new Graph(n, m);
        
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

}