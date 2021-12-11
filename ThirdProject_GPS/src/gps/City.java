/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 5/12/2021    3:19 PM
 */
package gps;

public class City implements Comparable<City> {

    // Attribute
    private String cityName;

    private float longitude;
    private float latitude;

    // Empty constructor
    public City(String cityName) {
        this.cityName = cityName.trim();
    }

    // Constructor with parameters
    public City(String cityName, float longitude, float latitude) {
        this.cityName = cityName.trim();
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "City" +
                "cityNam:" + cityName +
                ", longitude: " + longitude +
                ", latitude: " + latitude +"\n";
    }

    @Override
    public int compareTo(City other) {
        return cityName.compareTo(other.cityName);
    }

}
