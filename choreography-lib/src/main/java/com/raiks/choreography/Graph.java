package com.raiks.choreography;

import java.util.ArrayDeque;
import java.util.Deque;


public interface Graph<V> {
    public void addEdge(V from, V to);
    public void addVertex(V v);
    public void bfs(V start);
    public Deque<V> findPath(V startVertex, V endVertex);
}
