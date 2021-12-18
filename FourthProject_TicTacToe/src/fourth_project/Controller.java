package fourth_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ComboBox<String> comboLevel;

    @FXML
    private ComboBox<String> comboType;

    @FXML
    private Rectangle rectangle00, rectangle01, rectangle02,
            rectangle10, rectangle11, rectangle12,
            rectangle20, rectangle21, rectangle22;

    @FXML
    private TextField txtResult;


    public void handleRestartButton() {

    }

    @FXML
    void handleMouseEvent(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();

        if (node instanceof Rectangle) {
            if (event.getButton() == MouseButton.PRIMARY) {
                this.anchorPane.getChildren().addAll(this.drawX(node.getLayoutX(), node.getLayoutY()));
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboLevel.getItems().addAll("Easy Level", "With Your Friend", "Hard Level");
        this.comboType.getItems().addAll("X", "O");
    }

    private Circle drawO(double layout_X, double layout_Y) {
        Circle circle = new Circle(60);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(4);
        circle.setLayoutX(layout_X + 100);
        circle.setLayoutY(layout_Y + 83);
        return circle;
    }

    private Group drawX(double layout_X, double layout_Y) {
        Line line = new Line();
        line.setRotate(45);
        line.setStartX(-100);
        line.setStartY(0);
        line.setEndX(100);
        line.setEndY(0);
        line.setStrokeWidth(4);
        line.setStroke(Color.BLACK);
        line.setLayoutX(layout_X + 100);
        line.setLayoutY(layout_Y + 83);

        Line line2 = new Line();
        line2.setRotate(-45);
        line2.setStrokeWidth(4);
        line2.setStartX(-100);
        line2.setStartY(0);
        line2.setEndX(100);
        line2.setEndY(0);
        line2.setStroke(Color.BLACK);
        line2.setLayoutX(layout_X + 100);
        line2.setLayoutY(layout_Y + 83);

        Group group = new Group();
        group.getChildren().addAll(line, line2);
        return group;
    }
}
