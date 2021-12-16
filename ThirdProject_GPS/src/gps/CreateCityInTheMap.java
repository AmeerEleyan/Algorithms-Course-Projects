/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 5/12/2021    6:32 PM
 */
package gps;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class CreateCityInTheMap {
    private final Circle cityPosition;
    private final Label cityName;

    public CreateCityInTheMap(String cityName, float layout_X_Map, float layout_Y_Map) {
        this.cityPosition = new Circle(4);
        this.cityPosition.setLayoutX(layout_X_Map);
        this.cityPosition.setLayoutY(layout_Y_Map);
        this.cityPosition.setStroke(Color.BLACK);
        this.cityPosition.setFill(Color.RED);
        this.cityPosition.setStrokeType(StrokeType.INSIDE);
        this.cityPosition.toFront(); // Clickable

        this.cityName = new Label(cityName);
        this.cityName.setLayoutX(this.cityPosition.getLayoutX() - 15);
        this.cityName.setLayoutY(this.cityPosition.getLayoutY() + 3);
        this.cityName.setStyle("-fx-text-fill: #000000;");
        this.cityName.toBack();

        // To change the design of the circle when placing a mouse arrow on it
        this.cityPosition.setOnMouseEntered(e -> {
            this.cityPosition.setFill(Color.BLUE);
            this.cityPosition.setRadius(5.5);
            this.cityName.setStyle("-fx-font-weight: Bold; -fx-text-fill: #000000; -fx-font-size: 13;");

        });
        // To change the design of the circle when the mouse arrow is removed from it
        this.cityPosition.setOnMouseExited(e -> {

            this.cityPosition.setFill(Color.RED);
            this.cityPosition.setRadius(4);
            this.cityName.setStyle("-fx-font-size: 12; -fx-text-fill: #000000;");

        });
    }

    public Circle getCityPosition() {
        return cityPosition;
    }

    public Label getCityName() {
        return cityName;
    }
}
