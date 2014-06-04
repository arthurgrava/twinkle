twinkle
=======

Projeto criado para a matéria de AED do programa de mestrado do curso de Sistemas de Informção. A especificação do projeto, bem como a tradução total do repositório serão inseridas em breve.

## Graph
* Graph contains maps of Vertices and Edges
* Vertice contains id and coordinates (x and y)
* Edge contains value and pointers for the Vertice before and after

## Build, compile and run with sbt
```
$ sbt
> package
> compile
> run
```
or
```
$ sbt package compile run
```

or
```
$ mvn clean install
```

## Running the project
```
$ java -jar twinkle.jar < input/one.txt
```
