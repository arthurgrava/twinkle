twinkle
=======

The project was created for the Analysis of Algorithms and Data Structures course in the Master of Science Graduate Program in Information Systems.

## Graph
* Graph contains maps of Vertices and Edges
* Vertice contains id and coordinates (x and y)
* Edge contains value and pointers for the Vertice before and after

## Build the project
```
$ sbt package
```
or
```
$ mvn clean install
```

## Running the project
```
$ java -jar target/twinkle-1.0.jar [PATH_TO_INPUT_FILE] [PERFORMANCE_TEST: true|false]
```
examples:
```
$ java -jar target/twinkle-1.0.jar input/one.txt
$ java -jar target/twinkle-1.0.jar input/two.txt true
```