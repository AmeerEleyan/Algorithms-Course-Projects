/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 11/12/2021    4:07 AM
 */
package gps;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;


public class Graph {

    private final Vertex[] cities;
    private final HashMap<String, Vertex> hashMap;
    private static int numberOfCities = 0;


    public Graph(int numberOfCities) {
        this.cities = new Vertex[numberOfCities];
        this.hashMap = new HashMap<>(numberOfCities);
    }

    public void addNewCities(City city) {
        if (numberOfCities < this.cities.length) {
            if (this.hashMap.get(city.getCityName()) != null) {
                Message.displayMessage("Warning", city + " is already existing");
            } else {
                this.cities[numberOfCities] = new Vertex(city);
                this.hashMap.put(city.getCityName(), this.cities[numberOfCities]);
                numberOfCities++;
            }
        } else {
            Message.displayMessage("Warning", "You have exceeded the number of cities allowed to be added\nso " + city.getCityName() + " will not be added");
        }

    }

    public ShortestPath findShortestPath(String sourceCity, String destinationCity) {

        // there is no adjacent fore source city. so there is no path from source city to distinction city
        if (this.hashMap.get(sourceCity).getAdjacent().size() == 0) {
            return null;
        }

        // Initialize the hash map
        HashMap<String, DijkstraTable> table = new HashMap<>();
        for (Vertex city : cities) {
            table.put(city.getCity().getCityName(), new DijkstraTable(city.getCity().getCityName()));
        }
        table.get(sourceCity.trim()).setDistance(0);

        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();

        priorityQueue.add(this.hashMap.get(sourceCity));
        table.get(sourceCity.trim()).setKnown(true);

        float edgeDistance;
        float newDistance;
        float currentDistance;
        boolean isFound = false;
        while (!priorityQueue.isEmpty() && !isFound) {
            Vertex current = priorityQueue.poll();
            for (Adjacent adjacent : current.getAdjacent()) {
                edgeDistance = adjacent.getDistance(); // between them
                currentDistance = table.get(current.getCity().getCityName()).getDistance();// for it from table
                newDistance = edgeDistance + currentDistance;

                if (newDistance < table.get(adjacent.getAdjacentCity().getCityName()).getDistance()) {
                    table.get(adjacent.getAdjacentCity().getCityName()).setDistance(newDistance);
                    table.get(adjacent.getAdjacentCity().getCityName()).setPath(current.getCity().getCityName());
                    if (!table.get(adjacent.getAdjacentCity().getCityName()).isKnown()) {
                        Vertex newVertex =  this.hashMap.get(adjacent.getAdjacentCity().getCityName());
                        priorityQueue.add(newVertex);
                        table.get(adjacent.getAdjacentCity().getCityName()).setKnown(true);
                    }
                    if (adjacent.getAdjacentCity().getCityName().equals(destinationCity)) {
                        isFound = true;
                        break;
                    }
                }
            }
        }
        float totalDistance = table.get(destinationCity).getDistance();
        LinkedList<City> citiesInThePath = new LinkedList<>();
        String path = this.hashMap.get(destinationCity).getCity().getCityName();
        while (path != null) {
            citiesInThePath.addFirst(this.hashMap.get(path).getCity());
            path = table.get(path).getPath();
        }
        return new ShortestPath(totalDistance, citiesInThePath);


    }

    // Add adjacent of the given city
    public void addAdjacent(String parentName, String adjacentName) {
        City city = hashMap.get(parentName.trim()).getCity();
        City adjacent = hashMap.get(adjacentName.trim()).getCity();
        if (hashMap.get(city.getCityName()) != null) {
            hashMap.get(city.getCityName()).addAdjacent(adjacent);
            hashMap.get(adjacent.getCityName()).addAdjacent(city);
        }

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
