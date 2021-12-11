/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 5/12/2021    8:51 PM
 */
package gps;

public class Adjacent implements Comparable<Adjacent>{

    private final City adjacentCity;
    private final float distance;

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
    public int compareTo(Adjacent o) {
        return Float.compare(this.distance, o.distance);
    }
}
