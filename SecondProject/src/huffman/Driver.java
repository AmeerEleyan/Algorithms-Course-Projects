/**
 * @author: Ameer Eleyan
 * ID: 1191076
 * At: 10/30/2021   2:00 AM
 */
package huffman;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Driver implements Initializable {

    @FXML // fx:id="btBrowse"
    private Button btBrowse; // Value injected by FXMLLoader

    @FXML // fx:id="btTAnotherFile"
    private Button btAnotherFile; // Value injected by FXMLLoader

    @FXML // fx:id="cmASCII"
    private TableColumn<StatisticsTable, Byte> cmASCII; // Value injected by FXMLLoader

    @FXML // fx:id="cmFrequency"
    private TableColumn<StatisticsTable, Integer> cmFrequency; // Value injected by FXMLLoader

    @FXML // fx:id="cmHuffman"
    private TableColumn<StatisticsTable, String> cmHuffman; // Value injected by FXMLLoader

    @FXML // fx:id="cmLength"
    private TableColumn<StatisticsTable, Integer> cmLength; // Value injected by FXMLLoader

    @FXML // fx:id="comboBox"
    private ComboBox<String> comboBox; // Value injected by FXMLLoader

    @FXML // fx:id="tableView"
    private TableView<StatisticsTable> tableView; // Value injected by FXMLLoader

    @FXML // fx:id="txtHeader"
    private TextArea txtHeader; // Value injected by FXMLLoader

    @FXML // fx:id="lblHeader"
    private Label lblHeader; // Value injected by FXMLLoader

    @FXML // fx:id="lblStatistics"
    private Label lblStatistics; // Value injected by FXMLLoader

    private HuffmanCompress header;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboBox.getItems().add("Compress File");
        this.comboBox.getItems().add("Decompress File");
        this.comboBox.setDisable(false);
        this.btBrowse.setDisable(true);
        this.btAnotherFile.setDisable(true);
        this.lblHeader.setDisable(true);
        this.lblStatistics.setDisable(true);
        this.tableView.setDisable(true);
        this.txtHeader.setDisable(true);
        this.cmASCII.setCellValueFactory(new PropertyValueFactory<>("ASCII"));
        this.cmFrequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        this.cmHuffman.setCellValueFactory(new PropertyValueFactory<>("huffmanCode"));
        this.cmLength.setCellValueFactory(new PropertyValueFactory<>("huffmanLength"));
    }

    public void handleComboBox() {
        this.btBrowse.setDisable(false);
    }

    public void handleBrowse() {

        // File Chooser
        FileChooser fileChooser = new FileChooser();
        if (this.comboBox.getValue().equals("Decompress File")) {
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Files", "*.huf"); // Specifying the extension of the files
            fileChooser.getExtensionFilters().add(extFilter); // Appends the specified element to the end of this list
        }
        File sourceFile = fileChooser.showOpenDialog(GUI.window.getScene().getWindow()); // Browsing one file as .txt

        if (sourceFile != null) { // To check if the user select the file or close the window without selecting
            if (sourceFile.length() == 0) {
                Message.displayMessage("Warning", "There are no data in the " + sourceFile.getName());
            } else {
                if (this.comboBox.getValue().equals("Compress File")) {
                    this.header = new HuffmanCompress();
                    this.header.compress(sourceFile);
                    this.lblHeader.setDisable(false);
                    this.lblStatistics.setDisable(false);
                    this.tableView.setDisable(false);
                    this.txtHeader.setDisable(false);
                    this.txtHeader.setText(this.header.getFullHeaderAsString());
                    this.fillStatisticsTable();
                    Message.displayMessage("Successfully", sourceFile.getName() + " was compress successfully");
                } else {
                    HuffmanDecompress.decompress(sourceFile);
                    Message.displayMessage("Successfully", sourceFile.getName() + " was decompress successfully");
                }
                this.comboBox.setDisable(true);
                this.btAnotherFile.setDisable(false);
            }

        }
    }


    public void handleAnotherFile() {
        this.returnControlsDefault();
    }

    private void returnControlsDefault() {
        this.comboBox.setDisable(false);
        this.btBrowse.setDisable(false);
        this.btAnotherFile.setDisable(true);
        this.txtHeader.clear();
        this.tableView.getItems().clear();
        this.header.returnDefault();
        this.tableView.setDisable(true);
        this.txtHeader.setDisable(true);
        this.lblHeader.setDisable(true);
        this.lblStatistics.setDisable(true);

    }

    private void fillStatisticsTable() {
        for (int i = 0; i < this.header.getHuffmanTable().length; i++) {
            if (this.header.getHuffmanTable()[i] != null)
                this.tableView.getItems().add(this.header.getHuffmanTable()[i]);
        }
    }

}
