
package gps;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane anchorPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CreateCityInTheMap city1 = new CreateCityInTheMap("Ramallah",390,351);
        CreateCityInTheMap city2 = new CreateCityInTheMap("Jenen",244,428);
        CreateCityInTheMap city3 = new CreateCityInTheMap("Jerusalem",316,322);
        anchorPane.getChildren().addAll(city1.getCityName(),city1.getCityPosition() ,
                city2.getCityName(),city2.getCityPosition(), city3.getCityName(),city3.getCityPosition());
        Line l = new Line();
        l.setStartX(city1.getCityPosition().getLayoutX());
        l.setStartY(city1.getCityPosition().getLayoutY());
        l.setEndX(city2.getCityPosition().getLayoutX());
        l.setEndY(city2.getCityPosition().getLayoutY());

        Line l2 = new Line();
        l2.setStartX(city1.getCityPosition().getLayoutX());
        l2.setStartY(city1.getCityPosition().getLayoutY());
        l2.setEndX(city3.getCityPosition().getLayoutX());
        l2.setEndY(city3.getCityPosition().getLayoutY());
        anchorPane.getChildren().addAll(l, l2);

    }
}
