package com.raiks.choreography;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class WeightedGraphTest {
    @Test
    public void test_AddedEdgeWithWeightCanBeReadBackAndIsCorrect() {
        WeightedGraph<String> weightedGraph = new WeightedGraph<>(new UndirectedGraph());
        weightedGraph.addVertex("Vertex one");
        weightedGraph.addVertex("Vertex two");
        weightedGraph.addVertex("Vertex three");
        weightedGraph.addEdge("Vertex one", "Vertex two", 5);
        weightedGraph.addEdge("Vertex two", "Vertex three", 7);
        Assert.assertEquals(5, weightedGraph.getEdgeWeight("Vertex one", "Vertex two"));
    }
}
