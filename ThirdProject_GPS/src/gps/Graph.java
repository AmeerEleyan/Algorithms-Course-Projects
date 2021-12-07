package gps;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;


public class Graph {

    private final Vertex[] cities;
    HashMap<String, Vertex> hashMap = new HashMap<>();
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

        // Initialize the hash map
        HashMap<String, DijkstraTable> table = new HashMap<>();
        for (Vertex city : cities) {
            table.put(city.getCity().getCityName(), new DijkstraTable(city));
        }
        table.get(sourceCity.trim()).setDistance(0);

        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();

        priorityQueue.add(table.get(sourceCity.trim()).getCityVertex());

        float edgeDistance;
        float newDistance;
        float currentDistance;
        while (!priorityQueue.isEmpty()) {
            Vertex current = priorityQueue.poll();
            for (Adjacent adjacent : current.getAdjacent()) {
                edgeDistance = adjacent.getDistance(); // between them
                currentDistance = table.get(current.getCity().getCityName()).getDistance();// for it from table
                newDistance = edgeDistance + currentDistance;

                if (newDistance < table.get(adjacent.getAdjacentCity().getCityName()).getDistance()) {
                    table.get(adjacent.getAdjacentCity().getCityName()).setDistance(newDistance);
                    table.get(adjacent.getAdjacentCity().getCityName()).setPath(current.getCity().getCityName());
                    if (!table.get(adjacent.getAdjacentCity().getCityName()).isKnown()) {
                        Vertex newVertex = hashMap.get(adjacent.getAdjacentCity().getCityName());
                        priorityQueue.add(newVertex);
                        table.get(adjacent.getAdjacentCity().getCityName()).setKnown(true);
                    }
                }

            }

        }
        float totalDistance = table.get(destinationCity).getDistance();
        LinkedList<City> cities = new LinkedList<>();
        String path = this.hashMap.get(destinationCity).getCity().getCityName();
        while (path != null) {
            cities.addFirst(this.hashMap.get(path).getCity());
            path = table.get(path).getPath();
        }
        System.out.println("Total distance: " + totalDistance);
        System.out.println("Path:\n" + cities);
        System.out.println(table);

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
