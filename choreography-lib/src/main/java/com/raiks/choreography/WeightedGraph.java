package com.raiks.choreography;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WeightedGraph<V> implements Graph<V> {
    private Graph<V> graph;
    private Map<Edge, Integer> edgeToWeight = new HashMap<>();

    public WeightedGraph(Graph<V> graph) {
        this.graph = graph;
    }

    @Override
    public void addEdge(V from, V to) {
        addEdge(from, to, 0);
    }

    public void addEdge(V from, V to, int weight) {
        graph.addEdge(from, to);
        Edge<V> edge = new Edge<>(from, to);
        edgeToWeight.put(edge, weight);
    }

    @Override
    public void addVertex(V v) {
        graph.addVertex(v);
    }

    @Override
    public List<V> findPath(V startVertex, V endVertex) {
        return graph.findPath(startVertex, endVertex);
    }

    private static class Edge<V> {
        private V from;
        private V to;

        public Edge(V from, V to) {
            this.from = from;
            this.to = to;
        }

        public boolean equals(Object o) {
           if (o == this)
                return true;
            if (!(o instanceof Edge))
                return false;
            Edge other = (Edge)o;
            return other.from.equals(other.to);
        }

        public int hashcode() {
            return Objects.hash(from, to);
        }
    }
}
