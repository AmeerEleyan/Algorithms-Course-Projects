/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 5/12/2021    3:19 PM
 */
package gps;

public class City implements Comparable<City> {

    // Attribute
    private final String cityName;

    private float longitude;
    private float latitude;
    private short layout_X_Map;
    private short layout_Y_Map;

    // Empty constructor
    public City(String cityName) {
        this.cityName = cityName.trim();
    }

    // Constructor with parameters
    public City(String cityName, float longitude, float latitude, short layout_X_Map, short layout_Y_Map) {
        this.cityName = cityName.trim();
        this.longitude = longitude;
        this.latitude = latitude;
        this.layout_X_Map = layout_X_Map;
        this.layout_Y_Map = layout_Y_Map;
    }

    public String getCityName() {
        return cityName;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getLayouts_Map() {
        return this.layout_X_Map + " " + this.layout_Y_Map;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityName='" + cityName +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", layout_X_Map=" + layout_X_Map +
                ", layout_Y_Map=" + layout_Y_Map +
                '}';
    }

    @Override
    public int compareTo(City other) {
        return cityName.compareTo(other.cityName);
    }

}
