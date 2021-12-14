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

    private final HashMap<String, Vertex> hashMap;
    private final int numberOfCities;


    public Graph(int numberOfCities) {
        this.hashMap = new HashMap<>();
        this.numberOfCities = numberOfCities;
    }

    public void addNewCities(City city) {
        if (this.hashMap.size() < this.numberOfCities) {
            if (this.hashMap.get(city.getCityName()) != null) {
                Message.displayMessage("Warning", city.getCityName() + " is already existing");
            } else {
                this.hashMap.put(city.getCityName(), new Vertex(city));
            }
        } else {
            Message.displayMessage("Warning", "You have exceeded the number of cities allowed to be added\nso " + city.getCityName() + " will not be added");
        }

    }

    public ShortestPath findShortestPath(String sourceCity, String destinationCity) {

        // there is no adjacent fore source city. so there is no path from source city to destination city
        if (this.hashMap.get(sourceCity).getAdjacent().size() == 0) {
            return null;
        }

        // Initialize the hash map
        // Key: city name, Value: dijkstra table(city name, isKnown, distance, path)
        HashMap<String, DijkstraTable> table = new HashMap<>();
        for (Vertex city : this.hashMap.values()) {
            table.put(city.getCity().getCityName(), new DijkstraTable(city.getCity().getCityName()));
        }
        // change distance of the source city to zero; because the pat with itself zero
        table.get(sourceCity.trim()).setDistance(0);

        PriorityQueue<Adjacent> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new Adjacent(new City(sourceCity),0));

        float currentDistance; // distance for current vertex in  dijkstra table
        float newDistance; // summation for the above two variable
        float adjacentInTable; // distance for the adjacent in dijkstra table
        boolean isFound = false;

        while (!priorityQueue.isEmpty() && !isFound) {

            String vertex = priorityQueue.poll().getAdjacentCity().getCityName();
            Vertex current = this.hashMap.get(vertex);

            if (table.get(current.getCity().getCityName()).isVisited()) {
                continue;
            } else {
                table.get(current.getCity().getCityName()).setVisited(true);
            }

            for (Adjacent adjacent : current.getAdjacent()) {

                currentDistance = table.get(current.getCity().getCityName()).getDistance();
                // adjacent.getDistance(): between current vertex and his adjacent
                newDistance = adjacent.getDistance() + currentDistance;
                adjacentInTable = table.get(adjacent.getAdjacentCity().getCityName()).getDistance();

                if (newDistance < adjacentInTable) {
                    table.get(adjacent.getAdjacentCity().getCityName()).setDistance(newDistance);
                    table.get(adjacent.getAdjacentCity().getCityName()).setPath(current.getCity().getCityName());
                    priorityQueue.add(new Adjacent(new City(adjacent.getAdjacentCity().getCityName()), newDistance));
                }

            }
        }

        // get total distance from source to destination
        float totalDistance = table.get(destinationCity).getDistance();
        // get the cities in the path from source to destination
        LinkedList<City> citiesInThePath = new LinkedList<>();

        String path = this.hashMap.get(destinationCity).getCity().getCityName();
        while (path != null) {
            citiesInThePath.addFirst(this.hashMap.get(path).getCity());
            path = table.get(path).getPath();
        }
        return new ShortestPath(totalDistance, citiesInThePath);

    }

    // Add adjacent to each other, because the road is two direction
    public void addAdjacent(String parentName, String adjacentName) {
        //Check them if they are in the hash
        if (hashMap.get(parentName.trim()) != null && hashMap.get(adjacentName.trim()) != null) {
            City city = hashMap.get(parentName.trim()).getCity();
            City adjacent = hashMap.get(adjacentName.trim()).getCity();
            if (hashMap.get(parentName.trim()).getAdjacent().contains(new Adjacent(adjacent))) {
                Message.displayMessage("Warning", parentName + " and " + adjacentName + " is already existing as neighbors");
            } else {
                hashMap.get(city.getCityName()).addAdjacent(adjacent);
                hashMap.get(adjacent.getCityName()).addAdjacent(city);
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Vertex city : this.hashMap.values()) {
            result.append(city.toString()).append("\n");
        }
        return result.toString();
    }
}
