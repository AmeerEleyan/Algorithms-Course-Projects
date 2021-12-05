package gps;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Circle cr1;

    @FXML
    private Circle cr2;
    @FXML
    private AnchorPane anchorPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Line l = new Line();
        l.setStartX(cr1.getLayoutX());
        l.setStartY(cr1.getLayoutY());
        l.setEndX(cr2.getLayoutX());
        l.setEndY(cr2.getLayoutY());
        anchorPane.getChildren().addAll(l);
    }
}
