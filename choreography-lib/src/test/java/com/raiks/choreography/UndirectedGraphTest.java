package com.raiks.choreography;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.Assert;
import org.junit.Test;

public class UndirectedGraphTest {
    @Test
    public void test_AddEdgeSuccessfullyCreatesDirectedEdgeBetweenDistinctVertices() {
        UndirectedGraph<String> graph = new UndirectedGraph<>();
        graph.addVertex("Vertex one");
        graph.addVertex("Vertex two");
        graph.addEdge("Vertex one", "Vertex two");
        Assert.assertTrue(graph.edgeExists("Vertex one", "Vertex two"));
        Assert.assertTrue(graph.edgeExists("Vertex two", "Vertex one"));
    }
}
