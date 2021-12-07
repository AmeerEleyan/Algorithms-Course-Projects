/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 5/12/2021    9:02 PM
 */
package gps;

import java.util.LinkedList;

public class Vertex implements Comparable<Vertex> {

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

    // getting the distance between two adjacent city
    private float getDistance(City destinationCity) {
        return getDistance(this.city.getLatitude(), this.city.getLongitude(),
                destinationCity.getLatitude(), destinationCity.getLongitude());
    }

    // calculate distance between two cities(Km) depending on the latitude and longitude of them
    private float getDistance(float latitudeSource, float longitudeSource,
                              float latitudeDestination, float longitudeDestination) {
        latitudeSource = (float) Math.toRadians(latitudeSource);
        longitudeSource = (float) Math.toRadians(longitudeSource);

        latitudeDestination = (float) Math.toRadians(latitudeDestination);
        longitudeDestination = (float) Math.toRadians(longitudeDestination);


        // Haversine formula
        float distanceLat = latitudeDestination - latitudeSource;
        float distanceLong = longitudeDestination - longitudeSource;

        float distance = (float) (Math.pow(Math.sin(distanceLat / 2), 2)
                + Math.cos(latitudeSource) * Math.cos(latitudeDestination)
                * Math.pow(Math.sin(distanceLong / 2), 2));
        float c = (float) (2 * Math.asin(Math.sqrt(distance)));

        //The radius of the Earth in kilometres
        float r = 6371;
        return (c * r);
    }

    @Override
    public String toString() {
        return city.toString() + ": AdjacentCity =>" + adjacent;
    }

    @Override
    public int compareTo(Vertex other) {
        return this.city.compareTo(other.city);
    }
}
