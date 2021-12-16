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
        //this.setLayout_X_Map(longitude);
        //this.setLayout_Y_Map(latitude);
    }

    public void setLayout_X_Map(float longitude) {
        float longBeisan = 35.4973f;// long for most right city
        float diffInLong = 1.2715f; // 35.4973-34.2222:long for most left city
        short diffX = 325;//455-130, 455: x for most right city, 130: x for most left city
        short x = (short) (((longBeisan - longitude) * diffX) / diffInLong);
        x = (short) (455 - x);
        this.layout_X_Map = x;
    }

    public void setLayout_Y_Map(float latitude) {
        float laltiNorth = 33.2757f; // latitude  for most north city
        float diffInLati = 3.7118f; // 33.2757-29.5639: latitude for most south city
        short diffY = -770; // 50 - 820, 50: y for most north city, 820: y for most south city
        short y = (short) (((laltiNorth - latitude) * diffY) / diffInLati);
        y = (short) ((50 - y) + 10);
        this.layout_Y_Map = y;
    }

    public short getLayout_X_Map() {
        return layout_X_Map;
    }

    public short getLayout_Y_Map() {
        return layout_Y_Map;
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
