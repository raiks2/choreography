package com.raiks.choreography;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class DirectedGraph<V> implements Graph<V> {
    private Map<V, Integer> vertexToIndex = new HashMap<>();
    private List<V> indexToVertex = new ArrayList<>();
    private int[][] adjacencyMatrix;
    private int mostRecentVertexIndex = 0;
    private int numVertices;

    static final int DEFAULT_NUM_VERTICES = 50;
    static final int MAX_NUM_VERTICES = 1000;

    public DirectedGraph(int numVertices) {
        if (numVertices > MAX_NUM_VERTICES) {
            throw new RuntimeException(String.format("The maximum number of vertices is %d", MAX_NUM_VERTICES));
        }
        adjacencyMatrix = new int[numVertices][numVertices];
        this.numVertices = numVertices;
    }

    public DirectedGraph() {
        this(DEFAULT_NUM_VERTICES);
    }

    @Override
    public void addEdge(V from, V to) {
        if (!vertexToIndex.containsKey(from)) {
            throw new RuntimeException("The 'from' vertex you're trying to create an edge for doesn't exist");
        }
        if (!vertexToIndex.containsKey(to)) {
            throw new RuntimeException("The 'to' vertex you're trying to create an edge for doesn't exist");
        }
        if (from.equals(to)) {
            throw new RuntimeException("Loops are not allowed");
        }
        int fromIndex = vertexToIndex.get(from);
        int toIndex = vertexToIndex.get(to);
        markEdgeInAdjecencyMatrix(fromIndex, toIndex);
    }

    private void markEdgeInAdjecencyMatrix(int fromIndex, int toIndex) {
        adjacencyMatrix[fromIndex][toIndex] = 1;
    }

    @Override
    public void addVertex(V v) {
        if (vertexToIndex.containsKey(v)) {
            throw new RuntimeException("Vertex already exists");
        }
        boolean capacityExhausted = mostRecentVertexIndex == numVertices;
        if (capacityExhausted) {
            throw new RuntimeException("Graph capacity exhaused");
        }
        vertexToIndex.put(v, mostRecentVertexIndex);
        indexToVertex.add(mostRecentVertexIndex, v);
        mostRecentVertexIndex++;
    }

    @Override
    public void bfs(V startVertex) {
        Queue<V> queue = new LinkedList<>();
        boolean[] visited = new boolean[vertexToIndex.size()];

        queue.add(startVertex);
        int index = vertexToIndex.get(startVertex);
        visited[index] = true;

        while(!queue.isEmpty()) {
            V queuedVertex = queue.poll();
            System.out.print(queuedVertex + " ");

            List<V> ajacentVertices = getAdjacentVertices(queuedVertex);
            for(V ajacentVertex : ajacentVertices) {
                int aIndex = vertexToIndex.get(ajacentVertex);
                if(!visited[aIndex]) {
                    queue.add(ajacentVertex);
                    visited[aIndex] = true;
                }
            }
        }
    }

    boolean vertexExists(V vertex) {
        return vertexToIndex.containsKey(vertex);
    }

    boolean edgeExists(V from, V to) {
        return getAdjacentVertices(from).contains(to);
    }

    @Override
    public Deque<V> findPath(V startVertex, V endVertex) {
        if (!vertexExists(startVertex)) {
            throw new RuntimeException("The 'from' vertex doesn't exist");
        }
        if (!vertexExists(endVertex)) {
            throw new RuntimeException("The 'to' vertex doesn't exist");
        }
        boolean[] visited = new boolean[vertexToIndex.size()];
        Deque<V> pathToVertex = new ArrayDeque<>();
        dfs(startVertex, endVertex, visited, pathToVertex);
        System.err.println("\npath =" + pathToVertex.toString());
        return pathToVertex;
    }

    /**
     * Performs the depth-first search of the needle. If needle if found, returns true, false otherwise
     * After a method call, pathToVertex contains a path to the needle if it was found, or en empty collection
     */
    private boolean dfs(V startVertex, V needle, boolean[] visited, Deque<V> pathToVertex) {
        System.out.print(startVertex + " ");
        int index = vertexToIndex.get(startVertex);
        visited[index] = true;
        boolean found = startVertex.equals(needle);
        if (found) {
            return true;
        }

        pathToVertex.push(startVertex);

        List<V> ajacentVertices = getAdjacentVertices(startVertex);
        for(V ajacentVertex : ajacentVertices) {
            int aIndex = vertexToIndex.get(ajacentVertex);
            if(!visited[aIndex]) {
                found = dfs(ajacentVertex, needle, visited, pathToVertex);
                if (found) {
                    return true;
                }
            }
        }

        pathToVertex.pop();
        return false;
    }

    private List<V> getAdjacentVertices(V vertex) {
        int index = vertexToIndex.get(vertex);
        List<V> result = new ArrayList<>();
        for(int i = 0; i < adjacencyMatrix[index].length; i++) {
            boolean edgeExists = adjacencyMatrix[index][i] == 1;
            if (edgeExists) {
                result.add(indexToVertex.get(i));
            }
        }
        return result;
    }
}
