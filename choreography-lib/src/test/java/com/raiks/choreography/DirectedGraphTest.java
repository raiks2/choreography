package com.raiks.choreography;

import java.util.List;

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
        List<String> pathBetweenVertices = graph.findPath("Vertex two", "Vertex five");
        List<String> expectedPath = List.of("Vertex five", "Vertex three", "Vertex two");
        Assert.assertTrue(pathBetweenVertices.size() == expectedPath.size());
        Assert.assertTrue(pathBetweenVertices.toString().equals(expectedPath.toString()));
        Assert.assertTrue(pathBetweenVertices.get(0).equals("Vertex five"));
    }

    @Test
    public void test_FindPathCanNotDiscoverNonExistentPath() {
        DirectedGraph<String> graph = buildGraph();
        // Adding a strongly connected subcomponent. Vertex six is unreachable
        graph.addVertex("Vertex six");
        graph.addEdge("Vertex six", "Vertex five");
        List<String> pathBetweenVertices = graph.findPath("Vertex one", "Vertex six");
        Assert.assertTrue(pathBetweenVertices.size() == 0);
    }

    @Test
    public void test_FindPathCanHandleGraphCycle() {
        DirectedGraph<String> graph = buildGraph();
        graph.addVertex("Vertex six");
        graph.addEdge("Vertex five", "Vertex six");
        graph.addEdge("Vertex six", "Vertex three");
        graph.addVertex("Vertex seven");
        graph.addEdge("Vertex one", "Vertex seven");
        List<String> pathBetweenVertices = graph.findPath("Vertex one", "Vertex seven");
    }

    @Test
    public void testFailure_FindPathWithNonExistentVerticeThrowsException() {
        try {
            DirectedGraph<String> graph = new DirectedGraph<>();
            graph.addVertex("Vertex two");
            graph.findPath("Vertex one", "Vertex two");
        } catch (RuntimeException e) {
            Assert.assertEquals("The 'from' vertex doesn't exist", e.getMessage());
        }
    }

    @Test
    public void testFailure_DuplicateVerticesAreDisallowed() {
        try {
            DirectedGraph<String> graph = new DirectedGraph<>();
            graph.addVertex("Vertex one");
            graph.addVertex("Vertex one");
        } catch (RuntimeException e) {
            Assert.assertEquals("Vertex already exists", e.getMessage());
        }
    }

    @Test
    public void testFailure_AttemptToCreateEdgeForNonExistentVertexTriggersException() {
        try {
            DirectedGraph<String> graph = new DirectedGraph<>();
            graph.addVertex("Vertex one");
            graph.addEdge("Vertex one", "Vertex two");
        } catch (RuntimeException e) {
            Assert.assertEquals("The 'to' vertex you're trying to create an edge for doesn't exist", e.getMessage());
        }
    }

    @Test
    public void testFailure_whenGraphCapacityExhausted_thenExceptionIsThrown() {
        try {
            DirectedGraph<String> graph = new DirectedGraph<>(1);
            graph.addVertex("Vertex one");
            graph.addVertex("Vertex two");
        } catch (RuntimeException e) {
            Assert.assertEquals("Graph capacity exhaused", e.getMessage());
        }
    }

    @Test
    public void testFailure_AttemptToCreateTooLargeGraphTriggersException() {
        try {
            DirectedGraph<String> graph = new DirectedGraph<>(100000);
        } catch (RuntimeException e) {
            Assert.assertEquals("The maximum number of vertices is " + DirectedGraph.MAX_NUM_VERTICES, e.getMessage());
        }
    }

    @Test
    public void testFailure_AttemptToCreateLoopTriggersException() {
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
