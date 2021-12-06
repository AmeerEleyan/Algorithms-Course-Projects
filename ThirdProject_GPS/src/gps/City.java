/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 5/12/2021    3:19 PM
 */
package gps;

public class City implements Comparable<City> {

    // Attribute
    private String cityName;
    private float layoutX;
    private float layoutY;

    // Empty constructor
    public City(String cityName) {
        this.cityName = cityName.trim();
    }

    // Constructor with parameters
    public City(String cityName, float layoutX, float layoutY) {
        this.cityName = cityName.trim();
        this.layoutX = layoutX;
        this.layoutY = layoutY;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName.trim();
    }

    public float getLayoutX() {
        return layoutX;
    }

    public void setLayoutX(float layoutX) {
        this.layoutX = layoutX;
    }

    public float getLayoutY() {
        return layoutY;
    }

    public void setLayoutY(float layoutY) {
        this.layoutY = layoutY;
    }

    @Override
    public String toString() {
        return "CityName: " + cityName +
                ", layoutX=" + layoutX +
                ", layoutY=" + layoutY;
    }

    @Override
    public int compareTo(City other) {
        return cityName.compareTo(other.cityName);
    }

}
