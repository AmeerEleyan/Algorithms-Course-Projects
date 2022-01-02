/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 3/1/2022   1:02 AM
 */
package gps;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Graph {

    private final HashMap<String, Vertex> hashMap;
    private final int numberOfCities;
    private final HashMap<String, DijkstraTable> table;

    //Using LinkedHashSet we can prevent insertion of duplicate elements
    // as it implements the Set interface
    private LinkedHashSet<String> visitedCityInPreviousPath;

    public Graph(int numberOfCities) {
        this.hashMap = new HashMap<>();
        this.numberOfCities = numberOfCities;
        this.table = new HashMap<>();
        this.visitedCityInPreviousPath = new LinkedHashSet<>();
    }

    public void addNewCities(City city) {
        if (this.hashMap.size() < this.numberOfCities) {
            if (this.hashMap.get(city.getCityName()) != null) {
                Message.displayMessage("Warning", city.getCityName() + " is already existing");
            } else {
                this.hashMap.put(city.getCityName(), new Vertex(city));
                this.table.put(city.getCityName(), new DijkstraTable());
            }
        } else {
            Message.displayMessage("Warning", "You have exceeded the number of cities allowed to be added\nso " + city.getCityName() + " will not be added");
        }

    }

    // Find the shortest path between two cities
    public ShortestPath findShortestPath(String sourceCity, String destinationCity) {

        // there is no adjacent fore source city. so there is no path from source city to destination city
        if (this.hashMap.get(sourceCity).getAdjacent().size() == 0) {
            return null;
        }

        // reset the hash map to default values
        if (this.visitedCityInPreviousPath.size() != 0) {
            for (String city : this.visitedCityInPreviousPath) {
                this.table.get(city).reSetValue();
            }
        }

        // change distance of the source city to zero; because the pat with itself zero
        this.table.get(sourceCity.trim()).setDistance(0);
        this.visitedCityInPreviousPath = new LinkedHashSet<>();
        PriorityQueue<Pair<String, Float>> priorityQueue = new PriorityQueue<>((o1, o2) -> Float.compare(o1.getValue(), o2.getValue()));
        priorityQueue.add(new Pair<>(sourceCity, 0f));

        float currentDistance; // distance for current vertex in  dijkstra table
        float newDistance; // summation for the above two variable
        float adjacentInTable; // distance for the adjacent in dijkstra table

        while (!priorityQueue.isEmpty()) {

            String vertex = priorityQueue.poll().getKey();
            Vertex current = this.hashMap.get(vertex);

            if (this.table.get(current.getCity().getCityName()).isVisited()) {
                continue;
            } else {
                this.table.get(current.getCity().getCityName()).setVisited(true);
                // add this city(that was changed its values in table) to list that 
                if (this.table.get(current.getCity().getCityName()).getPath() == null)
                    this.visitedCityInPreviousPath.add(current.getCity().getCityName());
            }

            for (Adjacent adjacent : current.getAdjacent()) {
                currentDistance = this.table.get(current.getCity().getCityName()).getDistance();
                // adjacent.getDistance(): between current vertex and his adjacent
                newDistance = adjacent.getDistance() + currentDistance;
                adjacentInTable = this.table.get(adjacent.getAdjacentCity().getCityName()).getDistance();

                if (newDistance < adjacentInTable) {
                    this.table.get(adjacent.getAdjacentCity().getCityName()).setDistance(newDistance);
                    this.table.get(adjacent.getAdjacentCity().getCityName()).setPath(current.getCity().getCityName());
                    // add this city(that was changed its values in table) to list that
                    if (!this.table.get(adjacent.getAdjacentCity().getCityName()).isVisited())
                        this.visitedCityInPreviousPath.add(adjacent.getAdjacentCity().getCityName());
                    priorityQueue.add(new Pair<>(adjacent.getAdjacentCity().getCityName(), newDistance));
                }
            }
            // check if destination is current(the best path)
            if (destinationCity.equals(current.getCity().getCityName())) {
                break;
            }
        }

        if (this.table.get(destinationCity).getPath() == null) { // there is no path to destination city
            return null;
        }

        // get total distance from source to destination
        float totalDistance = this.table.get(destinationCity).getDistance();

        // get the cities in the path from source to destination
        LinkedList<City> citiesInThePath = new LinkedList<>();

        String path = this.hashMap.get(destinationCity).getCity().getCityName();
        while (path != null) {
            citiesInThePath.addFirst(this.hashMap.get(path).getCity());
            path = this.table.get(path).getPath();
        }
        return new ShortestPath(totalDistance, citiesInThePath);

    }


    // Add adjacent to each other, because the road is two direction
    public void addAdjacent(String parentName, String adjacentName) {
        //Check them if they are in the hash
        if (this.hashMap.get(parentName.trim()) != null && this.hashMap.get(adjacentName.trim()) != null) {
            City city = this.hashMap.get(parentName.trim()).getCity();
            City adjacent = this.hashMap.get(adjacentName.trim()).getCity();
            if (this.hashMap.get(parentName.trim()).getAdjacent().contains(new Adjacent(adjacent))) {
                Message.displayMessage("Warning", parentName + " and " + adjacentName + " is already existing as neighbors");
            } else {
                this.hashMap.get(city.getCityName()).addAdjacent(adjacent);
                this.hashMap.get(adjacent.getCityName()).addAdjacent(city);
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
