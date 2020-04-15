package com.raiks.choreography;

import java.util.List;

public interface Graph<V> {
    public void addEdge(V from, V to);
    public void addVertex(V v);
    public List<V> findPath(V startVertex, V endVertex);
}
