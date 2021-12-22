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

    private Line lineAnswer;

    // x, o, -:empty
    private char[][] matrix;
    private byte numberOfMovements = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboScenario.getItems().addAll("Easy Level", "With Your Friend", "Hard Level");
        this.comboType.getItems().addAll("X", "O");
        this.matrix = new char[][]{{'-', '-', '-'}, {'-', '-', '-'}, {'-', '-', '-'}};
        this.movements = FXCollections.observableArrayList(); // all movements in the path;
    }


    public void handleRestartButton() {
        this.reset();
    }

    // Restart game
    private void reset() {
        this.numberOfMovements = 0;
        this.comboScenario.setDisable(false);
        this.comboType.setDisable(false);
        this.matrix = new char[][]{{'-', '-', '-'}, {'-', '-', '-'}, {'-', '-', '-'}};
        this.anchorPane.getChildren().removeAll(this.movements);
        this.anchorPane.getChildren().remove(this.lineAnswer);
        this.lineAnswer = null;
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

        this.comboScenario.setDisable(true);
        this.comboType.setDisable(true);

        Node node = event.getPickResult().getIntersectedNode();
        char playerType = 'o';
        if (this.comboType.getValue().equals("X")) playerType = 'x';

        String scenario = this.comboScenario.getValue();

        if (node instanceof Rectangle) {

            if (event.getButton() == MouseButton.PRIMARY) {
                Node n;
                if (node.getId().equals("rectangle00")) {
                    this.matrix[0][0] = playerType;
                } else if (node.getId().equals("rectangle01")) {
                    this.matrix[0][1] = playerType;
                } else if (node.getId().equals("rectangle02")) {
                    this.matrix[0][2] = playerType;
                } else if (node.getId().equals("rectangle10")) {
                    this.matrix[1][0] = playerType;
                } else if (node.getId().equals("rectangle11")) {
                    this.matrix[1][1] = playerType;
                } else if (node.getId().equals("rectangle12")) {
                    this.matrix[1][2] = playerType;
                } else if (node.getId().equals("rectangle20")) {
                    this.matrix[2][0] = playerType;
                } else if (node.getId().equals("rectangle21")) {
                    this.matrix[2][1] = playerType;
                } else {
                    this.matrix[2][2] = playerType;
                }
                if (playerType == 'x') {
                    n = this.drawX(node.getLayoutX(), node.getLayoutY());
                } else {
                    n = this.drawO(node.getLayoutX(), node.getLayoutY());
                }
                this.movements.add(n);
                this.anchorPane.getChildren().add(n);

                // check senario
                if (scenario.equals("Easy Level")) {
                    this.easySenario();
                } else if (scenario.equals("With Your Friend")) {
                    this.twoPlayerSenario();
                } else {
                    this.hardSenario();
                }
                this.numberOfMovements++;
            }
        }
    }


    private void easySenario() {

    }

    private void twoPlayerSenario() {

    }

    private void hardSenario() {

    }

    private byte random_AI() {
        return 0;
    }

    private void displayAnswerLine(byte isWin) {
        // find answer lineAnswer and draw it
        this.lineAnswer = new Line();
        this.lineAnswer.setStrokeWidth(5);
        this.lineAnswer.setStroke(Color.BLACK);
        switch (isWin) {
            case 1 -> {
                // answer in first row
                this.lineAnswer.setStartX(this.rectangle00.getArcWidth() / 2);
                this.lineAnswer.setStartY(this.rectangle00.getHeight() / 2);
                this.lineAnswer.setEndX(this.rectangle02.getArcWidth() / 2);
                this.lineAnswer.setEndY(this.rectangle02.getHeight() / 2);
            }
            case 2 -> {
                // answer in second row
                this.lineAnswer.setStartX(this.rectangle10.getArcWidth() / 2);
                this.lineAnswer.setStartY(this.rectangle10.getHeight() / 2);
                this.lineAnswer.setEndX(this.rectangle12.getArcWidth() / 2);
                this.lineAnswer.setEndY(this.rectangle12.getHeight() / 2);
            }
            case 3 -> {
                // answer in third row
                this.lineAnswer.setStartX(this.rectangle20.getArcWidth() / 2);
                this.lineAnswer.setStartY(this.rectangle20.getHeight() / 2);
                this.lineAnswer.setEndX(this.rectangle22.getArcWidth() / 2);
                this.lineAnswer.setEndY(this.rectangle22.getHeight() / 2);
            }
            case 4 -> {
                // answer in first columns
                this.lineAnswer.setStartX(this.rectangle00.getArcWidth() / 2);
                this.lineAnswer.setStartY(this.rectangle00.getHeight() / 2);
                this.lineAnswer.setEndX(this.rectangle20.getArcWidth() / 2);
                this.lineAnswer.setEndY(this.rectangle20.getHeight() / 2);
            }
            case 5 -> {
                // answer in first row
                this.lineAnswer.setStartX(this.rectangle01.getArcWidth() / 2);
                this.lineAnswer.setStartY(this.rectangle01.getHeight() / 2);
                this.lineAnswer.setEndX(this.rectangle21.getArcWidth() / 2);
                this.lineAnswer.setEndY(this.rectangle21.getHeight() / 2);
            }
            case 6 -> {
                // answer in first row
                this.lineAnswer.setStartX(this.rectangle02.getArcWidth() / 2);
                this.lineAnswer.setStartY(this.rectangle02.getHeight() / 2);
                this.lineAnswer.setEndX(this.rectangle22.getArcWidth() / 2);
                this.lineAnswer.setEndY(this.rectangle22.getHeight() / 2);
            }
            case 7 -> {
                // answer in right diagonal
                this.lineAnswer.setStartX(this.rectangle00.getArcWidth() / 2);
                this.lineAnswer.setStartY(this.rectangle00.getHeight() / 2);
                this.lineAnswer.setEndX(this.rectangle22.getArcWidth() / 2);
                this.lineAnswer.setEndY(this.rectangle22.getHeight() / 2);
            }
            case 8 -> {
                // answer in left diagonal
                this.lineAnswer.setStartX(this.rectangle02.getArcWidth() / 2);
                this.lineAnswer.setStartY(this.rectangle02.getHeight() / 2);
                this.lineAnswer.setEndX(this.rectangle20.getArcWidth() / 2);
                this.lineAnswer.setEndY(this.rectangle20.getHeight() / 2);
            }
        }
        this.anchorPane.getChildren().add(lineAnswer);
    }

    private byte isWin(char type) {

        // 1-3: represent rows
        // 4-6: represent columns
        // 7: right diagonal
        // 8: left diagonal


        // check rows
        for (int i = 0; i < 3; ++i) {
            if (type == 'x') {
                if (this.matrix[i][0] == 'x' && this.matrix[i][1] == 'x' && this.matrix[i][2] == 'x')
                    return (byte) (i + 1);
            } else {
                if (this.matrix[i][0] == 'o' && this.matrix[i][1] == 'o' && this.matrix[i][2] == 'o')
                    return (byte) (i + 1);
            }
        }

        // check columns
        for (int j = 0; j < 3; ++j) {
            if (type == 'x') {
                if (this.matrix[0][j] == 'x' && this.matrix[1][j] == 'x' && this.matrix[2][j] == 'x')
                    return (byte) (j + 4);
            } else {
                if (this.matrix[0][j] == 'o' && this.matrix[1][j] == 'o' && this.matrix[2][j] == 'o')
                    return (byte) (j + 4);
            }
        }

        // right diagonal
        if (type == 'x') {
            if (this.matrix[0][0] == 'x' && this.matrix[1][1] == 'x' && this.matrix[2][2] == 'x') return 7;
        } else {
            if (this.matrix[0][0] == 'o' && this.matrix[1][1] == 'o' && this.matrix[2][2] == 'o') return 7;
        }

        // left diagonal
        if (type == 'x') {
            if (this.matrix[0][2] == 'x' && this.matrix[1][1] == 'x' && this.matrix[2][0] == 'x') return 8;
        } else {
            if (this.matrix[0][2] == 'o' && this.matrix[1][1] == 'o' && this.matrix[2][0] == 'o') return 8;
        }

        return '\0';
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
