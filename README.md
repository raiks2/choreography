# Synopsis

Choreography is a super simple graph library. Contains classes implementing undirected and directed graphs that support the following operations:

* addEdge()
* addVertex()
* findPath()

There's also a weighted graph implementation that adds support for weighted edges. In the future
it can be extended to support operations like findShortestPath() using Dijkstra's or A* algorithms.

The project is multi-module Maven project consisting of 2 parts:

1) Choreography library that can be used separately
2) An example application

How to build and use:

- Step into the project root directory and run `mvn clean package`. This will build both the library and the app
- Run the ChoreographyApp: `java -cp choreography-app/target/choreography-jar-with-dependencies.jar:choreography-lib/target/choreography-jar-with-dependencies.jar com.raiks.choreography.ChoreographyApp`
