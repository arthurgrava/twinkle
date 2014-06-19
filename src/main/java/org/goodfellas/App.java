package org.goodfellas;

import java.io.FileNotFoundException;

import org.goodfellas.structure.Graph;
import org.goodfellas.controller.GraphLoader;

public class App {

    public static void main( String[] args ) {

        try {
            if(args.length < 1) {
                System.out.println("One parameter is required: pathToFile:");
                System.out.println("\t java -jar twinkle.jar /tmp/input.txt");
                System.exit(1);
            }

            GraphLoader gl;
            gl = new GraphLoader(args[0]);
            Graph graph = gl.load();
            
            System.out.println(graph.toString());
            
            System.out.println("Implementation soon");
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

}
