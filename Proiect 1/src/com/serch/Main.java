package com.serch;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        Graph graph = new Graph();
        //create the Graph from all the distances
        try {
            FileReader fileReader = new FileReader("D:\\facultate\\an 2 semestrul 2\\problem solving\\Proiect 1\\resources\\distante.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            String src, desc;
            int weight;

            while (line != null) {
                String[] words = line.split(" ");
                src = words[0];
                desc = words[1];
                weight = Integer.valueOf(words[2]);
                graph.addEdge(src, desc, weight);

                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        graph.printGraph();

//BFS Algorithm
        System.out.println("\nBFS Algorithm " );
        long startTime = System.nanoTime(); // record start time

        List<String> path = graph.bfs("bucuresti", "botosani");

        long endTime = System.nanoTime(); // record end time
        long duration = (endTime - startTime) ; // calculate duration in nanoseconds

        if (path != null) {
            System.out.print("Path found: " + path);
            int cost = graph.getCostOfPath(path);
            System.out.println("\nCost: " + cost);
        } else {
            System.out.println("No path found.");
        }

        System.out.println("Duration: " + duration + "ms"); // print duration

//DFS Algorithm
        System.out.println("\nDFS Algorithm " );
        long startTime2 = System.nanoTime(); // record start time

        List<String> path2 = graph.DFS("bucuresti", "botosani");

        long endTime2 = System.nanoTime(); // record end time
        long duration2 = (endTime2 - startTime2); // calculate duration in nanoseconds

        if (path2 != null) {
            System.out.print("Path found: " + path2);
            int cost2 = graph.getCostOfPath(path2);
            System.out.println("\nCost: " + cost2);
        } else {
            System.out.println("No path found.");
        }

        System.out.println("Duration: " + duration2 + "ms"); // print duration


    }


}



