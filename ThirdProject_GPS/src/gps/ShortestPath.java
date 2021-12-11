/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 8/12/2021    2:14 AM
 */
package gps;

import java.util.LinkedList;

public class ShortestPath {

    private final float totalDistance;
    private final LinkedList<City> citiesInThePath;

    public ShortestPath(float totalDistance, LinkedList<City> citiesInThePath) {
        this.totalDistance = totalDistance;
        this.citiesInThePath = citiesInThePath;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public LinkedList<City> getCitiesInThePath() {
        return citiesInThePath;
    }

}
