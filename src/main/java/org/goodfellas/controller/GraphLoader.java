package org.goodfellas.controller;

import java.util.Map;
import java.util.Scanner;

import org.goodfellas.model.Graph;
import org.goodfellas.model.Vertice;

public class GraphLoader {

    final String REGEX = " ";
    private Graph graph;

    public Graph loadFromFile() {
        Scanner sc = new Scanner(System.in);
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
            one.add(graph, two);
        }
        
        graph.setOrigin(sc.nextInt());
        graph.setDestination(sc.nextInt());
        
        sc.close();
        
        return graph;
    }
    
//    @SuppressWarnings("finally")
//    public Graph loadFromFile() {
//        graph = null;
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//            String[] line = br.readLine().split(REGEX);
//            final int n = new Integer(line[0]);
//            final int m = new Integer(line[1]);
//            graph = new Graph();
//            graph.setNumVertices(n);
//            graph.setNumEdges(m);
//
//            br.readLine();
//
//            int index = 0;
//            while(index < n) {
//                line = br.readLine().split(REGEX);
//                Vertice vertice = new Vertice(new Integer(line[0]), new Integer(line[1]), new Integer(line[2]));
//                graph.addVertice(vertice);
//                index++;
//            }
//            
//            index = 0;
//            br.readLine();
//            
//            while(index < m) {
//                line = br.readLine().split(REGEX);
//                Vertice one = graph.getVertices().get(new Integer(line[0]));
//                Vertice two = graph.getVertices().get(new Integer(line[1]));
//                one.add(graph, two);
//                index++;
//            }
//            
//            br.readLine();
//            line = br.readLine().split(REGEX);
//            graph.setOrigin(new Integer(line[0]));
//            graph.setDestination(new Integer(line[1]));
//            
//            br.close();
//        } catch (IOException e) {
//            System.out.println("The command line to execute must be like: 'java -jar twinkle.jar < input.txt'");
//            System.err.println("IOError: " + e.getMessage());
//        } finally {
//            return graph;
//        }
//
//    }

}