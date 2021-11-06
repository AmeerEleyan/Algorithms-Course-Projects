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

    private File sourceFile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboBox.getItems().add("Compress File");
        this.comboBox.getItems().add("Decompress File");
        this.comboBox.setDisable(false);
        this.btBrowse.setDisable(true);
        this.btSave.setDisable(true);
        this.btAnotherFile.setDisable(true);
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
        this.sourceFile = fileChooser.showOpenDialog(GUI.window.getScene().getWindow()); // Browsing one file as .txt

        if (this.sourceFile != null) { // To check if the user select the file or close the window without selecting
            if (this.sourceFile.length() == 0) {
                Message.displayMessage("Warning", "There are no data in the " + this.sourceFile.getName());
            } else {
                if (this.comboBox.getValue().equals("Compress File")) {
                    Huffman.encoding(this.sourceFile);
                    Message.displayMessage("Successfully", this.sourceFile.getName() + " was encoding successfully, you can save it");
                } else {
                    Huffman.decoding(this.sourceFile);
                    Message.displayMessage("Successfully", this.sourceFile.getName() + " was decoding successfully, you can save it");
                }
                this.fillStatisticsTable();
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
        File destinationFile = fileChooser.showSaveDialog(GUI.window.getScene().getWindow()); // Browsing one file as .txt

        if (destinationFile != null) { // To check if the user select the file or close the window without selecting

            if (this.comboBox.getValue().equals("Compress File")) {
                Huffman.printToFile(this.sourceFile, destinationFile);
                // Huffman.compress(this.sourceFile, destinationFile);
                Message.displayMessage("Successfully", this.sourceFile.getName() + " was compressed successfully ");
            } else {
                Huffman.decompress(this.sourceFile, destinationFile);
                Message.displayMessage("Successfully", this.sourceFile.getName() + " was decompressed successfully ");
            }
            this.btBrowse.setDisable(true);
            this.btSave.setDisable(true);
        }
    }

    public void handleAnotherFile() {
        this.returnControlsDefault();
    }

    private void returnControlsDefault() {
        this.comboBox.setDisable(false);
        this.btBrowse.setDisable(false);
        this.btSave.setDisable(true);
        this.btAnotherFile.setDisable(true);
        this.txtHeader.clear();
        this.tableView.getItems().clear();
        Huffman.returnDefault();

    }

    private void fillStatisticsTable() {
        for (int i = 0; i < Huffman.huffmanTable.length; i++) {
            //&& Huffman.huffmanTable[i].getASCII() != '\0'
            if (Huffman.huffmanTable[i] != null)
                this.tableView.getItems().add(Huffman.huffmanTable[i]);
        }
    }
}
