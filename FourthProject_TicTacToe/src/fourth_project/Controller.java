package fourth_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ComboBox<String> comboScenario;

    @FXML
    private ComboBox<String> comboType;

    private ObservableList<Node> movements;

    @FXML
    private Rectangle rectangle00, rectangle01, rectangle02,
            rectangle10, rectangle11, rectangle12,
            rectangle20, rectangle21, rectangle22;

    @FXML
    private TextField txtResult;

    // type true: X
    // type false: O
    private boolean[][] matrix;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboScenario.getItems().addAll("Easy Level", "With Your Friend", "Hard Level");
        this.comboType.getItems().addAll("X", "O");
        this.matrix = new boolean[3][3];
        this.movements = FXCollections.observableArrayList(); // all movements in the path;
    }


    public void handleRestartButton() {
        this.reset();
    }

    // Restart game
    private void reset() {
        this.matrix = new boolean[3][3];
        this.anchorPane.getChildren().removeAll(this.movements);
        this.movements = FXCollections.observableArrayList(); // all movements in the path;
    }

    @FXML
    void handleMouseEvent(MouseEvent event) {
        if (this.comboScenario.getValue() == null) {
            Message.displayMessage("Warning", "Plese select the scenario");
            return;
        }
        if (this.comboType.getValue() == null) {
            Message.displayMessage("Warning", "Please select your type");
            return;
        }
        Node node = event.getPickResult().getIntersectedNode();
        boolean playerType = this.comboType.getValue().equals("X");
        if (node instanceof Rectangle) {
            if (event.getButton() == MouseButton.PRIMARY) {
                Node n;
                if (node.getId().equals("rectangle00")) {
                    this.matrix[0][0] = playerType;
                }else if(node.getId().equals("rectangle01")){
                    this.matrix[0][1] = playerType;
                }else if(node.getId().equals("rectangle02")){
                    this.matrix[0][2] = playerType;
                }else if(node.getId().equals("rectangle10")){
                    this.matrix[1][0] = playerType;
                }else if(node.getId().equals("rectangle11")){
                    this.matrix[1][1] = playerType;
                }else if(node.getId().equals("rectangle12")){
                    this.matrix[1][2] = playerType;
                }else if(node.getId().equals("rectangle20")){
                    this.matrix[2][0] = playerType;
                }else if(node.getId().equals("rectangle21")){
                    this.matrix[2][1] = playerType;
                }else{
                    this.matrix[2][2] = playerType;
                }
                if(playerType){
                    n =  this.drawX(node.getLayoutX(), node.getLayoutY());
                }else{
                    n = this.drawO(node.getLayoutX(), node.getLayoutY());
                }
                this.movements.add(n);
                this.anchorPane.getChildren().add(n);
            }
        }
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

    private byte isWin(boolean type) {
        // type true: X
        // type false: O

        // 1-3: represent rows
        // 4-6: represent columns
        // 7: right diagonal
        // 8: left diagonal


        // check rows
        for (int i = 0; i < 3; ++i) {
            if (type) {
                if (this.matrix[i][0] && this.matrix[i][1] && this.matrix[i][2]) return (byte) (i + 1);
            } else {
                if (!this.matrix[i][0] && !this.matrix[i][1] && !this.matrix[i][2]) return (byte) (i + 1);
            }
        }

        // check columns
        for (int j = 0; j < 3; ++j) {
            if (type) {
                if (this.matrix[0][j] && this.matrix[1][j] && this.matrix[2][j]) return (byte) (j + 4);
            } else {
                if (!this.matrix[0][j] && !this.matrix[1][j] && !this.matrix[2][j]) return (byte) (j + 4);
            }
        }

        // right diagonal
        if (type) {
            if (this.matrix[0][0] && this.matrix[1][1] && this.matrix[2][2]) return 7;
        } else {
            if (!this.matrix[0][0] && !this.matrix[1][1] && !this.matrix[2][2]) return 7;
        }

        // left diagonal
        if (type) {
            if (this.matrix[0][2] && this.matrix[1][1] && this.matrix[2][0]) return 8;
        } else {
            if (!this.matrix[0][2] && !this.matrix[1][1] && !this.matrix[2][0]) return 8;
        }

        return -1;
    }
}
