package com.serch;

import java.util.*;

public class Graph {
    private Map<String, Map<String, Integer>> graph;

    public Graph() {
        graph = new HashMap<>();
    }

    public void addVertex(String label) {
        if (!graph.containsKey(label)) {
            graph.put(label, new HashMap<>());
        }
    }

    public void addEdge(String sourceLabel, String destLabel, int weight) {
        if (!graph.containsKey(sourceLabel)) {
            addVertex(sourceLabel);
        }
        if (!graph.containsKey(destLabel)) {
            addVertex(destLabel);
        }
        graph.get(sourceLabel).put(destLabel, weight);
        graph.get(destLabel).put(sourceLabel, weight);
    }

    public int getEdgeWeight(String sourceLabel, String destLabel) {
        if (graph.containsKey(sourceLabel) && graph.get(sourceLabel).containsKey(destLabel)) {
            return graph.get(sourceLabel).get(destLabel);
        }
        return -1; // indicates that the edge doesn't exist
    }

    public Set<String> getVertices() {
        return graph.keySet();
    }

    public Set<String> getNeighbors(String label) {
        if (graph.containsKey(label)) {
            return graph.get(label).keySet();
        }
        return new HashSet<>(); // empty set if label is not in graph
    }

    public void printGraph() {
        for (String vertex : graph.keySet()) {
            System.out.print(vertex + " -> ");
            for (String neighbor : graph.get(vertex).keySet()) {
                int weight = graph.get(vertex).get(neighbor);
                System.out.print("(" + neighbor + ", " + weight + ") ");
            }
            System.out.println();
        }
    }

    public List<String> DFS(String start, String end) {
        // check if start and end vertices exist in the graph
        if (!graph.containsKey(start) || !graph.containsKey(end)) {
            return null;
        }

        // use a stack to keep track of the DFS traversal
        //Last-In-First-Out
        Stack<String> stack = new Stack<>();

        // use a map to keep track of visited vertices and their parent vertices
        Map<String, String> visited = new HashMap<>();

        // initialize the stack and visited map with the start vertex
        stack.push(start);
        visited.put(start, null);

        while (!stack.isEmpty()) {
            String current = stack.pop();

            // check if we've reached the end vertex
            if (current.equals(end)) {
                break;
            }

            // add all unvisited neighbors to the stack
            for (String neighbor : graph.get(current).keySet()) {
                if (!visited.containsKey(neighbor)) {
                    stack.push(neighbor);
                    visited.put(neighbor, current);
                }
            }
        }

        // if we didn't reach the end vertex, there's no path
        if (!visited.containsKey(end)) {
            return null;
        }

        // backtrack from the end vertex to the start vertex to get the path
        List<String> path = new ArrayList<>();
        String current = end;
        while (current != null) {
            path.add(current);
            current = visited.get(current);
        }
        Collections.reverse(path);

        return path;
    }

    public List<String> bfs(String startLabel, String endLabel) {
        // Create a queue for BFS and a map to keep track of visited vertices
        //First-In-First-Out
        Queue<String> queue = new LinkedList<>();
        Map<String, String> visited = new HashMap<>();

        // Add the start vertex to the queue and mark it as visited
        queue.add(startLabel);
        visited.put(startLabel, null);

        // Keep traversing the graph until the queue is empty
        while (!queue.isEmpty()) {
            // Get the next vertex from the queue
            String currentVertex = queue.remove();

            // Check if it is the end vertex, if so, construct and return the path
            if (currentVertex.equals(endLabel)) {
                List<String> path = new ArrayList<>();
                while (currentVertex != null) {
                    path.add(currentVertex);
                    currentVertex = visited.get(currentVertex);
                }
                Collections.reverse(path);
                return path;
            }

            // Otherwise, add all unvisited neighbors of the current vertex to the queue
            for (String neighbor : getNeighbors(currentVertex)) {
                if (!visited.containsKey(neighbor)) {
                    queue.add(neighbor);
                    visited.put(neighbor, currentVertex);
                }
            }
        }

        // If no path was found, return null
        return null;
    }

    public int getCostOfPath(List<String> path) {
        if (path == null) {
            return -1; // no path found
        }

        int cost = 0;
        String currentVertex = path.get(0);

        for (int i = 1; i < path.size(); i++) {
            String nextVertex = path.get(i);
            int edgeCost = getEdgeWeight(currentVertex, nextVertex);
            if (edgeCost == -1) {
                return -1; // edge doesn't exist
            }
            cost += edgeCost;
            currentVertex = nextVertex;
        }

        return cost;
    }


}
