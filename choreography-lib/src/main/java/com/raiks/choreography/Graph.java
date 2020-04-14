package com.raiks.choreography;

public interface Graph<V> {
    public void addEdge(V from, V to);
    public void addVertex(V v);
    public void bfs(V start);
    public void dfs(V start);
}
