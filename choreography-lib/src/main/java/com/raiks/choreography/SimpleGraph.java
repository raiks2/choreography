package com.raiks.choreography;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class SimpleGraph<V> implements Graph<V> {
    private Map<V, Integer> vertexToIndex = new HashMap<>();
    private List<V> indexToVertex = new ArrayList<>();
    private int[][] adjacencyMatrix;
    private int mostRecentVertexIndex = 0;
    private int numVertices;

    private final int MAX_NUM_VERTICES = 1000;

    public SimpleGraph(int numVertices) {
        if (numVertices > MAX_NUM_VERTICES) {
            throw new RuntimeException(String.format("The maximum number of vertices is %d", MAX_NUM_VERTICES));
        }
        adjacencyMatrix = new int[numVertices][numVertices];
        this.numVertices = numVertices;
    }

    @Override
    public void addEdge(V from, V to) {
        if (!vertexToIndex.containsKey(from)) {
            throw new RuntimeException("The 'from' vertex you're trying to create an edge for doesn't exist");
        }
        if (!vertexToIndex.containsKey(to)) {
            throw new RuntimeException("The 'to' vertex you're trying to create an edge for doesn't exist");
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

    @Override
    public boolean dfs(V startVertex, V needle) {
        boolean[] visited = new boolean[vertexToIndex.size()];
        return dfs(startVertex, needle, visited);
    }

    private boolean dfs(V startVertex, V needle, boolean[] visited) {
        System.out.print(startVertex + " ");
        int index = vertexToIndex.get(startVertex);
        visited[index] = true;
        boolean found = startVertex.equals(needle);
        if (found) {
            return true;
        }

        List<V> ajacentVertices = getAdjacentVertices(startVertex);
        for(V ajacentVertex : ajacentVertices) {
            int aIndex = vertexToIndex.get(ajacentVertex);
            if(!visited[aIndex]) {
                found = dfs(ajacentVertex, needle, visited);
                if (found) {
                    return true;
                }
            }
        }
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
