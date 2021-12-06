package gps;

import java.util.Arrays;

import java.util.HashMap;
import java.util.PriorityQueue;


public class Graph {

    private Vertex[] cities;
    HashMap<String, Vertex> hashMap = new HashMap<>(6);
    private static int numberOfCities = 0;


    public Graph(int numberOfCities) {
        this.cities = new Vertex[numberOfCities];
        this.hashMap = new HashMap<>(numberOfCities);
    }

    public void addNewCities(City city) {
        if (numberOfCities < this.cities.length) {
            if (isFound(city)) {
                Message.displayMessage("Warning", city + " is already existing");
            } else {
                this.cities[numberOfCities++] = new Vertex(city);
            }
        } else {
            Message.displayMessage("Warning", "You have exceeded the number of cities allowed to be added\nso " + city.getCityName() + " will not be added");
        }

    }

    public Graph(City[] cities) {
        this.cities = new Vertex[cities.length];
        for (int i = 0; i < cities.length; i++) {
            if (isFound(cities[i])) {
                Message.displayMessage("Warning", cities[i] + " is already existing");
            } else {
                this.cities[i] = new Vertex(cities[i]);
                hashMap.put(cities[i].getCityName(), this.cities[i]);
                numberOfCities++;
            }
        }

    }

    public void findShortestPath(String sourceCity, String destinationCity) {

        float[] distance = new float[numberOfCities];
        Arrays.fill(distance, Integer.MAX_VALUE);

        String[] path = new String[numberOfCities];

        PriorityQueue<Adjacent> priorityQueue = new PriorityQueue<>();
        int indexOfCurrent = getIndexOf(sourceCity);
        distance[indexOfCurrent] = 0;

        priorityQueue.add(new Adjacent(new City(sourceCity), 0));

        while (!priorityQueue.isEmpty()) {

            Adjacent current = priorityQueue.poll();

            for (Adjacent adjacent : cities[indexOfCurrent].getAdjacent()) {

                int indexAdjacent = getIndexOf(adjacent.getAdjacentCity().getCityName());

                if (distance[indexOfCurrent] + adjacent.getDistance() < distance[indexAdjacent]) {
                    distance[indexAdjacent] = distance[indexOfCurrent] + adjacent.getDistance();
                    path[indexAdjacent] = current.getAdjacentCity().getCityName();
                    priorityQueue.add(new Adjacent(new City(adjacent.getAdjacentCity().getCityName()), distance[indexAdjacent]));
                }
            }
            indexOfCurrent = getIndexOf(current.getAdjacentCity().getCityName());

        }
        System.out.println(Arrays.toString(distance));
        System.out.println(Arrays.toString(path));

    }

    private int getIndexOf(String city) {
        for (int i = 0; i < cities.length; i++) {
            if (cities[i] != null) {
                if (cities[i].getCity().getCityName().compareTo(city) == 0) {
                    return i;
                }
            }
        }
        return -1; // not found
    }

    // Check if city is existing in the list
    private boolean isFound(City city) {
        for (Vertex vertex : cities) {
            if (vertex != null) {
                if (vertex.getCity().equals(city)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Add adjacent of the given city
    public void addAdjacent(String parentName, String adjacentName) {
        City city = hashMap.get(parentName.trim()).getCity();
        City adjacent = hashMap.get(adjacentName.trim()).getCity();
        if (hashMap.get(city.getCityName()) != null)
            hashMap.get(city.getCityName()).addAdjacent(adjacent);
    }

    public static int getNumberOfCities() {
        return numberOfCities;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Vertex city : cities) {
            result.append(city.toString()).append("\n");
        }
        return result.toString();
    }
}
