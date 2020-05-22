package com.raiks.choreography;

import java.util.List;

public class UndirectedGraph<V> implements Graph<V> {
    private DirectedGraph directedGraph;

    public UndirectedGraph(int numVertices) {
        this.directedGraph = new DirectedGraph(numVertices);
    }

    public UndirectedGraph() {
        this(DirectedGraph.DEFAULT_NUM_VERTICES);
    }

    @Override
    public void addEdge(V from, V to) {
        directedGraph.addEdge(from, to);
        directedGraph.addEdge(to, from);
    }

    @Override
    public void addVertex(V v) {
        directedGraph.addVertex(v);
    }

    @Override
    public List<V> findPath(V startVertex, V endVertex) {
        return directedGraph.findPath(startVertex, endVertex);
    }

    boolean edgeExists(V from, V to) {
        return directedGraph.edgeExists(from, to);
    }
}
