/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 5/12/2021    9:02 PM
 */
package gps;

import java.util.LinkedList;

public class Vertex {

    private City city;
    private final LinkedList<Adjacent> adjacent;

    public Vertex(City city) {
        this.city = city;
        this.adjacent = new LinkedList<>();
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public LinkedList<Adjacent> getAdjacent() {
        return adjacent;
    }

    public void addAdjacent(City adjacentCity) {
        this.adjacent.addFirst(new Adjacent(adjacentCity, getDistance(adjacentCity)));
    }

    private int getDistance(City destinationGraph) {
        float differenceX = destinationGraph.getLayoutX() - this.city.getLayoutX();
        float distanceY = destinationGraph.getLayoutY() - this.city.getLayoutY();
        return (int) Math.sqrt(Math.pow(differenceX, 2) + Math.pow(distanceY, 2));
    }

    @Override
    public String toString() {
        return city.toString() + ": AdjacentCity =>"  +  adjacent;
    }
}
