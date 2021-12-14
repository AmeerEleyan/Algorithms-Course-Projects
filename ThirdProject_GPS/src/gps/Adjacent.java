/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 5/12/2021    8:51 PM
 */
package gps;

public class Adjacent implements Comparable<Adjacent>{

    private final City adjacentCity;
    private float distance;

    // use this constructor when add adjacent to check city name
    public Adjacent(City adjacentCity) {
        this.adjacentCity = adjacentCity;
    }

    public Adjacent(City adjacentCity, float distance) {
        this.adjacentCity = adjacentCity;
        this.distance = distance;
    }

    public City getAdjacentCity() {
        return adjacentCity;
    }

    public float getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return adjacentCity.toString() + ", distance: " + distance + "";
    }

    @Override
    public boolean equals(Object o) {
        return this.adjacentCity.getCityName().compareTo(((Adjacent) o).adjacentCity.getCityName()) == 0;
    }

    @Override
    public int compareTo(Adjacent o) {
        return Float.compare(this.distance, o.distance);
    }
}
