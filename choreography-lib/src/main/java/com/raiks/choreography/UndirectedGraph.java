package com.raiks.choreography;

import java.util.Deque;

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
    public void bfs(V startVertex) {
        directedGraph.bfs(startVertex);
    }

    @Override
    public Deque<V> findPath(V startVertex, V endVertex) {
        return findPath(startVertex, endVertex);
    }
}
