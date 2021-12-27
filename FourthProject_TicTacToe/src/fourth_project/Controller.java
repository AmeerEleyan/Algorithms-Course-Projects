/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 27/12/2021    4:11 AM
 */
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
import java.util.*;


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

    // TicTacToe board (X, O, -)
    private char[][] board;
    private byte numberOfMovements;
    private char player, opponent;
    private boolean isFinish, whoseMove;
    private String scenario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboScenario.getItems().addAll("Easy Level", "With Your Friend", "Hard Level");
        this.comboType.getItems().addAll("X", "O");
        this.board = new char[][]{{'-', '-', '-'}, {'-', '-', '-'}, {'-', '-', '-'}};
        this.movements = FXCollections.observableArrayList(); // all movements in the path;
        this.numberOfMovements = 0;
        this.whoseMove = true;

    }

    // Handle restart button, to return the game to default status
    public void handleRestartButton() {
        this.reset();
    }

    // To exit from the game
    public void handleExitButton() {
        System.exit(0);
    }

    // Restart game
    private void reset() {
        this.whoseMove = true;
        this.isFinish = false;
        this.numberOfMovements = 0;
        this.comboScenario.setDisable(false);
        this.comboType.setDisable(false);
        this.txtResult.setVisible(false);
        this.board = new char[][]{{'-', '-', '-'}, {'-', '-', '-'}, {'-', '-', '-'}};
        this.anchorPane.getChildren().removeAll(this.movements);
        this.resetRectangleColor();
        this.movements = FXCollections.observableArrayList(); // all movements in the path;
    }

    // Handle when user select the rectangle to put his answer
    @FXML
    void handleMouseEvent(MouseEvent event) {

        // Check if the game is finish
        if (this.isFinish) {
            return;
        }

        // Get the node that selected by user
        Node node = event.getPickResult().getIntersectedNode();

        // check the type of clicked node
        if (!(node instanceof Rectangle)) {
            return;
        }

        if (this.numberOfMovements == 0) {

            // Check the scenario type if selected
            if (this.comboScenario.getValue() == null) {
                Message.displayMessage("Warning", "Plese select the scenario");
                return;
            }
            // Check the player type if selected
            if (this.comboType.getValue() == null) {
                Message.displayMessage("Warning", "Please select your type");
                return;
            }

            // determine the player and the opponent
            if (this.comboType.getValue().equals("X")) {
                this.player = 'x';
                this.opponent = 'o';
            } else {
                this.player = 'o';
                this.opponent = 'x';
            }

            // Hide comboBox senario and player type after first move
            this.comboScenario.setDisable(true);
            this.comboType.setDisable(true);
            this.scenario = this.comboScenario.getValue();
        }

        // Check if LeftMouseButton Clicked
        if (event.getButton() == MouseButton.PRIMARY) {
            // check senario type
            if (this.scenario.equals("Easy Level")) {

                this.easySenario(node);

            } else if (this.scenario.equals("With Your Friend")) {

                if (this.whoseMove) {
                    this.twoPlayerSenario(node, this.player, true);
                    this.whoseMove = false;
                } else {
                    this.twoPlayerSenario(node, this.opponent, false);
                    this.whoseMove = true;
                }

            } else {
                this.hardSenario(node);
            }
        }

    }

    // Process the easy senario with random move by computer
    private void easySenario(Node node) {

        // check if the player(user) can do this move
        if (!this.isLegalMove(node, this.player)) {
            return;
        } else this.numberOfMovements++;

        // check if the game was finished by the user
        if (this.isGameFinished_ByUser()) {
            return;
        }

        // opponent(computer) random move
        this.random_AI();

        // check if the game was finished by the opponent(computer) after the second move
        if (this.numberOfMovements > 2) {
            this.checkIfTheOpponentIsWinner();
        }
    }

    // Process the two player senario
    private void twoPlayerSenario(Node node, char whichPlayer, boolean whoseMove) {

        // check if the player or his friend can do this move
        if (this.isLegalMove(node, whichPlayer)) {
            // whoseMove: true: you
            // whoseMove: false :opponent(your friend)
            if (whoseMove) {
                this.numberOfMovements++;
                // check if the game was finished by the user
                this.isGameFinished_ByUser();
            } else {
                // check if the game was finished by the opponent(computer) after the second move
                if (this.numberOfMovements > 2) {
                    this.checkIfTheOpponentIsWinner();
                }
            }
        }
    }

    // Process the advanced scenario with the intelligent move by the computer
    private void hardSenario(Node node) {
        // check if the player(user) can do this move
        if (!this.isLegalMove(node, this.player)) {
            return;
        } else {
            this.numberOfMovements++;
        }

        // Find the best move
        int[] bestMove = this.findBestMove(this.board);

        // There are no moves( the game finished with draw)
        if (bestMove == null) {

            this.txtResult.setVisible(true);
            this.txtResult.setText("Draw");
            this.isFinish = true;

        } else {
            // set the best move for the opponent (computer), and fill it type in the cell
            this.board[bestMove[0]][bestMove[1]] = this.opponent;
            Node n = this.wichRectangleNode(bestMove[0], bestMove[1]);
            Node temp;
            if (this.opponent == 'x') {
                temp = this.drawX(n.getLayoutX(), n.getLayoutY());
            } else {
                temp = this.drawO(n.getLayoutX(), n.getLayoutY());
            }
            this.movements.add(temp);
            this.anchorPane.getChildren().add(temp);

            // check if the computer win after this move
            this.checkIfTheOpponentIsWinner();
        }

    }


    // This will return the best possible
    // move for the player
    private int[] findBestMove(char[][] board) {

        // Game finished
        if (this.numberOfMovements == 5) return null;

        int bestVal = Integer.MIN_VALUE;

        int row = -1, column = -1;

        // Traverse all cells, evaluate minimax function
        // for all empty cells. And return the cell with optimal value
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if cell is empty
                if (board[i][j] == '-') {
                    // Make the move
                    board[i][j] = this.opponent;

                    // compute evaluation function for this move.
                    int moveValue = minimax(board, 0, false);

                    // Undo the move
                    board[i][j] = '-';

                    // If the value of the current move is
                    // more than the best value, then update best
                    if (moveValue > bestVal) {
                        row = i;
                        column = j;
                        bestVal = moveValue;
                    }
                }
            }
        }
        // return the index of the best move in the board
        return new int[]{row, column};
    }


    // All the possible ways the game can go and returns
    // the best value of the board
    private int minimax(char[][] board, int depth, boolean isMax) {

        int score = checkIfThereIsAWinner(board);

        // If Maximizer has won the game
        // return his/her evaluated score
        if (score == 1)
            return score;

        // If Minimizer has won the game
        // return his/her evaluated score
        if (score == -1)
            return score;

        // If there are no more moves and
        // no winner then it is a tie
        if (!isMovesLeft(board))
            return 0;

        // If this maximizer's move
        int best;
        if (isMax) {
            best = Integer.MIN_VALUE;

            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (board[i][j] == '-') {
                        // Make the move
                        board[i][j] = this.opponent;

                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max(best, minimax(board, depth + 1, false));

                        // Undo the move
                        board[i][j] = '-';
                    }
                }
            }
        }
        // If this minimizer's move
        else {
            best = Integer.MAX_VALUE;

            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (board[i][j] == '-') {
                        // Make the move
                        board[i][j] = this.player;

                        // Call minimax recursively and choose
                        // the minimum value
                        best = Math.min(best, minimax(board, depth + 1, true));

                        // Undo the move
                        board[i][j] = '-';
                    }
                }
            }
        }
        return best;
    }

    // Check if there is a winner in the board
    private int checkIfThereIsAWinner(char[][] b) {
        // Checking for Rows for X or O .
        for (int row = 0; row < 3; row++) {
            if (b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
                if (b[row][0] == this.opponent)
                    return +1;
                else if (b[row][0] == this.player)
                    return -1;
            }
        }

        // Checking for Columns for X or O.
        for (int col = 0; col < 3; col++) {
            if (b[0][col] == b[1][col] && b[1][col] == b[2][col]) {
                if (b[0][col] == this.opponent)
                    return +1;

                else if (b[0][col] == this.player)
                    return -1;
            }
        }

        // Checking for right diagonals for X or O victory.
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == this.opponent)
                return +1;
            else if (b[0][0] == this.player)
                return -1;
        }

        // Checking for left diagonals for X or O victory.
        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == this.opponent)
                return +1;
            else if (b[0][2] == this.player)
                return -1;
        }

        // Else if none of them have won then return 0
        return 0;
    }


    // Check the status of the game that was finished by user(draw or win)
    private boolean isGameFinished_ByUser() {

        if (this.numberOfMovements > 2 && this.numberOfMovements <= 5) {
            byte win = this.isGameFinished_ByUser(this.player);

            if (win == '\0' && this.numberOfMovements == 5) {
                this.txtResult.setVisible(true);
                this.txtResult.setText("Draw");
                this.isFinish = true;
                return true;
            } else if (win != '\0') {
                this.txtResult.setVisible(true);
                this.displayAnswerLine(win);
                this.txtResult.setText("Congratulations. You Win");
                this.isFinish = true;
                return true;
            }
        }
        return false;
    }

    private byte isGameFinished_ByUser(char type) {

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

        // No Winner
        return '\0';
    }

    // Random move in the board
    private void random_AI() {

        Random random = new Random();
        int row = random.nextInt(0, 3);
        int columns = random.nextInt(0, 3);

        // Get empty cell randomly
        while (this.board[row][columns] != '-') {
            row = random.nextInt(0, 3);
            columns = random.nextInt(0, 3);
        }

        // get the node that was obtained it randomly, and draw on it
        Node n = this.wichRectangleNode(row, columns);
        Node nodeType;
        if (this.opponent == 'x') {
            nodeType = this.drawX(n.getLayoutX(), n.getLayoutY());
        } else {
            nodeType = this.drawO(n.getLayoutX(), n.getLayoutY());
        }

        this.movements.add(nodeType);
        this.anchorPane.getChildren().add(nodeType);

        // set the random opponent move in the board
        this.board[row][columns] = this.opponent;

    }


    // Check wich cell selected ,
    // and fill it if empty with player type
    private boolean isLegalMove(Node node, char whichPlayer) {

        // Check the cell ID
        if (node.getId().equals("rectangle00")) {

            if (this.board[0][0] == '-') this.board[0][0] = whichPlayer;
            else return false;// the cell was filled

        } else if (node.getId().equals("rectangle01")) {

            if (this.board[0][1] == '-') this.board[0][1] = whichPlayer;
            else return false;

        } else if (node.getId().equals("rectangle02")) {

            if (this.board[0][2] == '-') this.board[0][2] = whichPlayer;
            else return false;

        } else if (node.getId().equals("rectangle10")) {

            if (this.board[1][0] == '-') this.board[1][0] = whichPlayer;
            else return false;

        } else if (node.getId().equals("rectangle11")) {

            if (this.board[1][1] == '-') this.board[1][1] = whichPlayer;
            else return false;

        } else if (node.getId().equals("rectangle12")) {

            if (this.board[1][2] == '-') this.board[1][2] = whichPlayer;
            else return false;

        } else if (node.getId().equals("rectangle20")) {

            if (this.board[2][0] == '-') this.board[2][0] = whichPlayer;
            else return false;

        } else if (node.getId().equals("rectangle21")) {

            if (this.board[2][1] == '-') this.board[2][1] = whichPlayer;
            else return false;

        } else {

            if (this.board[2][2] == '-') this.board[2][2] = whichPlayer;
            else return false;

        }
        // Draw the player in the selected cell
        Node n;
        if (whichPlayer == 'x') {
            n = this.drawX(node.getLayoutX(), node.getLayoutY());
        } else {
            n = this.drawO(node.getLayoutX(), node.getLayoutY());
        }
        // add this move to the list, to be able to remove it when restarting
        this.movements.add(n);

        this.anchorPane.getChildren().add(n);
        // the move is legal
        return true;
    }

    // Check if the board has empty cell
    boolean isMovesLeft(char[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '-')
                    return true;
        return false;
    }

    // Check if the player lost in the game
    private void checkIfTheOpponentIsWinner() {
        byte win = this.isGameFinished_ByUser(this.opponent);
        if (win != '\0') {
            this.txtResult.setVisible(true);
            this.displayAnswerLine(win);
            this.txtResult.setText(Character.toUpperCase(this.player) + " is the loser!!");
            this.isFinish = true;
        }
    }

    // Get the rectangle node by it index
    private Node wichRectangleNode(int row, int columns) {
        if (row == 0 && columns == 0) {
            return this.rectangle00;
        } else if (row == 0 && columns == 1) {
            return this.rectangle01;
        } else if (row == 0 && columns == 2) {
            return this.rectangle02;
        } else if (row == 1 && columns == 0) {
            return this.rectangle10;
        } else if (row == 1 && columns == 1) {
            return this.rectangle11;
        } else if (row == 1 && columns == 2) {
            return this.rectangle12;
        } else if (row == 2 && columns == 0) {
            return this.rectangle20;
        } else if (row == 2 && columns == 1) {
            return this.rectangle21;
        } else {
            return this.rectangle22;
        }
    }

    // ReSet rectangle color to black when restart the game
    private void resetRectangleColor() {
        this.rectangle00.setFill(Color.BLACK);
        this.rectangle01.setFill(Color.BLACK);
        this.rectangle02.setFill(Color.BLACK);
        this.rectangle10.setFill(Color.BLACK);
        this.rectangle11.setFill(Color.BLACK);
        this.rectangle12.setFill(Color.BLACK);
        this.rectangle20.setFill(Color.BLACK);
        this.rectangle21.setFill(Color.BLACK);
        this.rectangle22.setFill(Color.BLACK);
    }

    // Fill the cell that has the answer line by red color
    private void displayAnswerLine(byte isWin) {

        switch (isWin) {
            case 1 -> {
                this.rectangle00.setFill(Color.RED);
                this.rectangle01.setFill(Color.RED);
                this.rectangle02.setFill(Color.RED);
            }
            case 2 -> {
                this.rectangle10.setFill(Color.RED);
                this.rectangle11.setFill(Color.RED);
                this.rectangle12.setFill(Color.RED);
            }
            case 3 -> {
                this.rectangle20.setFill(Color.RED);
                this.rectangle21.setFill(Color.RED);
                this.rectangle22.setFill(Color.RED);
            }
            case 4 -> {
                this.rectangle00.setFill(Color.RED);
                this.rectangle10.setFill(Color.RED);
                this.rectangle20.setFill(Color.RED);
            }
            case 5 -> {
                this.rectangle01.setFill(Color.RED);
                this.rectangle11.setFill(Color.RED);
                this.rectangle21.setFill(Color.RED);
            }
            case 6 -> {
                this.rectangle02.setFill(Color.RED);
                this.rectangle12.setFill(Color.RED);
                this.rectangle22.setFill(Color.RED);
            }
            case 7 -> {
                this.rectangle00.setFill(Color.RED);
                this.rectangle11.setFill(Color.RED);
                this.rectangle22.setFill(Color.RED);
            }
            case 8 -> {
                this.rectangle02.setFill(Color.RED);
                this.rectangle11.setFill(Color.RED);
                this.rectangle20.setFill(Color.RED);
            }
        }
    }

    // Get The O char as circle
    private Circle drawO(double layout_X, double layout_Y) {
        Circle circle = new Circle(70);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(6);
        circle.setLayoutX(layout_X + 140);
        circle.setLayoutY(layout_Y + 165);
        return circle;
    }

    // Get the X char as a group of two line
    private Group drawX(double layout_X, double layout_Y) {
        Line line = new Line();

        line.setRotate(45);
        line.setStrokeWidth(6);

        line.setStartX(-100);
        line.setStartY(0);
        line.setEndX(100);
        line.setEndY(0);

        line.setStroke(Color.GREENYELLOW);
        line.setLayoutX(layout_X + 140);
        line.setLayoutY(layout_Y + 165);

        Line line2 = new Line();

        line2.setRotate(-45);
        line2.setStrokeWidth(6);

        line2.setStartX(-100);
        line2.setStartY(0);
        line2.setEndX(100);
        line2.setEndY(0);

        line2.setStroke(Color.GREENYELLOW);
        line2.setLayoutX(layout_X + 140);
        line2.setLayoutY(layout_Y + 165);

        Group group = new Group();
        group.getChildren().addAll(line, line2);
        return group;
    }

}
