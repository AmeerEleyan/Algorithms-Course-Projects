/**
 * @author: Ameer Eleyan
 * ID: 1191076
 */
package lcs;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Driver implements Initializable {

    public int[] LEDs;
    private int size = 0;
    private int index = 0;

    @FXML // fx:id="HBoxAdd"
    private HBox HBoxAdd; // Value injected by FXMLLoader

    @FXML // fx:id="HBoxSet"
    private HBox HBoxSet; // Value injected by FXMLLoader

    @FXML // fx:id="btAdd"
    private Button btAdd; // Value injected by FXMLLoader

    @FXML // fx:id="btTryAnotherData"
    private Button btTryAnotherData; // Value injected by FXMLLoader

    @FXML // fx:id="btSet"
    private Button btSet; // Value injected by FXMLLoader

    @FXML // fx:id="btUploadData"
    private Button btUploadData; // Value injected by FXMLLoader

    @FXML // fx:id="btFind"
    private Button btFind; // Value injected by FXMLLoader

    @FXML // fx:id="comboBox"
    private ComboBox<String> comboBox; // Value injected by FXMLLoader

    @FXML // fx:id="lblData"
    private Label lblLedNo; // Value injected by FXMLLoader

    @FXML // fx:id="lblDataSize"
    private Label lblNoOfLed; // Value injected by FXMLLoader

    @FXML // fx:id="textAreaForData"
    private TextArea textAreaForData; // Value injected by FXMLLoader

    @FXML // fx:id="textAreaDetails"
    private TextArea textAreaDetails; // Value injected by FXMLLoader

    @FXML // fx:id="textAreaForResult"
    private TextArea textAreaForResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtData"
    private TextField txtData; // Value injected by FXMLLoader

    @FXML // fx:id="txtDataSize"
    private TextField txtDataSize; // Value injected by FXMLLoader

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboBox.getItems().add("Manual");
        this.comboBox.getItems().add("Using File");
        this.returnControlsDefault();
    }

    public void handleComboBox() {
        if (comboBox.getValue().equals("Manual")) {
            this.HBoxSet.setDisable(false);
            this.btUploadData.setDisable(true);
        } else {
            this.btUploadData.setDisable(false);
            this.HBoxSet.setDisable(true);
            this.HBoxAdd.setDisable(true);
        }
    }

    public void handleBtSet() {
        if (this.txtDataSize.getText().trim().isEmpty()) { // get the data size
            Message.displayMessage("Warning", "Please Enter the data size");
            this.txtDataSize.clear();
        } else if (isNumber(this.txtDataSize.getText().trim())) {
            // Creating matrix with a size that was entered
            this.size = Integer.parseInt(this.txtDataSize.getText().trim());
            this.LEDs = new int[this.size];
            this.textAreaForData.setText("Data size is: " + this.size + ", and they are:\n");
            this.HBoxSet.setDisable(true);
            this.comboBox.setDisable(true);
            this.HBoxAdd.setDisable(false);
        } else {
            Message.displayMessage("Warning", "Please enter a valid size");
            this.txtDataSize.clear();
        }
    }

    public void handleBtAdd() {
        if (this.txtData.getText().trim().isEmpty()) { // get the data
            Message.displayMessage("Warning", "Please Enter the data");
            this.txtData.clear();
            return;
        } else if (isNumber(this.txtData.getText().trim())) {
            if (Integer.parseInt(this.txtData.getText().trim()) <= this.size) {
                this.LEDs[this.index] = Integer.parseInt(this.txtData.getText().trim());
                this.textAreaForData.appendText(" \nLed # " + this.LEDs[this.index] + "\t\tPower- " + (this.index + 1) + "\n");
                this.index++;
            } else
                Message.displayMessage("Warning", "The value must be less than or equal" + this.size);

        } else {
            Message.displayMessage("Warning", "Please enter a valid data");
        }

        this.txtData.clear(); // te receive another value

        if (this.index == this.size) {
            this.HBoxAdd.setDisable(true);
            this.btFind.setDisable(false);
        }
    }

    public void handleUploadData() {
        this.uploadFiles();
    }

    public void handleFind() {
        longestCommonSubsequence();
        this.btTryAnotherData.setDisable(false);
        this.btFind.setDisable(true);
    }

    public void handleBtTryAnotherData() {
        this.returnControlsDefault();
    }

    // upload files using a browser
    private void uploadFiles() {

        // File Chooser
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Files", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(GUI.window.getScene().getWindow());

        if (file != null) {
            readDataFromFile(file);
        }
    }

    /**
     * Methode to read data from file iteratively
     */
    private void readDataFromFile(File fileName) {
        try {
            Scanner input = new Scanner(fileName); // instance of scanner for read data from file
            if (fileName.length() == 0) {
                // no data in file
                Message.displayMessage("Warning", "  There are no data in the file " + fileName + " ");
                this.comboBox.setDisable(false);
            } else {
                boolean temp = true; // represent line on the file to display in which line has problem If that happens
                while (input.hasNext()) {

                    if (input.hasNextInt()) {
                        if (temp) {
                            this.size = input.nextInt();
                            this.LEDs = new int[this.size];
                            this.textAreaForData.setText("Data size is: " + this.size + ", and they are:\n");
                            temp = false;
                        } else {
                            int data = input.nextInt();
                            if (data <= this.size) {
                                this.LEDs[this.index] = data;
                                this.textAreaForData.appendText(" \nLed #" + this.LEDs[this.index] + "\t\tPower-" + (this.index + 1) + "\n");
                                this.index++;
                            } else {
                                Message.displayMessage("Warning", data + " is invalid");
                            }

                        }
                    } else {
                        input.next(); // To ignore anything not an integer
                    }
                }
                input.close(); // prevent(close) scanner to read data
                this.btFind.setDisable(false);
                this.comboBox.setDisable(true);
                this.btUploadData.setDisable(true);
            }
        } catch (FileNotFoundException e) {
            // The specific file for reading data does not exist
            Message.displayMessage("Error", " The system can NOT find the file " + fileName + "  ");
        }
    }

    /**
     * To check the value of the entered numberOfShares if contain only digits or not
     */
    public static boolean isNumber(String number) {
        /* To check the entered number of shares, that it consists of
           only digits
         */
        try {
            int temp = Integer.parseInt(number);
            return number.matches("\\d+") && temp > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void returnControlsDefault() {
        this.comboBox.setDisable(false);
        this.HBoxSet.setDisable(true);
        this.HBoxAdd.setDisable(true);
        this.btUploadData.setDisable(true);
        this.btFind.setDisable(true);
        this.btTryAnotherData.setDisable(true);
        this.size = 0;
        this.index = 0;
        this.textAreaDetails.clear();
        this.textAreaForData.clear();
        this.textAreaForResult.clear();
        this.btTryAnotherData.setDisable(true);
    }

    public void longestCommonSubsequence() {

        int[][] costForLCS = new int[this.size + 1][this.size + 1];
        byte[][] tempArrayForDisplayResult = new byte[this.size + 1][this.size + 1];

        for (int i = 1; i <= this.size; i++) {
            for (int j = 1; j <= this.size; j++) {
                if (i == this.LEDs[j - 1]) {
                    costForLCS[i][j] = costForLCS[i - 1][j - 1] + 1;
                    tempArrayForDisplayResult[i][j] = 1;
                } else {
                    if (costForLCS[i][j - 1] > costForLCS[i - 1][j]) {
                        costForLCS[i][j] = costForLCS[i][j - 1];
                        tempArrayForDisplayResult[i][j] = 0;
                    } else {
                        costForLCS[i][j] = costForLCS[i - 1][j];
                        tempArrayForDisplayResult[i][j] = 2;
                    }
                }
            }
        }
        // view details of the results
        this.textAreaDetails.appendText("\t");
        for (int i = 0; i < this.size + 1; i++) {
            for (int j = 0; j < this.size + 1; j++) {
                if (i == 0 && j < this.size)
                    textAreaDetails.appendText("\t\t" + "Led-" + this.LEDs[j]);//this.size => Led7 print
                else if (j == 0) textAreaDetails.appendText("Power-" + i + "\t\t");
                if ((j > 0) && (i > 0))
                    textAreaDetails.appendText(" " + costForLCS[i][j] + " \t  \t  \t"); // first row and first column that was zero will be skipped
            }
            this.textAreaDetails.appendText("\n");
        }
        displaySolution(tempArrayForDisplayResult, this.size, this.size, costForLCS);

    }

    public void displaySolution(byte[][] b, int i, int j, int[][] costForLCS) {

        int row = i; // to looping for all row have the LCS number
        int column = j;// to looping for all column have the LCS number

        String[] allLCS = new String[i]; // To store all unique LCS pairs
        int Index_For_All_LCS_Matrix = 0;

        // To visit through column rows that contain LCS number
        while (costForLCS[row][column] == costForLCS[i][j]) { // To check if the current number in the costForLCS matrix equal the LCS length

            // To visit through column that contain LCS number
            while (costForLCS[row][column] == costForLCS[i][j]) { // To check if the current number in the costForLCS matrix equal the LCS length

                int tempRow = row;
                int tempColumn = column;
                /* We used the above two variable to visit the paths,
                but the initial value for them start from
                the current row and column, so will be changed
                in every row and column */

                String pairs = ""; // To store the LCS LEDs

                while (tempRow != 0 && tempColumn != 0) { // To visit the path that represent LCS pairs
                    if (b[tempRow][tempColumn] == 1) {
                        pairs = "{Power-" + tempRow + ", Led-" + tempRow + "}\n" + pairs;
                        tempRow--; // to move the previous row
                        tempColumn--; // to move the previous column
                    } else if (b[tempRow][tempColumn] == 0) tempColumn--;
                    else tempRow--;
                }

                if (column == j) { // first solution
                    allLCS[Index_For_All_LCS_Matrix] = pairs;
                }

                // other solutions but unique
                boolean notFound = true;

                // To check if current LCS is already exist
                for (int p = 0; p < Index_For_All_LCS_Matrix; p++) {
                    if (pairs.equals(allLCS[p])) {
                        notFound = false; // the solution does exist
                        break;
                    }
                }

                if (notFound) { // new solution not found(first appearance)
                    this.textAreaForResult.appendText("The best pairs to be connected are:\n");
                    this.textAreaForResult.appendText(pairs);
                    this.textAreaForResult.appendText("\nThus " + costForLCS[row][column] + " LEDs will be lighted");
                    this.textAreaForResult.appendText("\n\n----------------------OR----------------------\n\n");
                    allLCS[Index_For_All_LCS_Matrix++] = pairs; // To store the unique current LCS in it's matrix
                }

                column--;
            }
            row--;
            column = j; // To start from last column in the new row(previous row of the current row)
        }

    }

}
