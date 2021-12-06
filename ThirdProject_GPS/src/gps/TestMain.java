package gps;

import java.util.Arrays;
import java.util.Hashtable;

public class TestMain {
    public static void main(String[] args) {
        City[] cities = new City[6];

        cities[0] = new City("City1", 1000, 2400);
        cities[1] = new City("City2", 2800, 3000);
        cities[2] = new City("City3", 2400, 2500);
        cities[3] = new City("City4", 4000, 0);
        cities[4] = new City("City5", 4500, 3800);
        cities[5] = new City("City6", 6000, 1500);


        Graph graph = new Graph(cities);

        graph.addAdjacent(cities[0], cities[1]);
        graph.addAdjacent(cities[0], cities[3]);
        graph.addAdjacent(cities[1], cities[2]);
        graph.addAdjacent(cities[1], cities[4]);
        graph.addAdjacent(cities[2], cities[4]);
        graph.addAdjacent(cities[2], cities[3]);
        graph.addAdjacent(cities[2], cities[5]);
        graph.addAdjacent(cities[3], cities[5]);
        graph.addAdjacent(cities[4], cities[5]);
        graph.findShortestPath("City1","City6");


    }
}
