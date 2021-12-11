
package gps;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {


    @FXML // fx:id="anchorPane"
    private AnchorPane anchorPane; // Value injected by FXMLLoader

    @FXML // fx:id="combDestinationCity"
    private ComboBox<String> combDestinationCity; // Value injected by FXMLLoader

    @FXML // fx:id="combSrcCity"
    private ComboBox<String> combSourceCity; // Value injected by FXMLLoader

    @FXML // fx:id="txtDistance"
    private TextField txtDistance; // Value injected by FXMLLoader

    @FXML // fx:id="txtPath"
    private TextArea txtPath; // Value injected by FXMLLoader

    // Using this attribute to handling
    private ObservableList<Line> lineForPath;
    private HashMap<String, CreateCityInTheMap> citiesInMap;

    private Graph graphCities; // Map

    private String[] citiesNames; // cities name in the map

    private String sourceCity, destinationCity; // value of the comboBox


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.readBabyRecordFromFile();
        this.loadCitiesNameToComboBox();
    }

    /**
     * Methode to read data from file iteratively
     */
    private void readBabyRecordFromFile() {
        File cities = new File("Cities.txt");
        int numberOfCities = 0;
        try {
            Scanner input = new Scanner(cities); // instance of scanner for read data from file
            if (cities.length() == 0) {
                // no data in file
                Message.displayMessage("Warning", "  There are No records in the " + cities.getName() + " ");
            } else {
                int line = 1; // represent line on the file to display in which line has problem If that happens
                String[] data;
                String cityName;
                float latitude, longitude;
                City city;
                float layout_X_Map, layout_Y_Map;
                int indexCitiesName = 0;
                while (input.hasNext()) { // read line of data
                    try {
                        String lineOfData = input.nextLine();
                        if (line == 1) {
                            // get number of cities and initialize the graph and hashMap
                            numberOfCities = Integer.parseInt(lineOfData.trim());
                            this.graphCities = new Graph(numberOfCities);
                            this.citiesInMap = new HashMap<>(numberOfCities);
                            this.citiesNames = new String[numberOfCities];

                        } else if (line > 1 && line < numberOfCities + 2) {
                            // get data of this city
                            data = lineOfData.split(",");
                            cityName = data[0].trim();
                            longitude = Float.parseFloat(data[1].trim());
                            latitude = Float.parseFloat(data[2].trim());
                            city = new City(cityName, longitude, latitude);
                            this.graphCities.addNewCities(city);
                            this.citiesNames[indexCitiesName++] = cityName;

                            // add this city in the map and store in hash to access it later
                            layout_X_Map = Integer.parseInt(data[3].trim());
                            layout_Y_Map = Integer.parseInt(data[4].trim());
                            CreateCityInTheMap cityMap = new CreateCityInTheMap(cityName, layout_X_Map, layout_Y_Map);
                            this.citiesInMap.put(cityName, cityMap);
                            this.anchorPane.getChildren().addAll(cityMap.getCityName(), cityMap.getCityPosition());
                        } else {
                            data = lineOfData.split(" ");
                            this.graphCities.addAdjacent(data[0], data[1]);
                        }
                        line++; // increment the line by one

                    } catch (Exception ex) {
                        // the record in the file has a problem
                        Message.displayMessage("Warning", " Invalid format in line # " + line + " in file " + cities.getName() + "  ");
                    }
                }
                input.close(); // prevent(close) scanner to read data
            }

        } catch (FileNotFoundException e) {
            // The specific file for reading data does not exist
            Message.displayMessage("Error", " The system can NOT find the file " + cities.getName() + "  ");
        }
    }

    // To load cities names from file to tha combo box
    private void loadCitiesNameToComboBox() {
        for (String citiesName : this.citiesNames) {
            this.combSourceCity.getItems().add(citiesName);
            this.combDestinationCity.getItems().add(citiesName);
        }
    }


    public void handleBtGo() {
        if (this.combSourceCity != null) {
            if (this.combDestinationCity != null) {
                this.sourceCity = this.combSourceCity.getValue();
                this.destinationCity = this.combDestinationCity.getValue();
                ShortestPath shortestPath = this.graphCities.findShortestPath(sourceCity, destinationCity);
                if (shortestPath != null) {
                    if (this.sourceCity.equals(this.destinationCity)) {
                        Message.displayMessage("Warning", "the source city is the same destination city\nso the distance 0.0 Km");
                    } else {
                        this.txtDistance.setText(String.format("%.2f Km", shortestPath.getTotalDistance()) );
                        String citiesInThePath = getCitiesInThePathAsString(shortestPath.getCitiesInThePath());
                        this.txtPath.setText(citiesInThePath);
                        this.drawPath(shortestPath.getCitiesInThePath());
                    }

                } else {
                    Message.displayMessage("Warning", "There is no path between " + this.sourceCity + " and " + this.destinationCity);
                }
            } else {
                Message.displayMessage("Warning", "Please select the destination city");
            }
        } else {
            Message.displayMessage("Warning", "Please select the source city");
        }
    }

    public void handleBtAnotherPath() {
        this.txtDistance.setText("0.0 Km");
        this.txtPath.clear();
        this.anchorPane.getChildren().removeAll(lineForPath); // remove lineFromPath
        this.lineForPath = null;
    }

    @FXML
    void handleGetCity(MouseEvent event) {
        float x, y;
        if (event.getButton() == MouseButton.PRIMARY) {
            x = (float) event.getPickResult().getIntersectedNode().getLayoutX();
            y = (float) event.getPickResult().getIntersectedNode().getLayoutY();
            if (x > 0 && y > 0) {
                this.sourceCity = getCityClicked(x, y);
                this.combSourceCity.setValue(this.sourceCity);
            }
        }
        if (event.getButton() == MouseButton.SECONDARY) {
            x = (float) event.getPickResult().getIntersectedNode().getLayoutX();
            y = (float) event.getPickResult().getIntersectedNode().getLayoutY();
            if (x > 0 && y > 0) {
                this.destinationCity = getCityClicked(x, y);
                this.combDestinationCity.setValue(this.destinationCity);
            }
        }

    }

    private void drawPath(LinkedList<City> citiesInThePath) {
        Line line;
        this.lineForPath = FXCollections.observableArrayList();
        for (int i = 0; i < citiesInThePath.size() - 1; i++) {
            line = new Line();

            CreateCityInTheMap city1 = this.citiesInMap.get(citiesInThePath.get(i).getCityName());
            CreateCityInTheMap city2 = this.citiesInMap.get(citiesInThePath.get(i + 1).getCityName());

            line.setStartX(city1.getCityPosition().getLayoutX());
            line.setStartY(city1.getCityPosition().getLayoutY());

            line.setEndX(city2.getCityPosition().getLayoutX());
            line.setEndY(city2.getCityPosition().getLayoutY());
            this.lineForPath.add(line);
        }
        anchorPane.getChildren().addAll(this.lineForPath);
    }

    private String getCitiesInThePathAsString(LinkedList<City> citiesInThePath) {
        StringBuilder path = new StringBuilder("Start: ");
        byte newLine = 0;
        for (City city : citiesInThePath) {
            // To style the text area
            if (newLine == 4) {
                path.append("\n");
                newLine = 0;
            }
            path.append(city.getCityName()).append(", ");
            newLine++;

        }
        return path.substring(0, path.length() - 2);
    }

    private String getCityClicked(float layoutX, float layoutY) {
        for (CreateCityInTheMap city : this.citiesInMap.values()) {
            if (layoutX == city.getCityPosition().getLayoutX() && layoutY == city.getCityPosition().getLayoutY()) {
                return city.getCityName().getText();
            }
        }
        return null;
    }
}
