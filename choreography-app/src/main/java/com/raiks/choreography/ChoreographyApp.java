package com.raiks.choreography;

import java.util.Collections;
import java.util.List;

import com.raiks.choreography.UndirectedGraph;

public class ChoreographyApp {
    public static void main(String[] args) {
        UndirectedGraph<String> graph = new UndirectedGraph<>();
        graph.addVertex("Boston");
        graph.addVertex("New York");
        graph.addVertex("Charlotte");
        graph.addVertex("Nashville");
        graph.addVertex("Atlanta");
        graph.addVertex("Honolulu");

        graph.addEdge("Boston", "New York");
        graph.addEdge("New York", "Nashville");
        graph.addEdge("New York", "Charlotte");
        graph.addEdge("Charlotte", "Atlanta");
        graph.addEdge("Nashville", "Atlanta");

        System.out.println("You can travel from Boston to Atlanta this way:");
        List<String> path = graph.findPath("Boston", "Atlanta");
        Collections.reverse(path);
        System.out.println(path);

        System.out.println("----------------------");

        System.out.println("You can travel from Boston to Honolulu this way:");
        path = graph.findPath("Boston", "Honolulu");
        Collections.reverse(path);
        System.out.println(path);
    }
}
