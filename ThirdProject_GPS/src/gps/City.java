/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 5/12/2021    3:19 PM
 */
package gps;

public class City implements Comparable<City> {

    // Attribute
    private String cityName;
    private float layout_X_Map;
    private float layout_Y_Map;

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
        //this.layout_X_Map = layout_X_Map;
      //  this.layout_Y_Map = layout_Y_Map;
    }

    private float[] getLayout_X_MapFrom_longitude_latitude(float longitude, float latitude){
        return  null;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public float getLayout_X_Map() {
        return layout_X_Map;
    }

    public void setLayout_X_Map(float layout_X_Map) {
        this.layout_X_Map = layout_X_Map;
    }

    public float getLayout_Y_Map() {
        return layout_Y_Map;
    }

    public void setLayout_Y_Map(float layout_Y_Map) {
        this.layout_Y_Map = layout_Y_Map;
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
