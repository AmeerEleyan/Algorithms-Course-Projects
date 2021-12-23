package fourth_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Random;
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
    private char[][] board;
    private byte numberOfMovements = 0;
    private char playerType;
    private boolean isFinish;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboScenario.getItems().addAll("Easy Level", "With Your Friend", "Hard Level");
        this.comboType.getItems().addAll("X", "O");
        this.board = new char[][]{{'-', '-', '-'}, {'-', '-', '-'}, {'-', '-', '-'}};
        this.movements = FXCollections.observableArrayList(); // all movements in the path;
    }


    public void handleRestartButton() {
        this.reset();
    }

    // Restart game
    private void reset() {
        this.isFinish = false;
        this.numberOfMovements = 0;
        this.comboScenario.setDisable(false);
        this.comboType.setDisable(false);
        this.txtResult.setVisible(false);
        this.board = new char[][]{{'-', '-', '-'}, {'-', '-', '-'}, {'-', '-', '-'}};
        this.anchorPane.getChildren().removeAll(this.movements);
        this.anchorPane.getChildren().remove(this.lineAnswer);
        this.lineAnswer = null;
        this.movements = FXCollections.observableArrayList(); // all movements in the path;
    }

    @FXML
    void handleMouseEvent(MouseEvent event) {

        // Check if the game is finish
        if (this.isFinish) {
            return;
        }

        Node node = event.getPickResult().getIntersectedNode();
        // the clicked node not a rectangle
        if (!(node instanceof Rectangle)) {
            return;
        }

        if (this.comboScenario.getValue() == null) {
            Message.displayMessage("Warning", "Plese select the scenario");
            return;
        }
        if (this.comboType.getValue() == null) {
            Message.displayMessage("Warning", "Please select your type");
            return;
        }

        if (this.numberOfMovements == 0) {
            this.comboScenario.setDisable(true);
            this.comboType.setDisable(true);
        }

        this.playerType = 'o';
        if (this.comboType.getValue().equals("X")) this.playerType = 'x';

        String scenario = this.comboScenario.getValue();

        if (event.getButton() == MouseButton.PRIMARY) {
            if (node.getId().equals("rectangle00")) {

                if (this.board[0][0] == '-') this.board[0][0] = this.playerType;
                else return;

            } else if (node.getId().equals("rectangle01")) {

                if (this.board[0][1] == '-') this.board[0][1] = this.playerType;
                else return;

            } else if (node.getId().equals("rectangle02")) {

                if (this.board[0][2] == '-') this.board[0][2] = this.playerType;
                else return;

            } else if (node.getId().equals("rectangle10")) {

                if (this.board[1][0] == '-') this.board[1][0] = this.playerType;
                else return;

            } else if (node.getId().equals("rectangle11")) {

                if (this.board[1][1] == '-') this.board[1][1] = this.playerType;
                else return;

            } else if (node.getId().equals("rectangle12")) {

                if (this.board[1][2] == '-') this.board[1][2] = this.playerType;
                else return;

            } else if (node.getId().equals("rectangle20")) {

                if (this.board[2][0] == '-') this.board[2][0] = this.playerType;
                else return;

            } else if (node.getId().equals("rectangle21")) {

                if (this.board[2][1] == '-') this.board[2][1] = this.playerType;
                else return;

            } else {

                if (this.board[2][2] == '-') this.board[2][2] = this.playerType;
                else return;

            }
            Node n;
            if (this.playerType == 'x') {
                n = this.drawX(node.getLayoutX(), node.getLayoutY());
            } else {
                n = this.drawO(node.getLayoutX(), node.getLayoutY());
            }
            this.movements.add(n);
            this.anchorPane.getChildren().add(n);

            this.numberOfMovements++;

            if (this.numberOfMovements > 2 && this.numberOfMovements <= 5) {
                byte win = this.isWin(this.playerType);

                if (win == '\0' && this.numberOfMovements == 5) {
                    this.txtResult.setVisible(true);
                    this.txtResult.setText("GameOver 😒😥");
                    this.isFinish = true;
                    return;
                } else if (win != '\0') {
                    this.txtResult.setVisible(true);
                    this.displayAnswerLine(win);
                    this.txtResult.setText("Congratulations!! 🤝 You Win 💪");
                    this.isFinish = true;
                    return;
                }
            }
            // check senario
            if (scenario.equals("Easy Level")) {
                this.easySenario();
            } else if (scenario.equals("With Your Friend")) {
                this.twoPlayerSenario();
            } else {
                this.hardSenario();
            }

        }

    }

    // Get a legal move on the board
    private int[] random_AI() {
        Random random = new Random();

        int row = random.nextInt(0, 3);
        int columns = random.nextInt(0, 3);

        while (this.board[row][columns] != '-') {
            row = random.nextInt(0, 3);
            columns = random.nextInt(0, 3);
        }

        return new int[]{row, columns};
    }

    private void easySenario() {

        int[] random_AI = this.random_AI();
        int row = random_AI[0];
        int columns = random_AI[1];

        Node n;
        if (row == 0 && columns == 0) {
            n = this.rectangle00;
        } else if (row == 0 && columns == 1) {
            n = this.rectangle01;
        } else if (row == 0 && columns == 2) {
            n = this.rectangle02;
        } else if (row == 1 && columns == 0) {
            n = this.rectangle10;
        } else if (row == 1 && columns == 1) {
            n = this.rectangle11;
        } else if (row == 1 && columns == 2) {
            n = this.rectangle12;
        } else if (row == 2 && columns == 0) {
            n = this.rectangle20;
        } else if (row == 2 && columns == 1) {
            n = this.rectangle21;
        } else {
            n = this.rectangle22;
        }
        Node nodeType;
        if (this.playerType == 'x') {
            nodeType = this.drawO(n.getLayoutX(), n.getLayoutY());
        } else {
            nodeType = this.drawX(n.getLayoutX(), n.getLayoutY());
        }
        this.movements.add(nodeType);
        this.anchorPane.getChildren().add(nodeType);

        //change matrix answer and check if wins

        if (this.playerType == 'x') {
            this.board[row][columns] = 'o';
        } else {
            this.board[row][columns] = 'x';
        }

        if (this.numberOfMovements > 2) {

            byte win;
            if (this.playerType == 'x') {
                win = this.isWin('o');
            } else {
                win = this.isWin('x');
            }
            if (win != '\0') {
                this.txtResult.setVisible(true);
                this.displayAnswerLine(win);
                this.txtResult.setText("GameOver 😒😥");
                this.isFinish = true;
            }
        }


    }

    private void twoPlayerSenario() {

    }

    private void hardSenario() {

    }


    private byte isWin(char type) {

        // 1-3: represent rows
        // 4-6: represent columns
        // 7: right diagonal
        // 8: left diagonal

        // check rows
        for (int i = 0; i < 3; ++i) {
            if (type == 'x') {
                if (this.board[i][0] == 'x' && this.board[i][1] == 'x' && this.board[i][2] == 'x')
                    return (byte) (i + 1);
            } else {
                if (this.board[i][0] == 'o' && this.board[i][1] == 'o' && this.board[i][2] == 'o')
                    return (byte) (i + 1);
            }
        }

        // check columns
        for (int j = 0; j < 3; ++j) {
            if (type == 'x') {
                if (this.board[0][j] == 'x' && this.board[1][j] == 'x' && this.board[2][j] == 'x')
                    return (byte) (j + 4);
            } else {
                if (this.board[0][j] == 'o' && this.board[1][j] == 'o' && this.board[2][j] == 'o')
                    return (byte) (j + 4);
            }
        }

        // right diagonal
        if (type == 'x') {
            if (this.board[0][0] == 'x' && this.board[1][1] == 'x' && this.board[2][2] == 'x') return 7;
        } else {
            if (this.board[0][0] == 'o' && this.board[1][1] == 'o' && this.board[2][2] == 'o') return 7;
        }

        // left diagonal
        if (type == 'x') {
            if (this.board[0][2] == 'x' && this.board[1][1] == 'x' && this.board[2][0] == 'x') return 8;
        } else {
            if (this.board[0][2] == 'o' && this.board[1][1] == 'o' && this.board[2][0] == 'o') return 8;
        }

        return '\0';
    }

    private void displayAnswerLine(byte isWin) {
        // find answer lineAnswer and draw it
        this.lineAnswer = this.drawAnswerLine(isWin);
        this.anchorPane.getChildren().add(lineAnswer);
    }

    private Line drawAnswerLine(byte isWin) {
        Line line = new Line();
        line.setStrokeWidth(5);
        line.setStroke(Color.RED);
        if (isWin < 4) {
            line.setStartX(-297);
            line.setStartY(0);
            line.setEndX(297);
            line.setEndY(0);
            if (isWin == 1) {
                line.setLayoutX(350);
                line.setLayoutY(170);
            } else if (isWin == 2) {
                line.setLayoutX(350);
                line.setLayoutY(340);
            } else {
                line.setLayoutX(350);
                line.setLayoutY(500);
            }
        } else if (isWin < 7) {
            line.setStartX(195);
            line.setStartY(0);
            line.setEndX(195);
            line.setEndY(480);
            if (isWin == 4) {
                line.setLayoutX(-43);
                line.setLayoutY(100);
            } else if (isWin == 5) {
                line.setLayoutX(154);
                line.setLayoutY(100);
            } else {
                line.setLayoutX(354);
                line.setLayoutY(100);
            }

        } else if (isWin == 7) {
            line.setStartX(310);
            line.setStartY(420);
            line.setEndX(-278);
            line.setEndY(-55);
            line.setLayoutX(335);
            line.setLayoutY(155);
        } else {
            line.setStartX(-278);
            line.setStartY(425);
            line.setEndX(305);
            line.setEndY(-55);
            line.setLayoutX(335);
            line.setLayoutY(155);
        }
        return line;
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
