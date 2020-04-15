package com.raiks.choreography;

import java.util.ArrayDeque;
import java.util.Deque;


public interface Graph<V> {
    public void addEdge(V from, V to);
    public void addVertex(V v);
    public Deque<V> findPath(V startVertex, V endVertex);
}
