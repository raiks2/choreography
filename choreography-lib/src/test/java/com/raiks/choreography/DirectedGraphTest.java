package com.raiks.choreography;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.Assert;
import org.junit.Test;

public class DirectedGraphTest {
    @Test
    public void test_AddVertexSuccessfullyCreatesVertexNotYetExistingInGraph() {
        DirectedGraph<String> graph = new DirectedGraph<>();
        graph.addVertex("Vertex one");
        Assert.assertFalse(graph.vertexExists("Vertex two"));
        graph.addVertex("Vertex two");
        Assert.assertTrue(graph.vertexExists("Vertex two"));
    }

    @Test
    public void test_AddEdgeSuccessfullyCreatesDirectedEdgeBetweenDistinctVertices() {
        DirectedGraph<String> graph = new DirectedGraph<>();
        graph.addVertex("Vertex one");
        graph.addVertex("Vertex two");
        graph.addEdge("Vertex one", "Vertex two");
        Assert.assertTrue(graph.edgeExists("Vertex one", "Vertex two"));
        Assert.assertFalse(graph.edgeExists("Vertex two", "Vertex one"));
    }

    @Test
    public void test_FindPathCanDiscoverExistingPath() {
        DirectedGraph<String> graph = buildGraph();
        Deque<String> pathBetweenVertices = graph.findPath("Vertex two", "Vertex five");
        Assert.assertTrue(pathBetweenVertices.size() == 2);
        Assert.assertTrue(pathBetweenVertices.toString().equals("[Vertex three, Vertex two]"));
        Assert.assertTrue(pathBetweenVertices.pop().equals("Vertex three"));
    }

    @Test
    public void test_FindPathCanNotDiscoverNonExistentPath() {
        DirectedGraph<String> graph = buildGraph();
        // Adding a strongly connected subcomponent. Vertex six is unreachable
        graph.addVertex("Vertex six");
        graph.addEdge("Vertex six", "Vertex five");
        Deque<String> pathBetweenVertices = graph.findPath("Vertex one", "Vertex six");
        Assert.assertTrue(pathBetweenVertices.size() == 0);
    }

    @Test
    public void test_DuplicateVerticesAreDisallowed() {
        try {
            DirectedGraph<String> graph = new DirectedGraph<>();
            graph.addVertex("Vertex one");
            graph.addVertex("Vertex one");
        } catch (RuntimeException e) {
            Assert.assertEquals("Vertex already exists", e.getMessage());
        }
    }

    @Test
    public void test_AttemptToCreateEdgeForNonExistentVertexTriggersException() {
        try {
            DirectedGraph<String> graph = new DirectedGraph<>();
            graph.addVertex("Vertex one");
            graph.addEdge("Vertex one", "Vertex two");
        } catch (RuntimeException e) {
            Assert.assertEquals("The 'to' vertex you're trying to create an edge for doesn't exist", e.getMessage());
        }
    }

    @Test
    public void test_whenGraphCapacityExhausted_thenExceptionIsThrown() {
        try {
            DirectedGraph<String> graph = new DirectedGraph<>(1);
            graph.addVertex("Vertex one");
            graph.addVertex("Vertex two");
        } catch (RuntimeException e) {
            Assert.assertEquals("Graph capacity exhaused", e.getMessage());
        }
    }

    @Test
    public void test_AttemptToCreateTooLargeGraphTriggersException() {
        try {
            DirectedGraph<String> graph = new DirectedGraph<>(100000);
        } catch (RuntimeException e) {
            Assert.assertEquals("The maximum number of vertices is " + DirectedGraph.MAX_NUM_VERTICES, e.getMessage());
        }
    }

    @Test
    public void test_AttemptToCreateLoopTriggersException() {
        try {
            DirectedGraph<String> graph = buildGraph();
            graph.addEdge("Vertex one", "Vertex one");
        } catch (RuntimeException e) {
            Assert.assertEquals("Loops are not allowed", e.getMessage());
        }
    }

    private DirectedGraph<String> buildGraph() {
        DirectedGraph<String> graph = new DirectedGraph<>(10);
        graph.addVertex("Vertex one");
        graph.addVertex("Vertex two");
        graph.addVertex("Vertex three");
        graph.addVertex("Vertex four");
        graph.addVertex("Vertex five");
        graph.addEdge("Vertex one", "Vertex two");
        graph.addEdge("Vertex two", "Vertex three");
        graph.addEdge("Vertex two", "Vertex four");
        graph.addEdge("Vertex three", "Vertex five");
        return graph;
    }
}
