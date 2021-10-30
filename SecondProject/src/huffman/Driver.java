package huffman;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Driver implements Initializable {

    @FXML // fx:id="btBrowse"
    private Button btBrowse; // Value injected by FXMLLoader

    @FXML // fx:id="btSave"
    private Button btSave; // Value injected by FXMLLoader

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboBox.getItems().add("Compress File");
        this.comboBox.getItems().add("Decompress File");
        this.returnControlsDefault();
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
        File file = fileChooser.showOpenDialog(GUI.window.getScene().getWindow()); // Browsing one file as .txt

        if (file != null) { // To check if the user select the file or close the window without selecting
            if (file.length() == 0) {
                Message.displayMessage("Warning", "There are no data in the " + file.getName());
            }else{
                Huffman.buildHuffmanTree(file);
                this.comboBox.setDisable(true);
                this.btSave.setDisable(false);
                this.btAnotherFile.setDisable(false);
            }

        }
    }

    public void handleSave() {
        // File Chooser
        FileChooser fileChooser = new FileChooser(); //File to upload
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Files", "*.huf"); // Specifying the extension of the files
        fileChooser.getExtensionFilters().add(extFilter); // Appends the specified element to the end of this list
        File file = fileChooser.showSaveDialog(GUI.window.getScene().getWindow()); // Browsing one file as .txt

        if (file != null) { // To check if the user select the file or close the window without selecting
            //  this.readDataFromFile(file);
            this.btBrowse.setDisable(true);
            this.btSave.setDisable(true);
        }
    }

    public void handleAnotherFile() {
        this.returnControlsDefault();
    }

    private void returnControlsDefault() {
        this.comboBox.setDisable(false);
        this.btBrowse.setDisable(true);
        this.btSave.setDisable(true);
        this.btAnotherFile.setDisable(true);
        this.txtHeader.clear();
        this.tableView.getItems().clear();

    }
}
