twinkle
=======

The project was created for the Analysis of Algorithms and Data Structures course in the Master of Science Graduate Program in Information Systems.

## Graph
* Graph contains maps of Vertices and Edges
* Vertice contains id and coordinates (x and y)
* Edge contains value and pointers for the Vertice before and after

## Build the project
```
$ sbt package compile run
```
or
```
$ mvn clean install
```

## Running the project
```
$ java -jar twinkle.jar [PATH_TO_INPUT_FILE] [PERFORMANCE_TEST: true|false]
```
examples:
```
$ java -jar twinkle.jar input/one.txt
$ java -jar twinkle.jar input/two.txt true
```