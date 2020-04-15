package com.raiks.choreography;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.Assert;
import org.junit.Test;

public class SimpleGraphTest {
    @Test
    public void test_givenVertexExists_whenItsExistenceIsChecked_thenItIsConfirmed() {
        SimpleGraph<String> graph = buildGraph();
        Assert.assertTrue(graph.vertexExists("Vertex five"));
    }

    @Test
    public void test_givenVertexDoesntExist_whenItsExistenceIsChecked_thenItIsNotConfirmed() {
        SimpleGraph<String> graph = buildGraph();
        Assert.assertFalse(graph.vertexExists("Vertex six"));
    }

    @Test
    public void test_given_pathExists_whenItsChecked_thenItCanBeDiscovered() {
        SimpleGraph<String> graph = buildGraph();
        Deque<String> pathBetweenVertices = graph.findPath("Vertex two", "Vertex five");
        Assert.assertTrue(pathBetweenVertices.size() == 2);
        Assert.assertTrue(pathBetweenVertices.toString().equals("[Vertex three, Vertex two]"));
        Assert.assertTrue(pathBetweenVertices.pop().equals("Vertex three"));
    }

    @Test
    public void test_given_pathDoesntExist_whenItsChecked_thenItCanNotBeDiscovered() {
        SimpleGraph<String> graph = buildGraph();
        // Adding a strongly connected component. Vertex six is unreachable
        graph.addVertex("Vertex six");
        graph.addEdge("Vertex six", "Vertex five");
        Deque<String> pathBetweenVertices = graph.findPath("Vertex one", "Vertex six");
        Assert.assertTrue(pathBetweenVertices.size() == 0);
    }

    @Test
    public void test_givenVertexAlreadyExists_whenAttemptToAddItIsMade_thenAnExceptionIsThrown() {
        try {
            SimpleGraph<String> graph = new SimpleGraph<>();
            graph.addVertex("Vertex one");
            graph.addVertex("Vertex one");
        } catch (RuntimeException e) {
            Assert.assertEquals("Vertex already exists", e.getMessage());
        }
    }

    @Test
    public void test_givenVertexDoesntExist_whenAttemptToAddEdgeIsMade_thenAnExceptionIsThrown() {
        try {
            SimpleGraph<String> graph = new SimpleGraph<>();
            graph.addVertex("Vertex one");
            graph.addEdge("Vertex one", "Vertex two");
        } catch (RuntimeException e) {
            Assert.assertEquals("The 'to' vertex you're trying to create an edge for doesn't exist", e.getMessage());
        }
    }

    @Test
    public void test_givenGraphCapacityExhausted_whenAttemptToAddNewVertexIsMade_thenAnExceptionIsThrown() {
        try {
            SimpleGraph<String> graph = new SimpleGraph<>(1);
            graph.addVertex("Vertex one");
            graph.addVertex("Vertex two");
        } catch (RuntimeException e) {
            Assert.assertEquals("Graph capacity exhaused", e.getMessage());
        }
    }

    @Test
    public void test_givenSpecifiedGraphCapacityIsTooLarge_whenItsCreated_thenAnExceptionIsThrown() {
        try {
            SimpleGraph<String> graph = new SimpleGraph<>(100000);
        } catch (RuntimeException e) {
            Assert.assertEquals("The maximum number of vertices is " + SimpleGraph.MAX_NUM_VERTICES, e.getMessage());
        }
    }

    @Test
    public void test_whenAttemptToCreateLoopIsMade_thenAnExceptionIsThrown() {
        try {
            SimpleGraph<String> graph = buildGraph();
            graph.addEdge("Vertex one", "Vertex one");
        } catch (RuntimeException e) {
            Assert.assertEquals("Loops are not allowed", e.getMessage());
        }
    }

    private SimpleGraph<String> buildGraph() {
        SimpleGraph<String> graph = new SimpleGraph<>(10);
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
