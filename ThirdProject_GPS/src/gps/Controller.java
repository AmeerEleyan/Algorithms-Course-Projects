/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 12/12/2021    10:21 AM
 */
package gps;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

    // all lines(path) between source and destination
    private ObservableList<Line> linesForPath;

    private HashMap<String, CreateCityInTheMap> citiesInMap; // all cities in the map

    private Graph graphCities; // Map

    private String[] citiesNames,// cities name in the map
            citiesInPath;  // cities in the path


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
                short layout_X_Map, layout_Y_Map;
                int indexCitiesName = 0;
                String cityPosition;
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
                            layout_X_Map = Short.parseShort(data[3].trim());
                            layout_Y_Map = Short.parseShort(data[4].trim());
                            city = new City(cityName, longitude, latitude, layout_X_Map, layout_Y_Map);
                            this.graphCities.addNewCities(city);
                            this.citiesNames[indexCitiesName++] = cityName;

                            // add this city in the map and store in hash to access it later
                            CreateCityInTheMap cityMap = new CreateCityInTheMap(cityName, layout_X_Map, layout_Y_Map);
                            cityPosition = layout_X_Map + " " + layout_Y_Map;
                            this.citiesInMap.put(cityPosition, cityMap);
                            this.anchorPane.getChildren().addAll(cityMap.getCityName(), cityMap.getCityPosition());
                        } else {
                            // Add the adjacent
                            data = lineOfData.split(" ");
                            if(!data[0].equals(data[1])){
                                this.graphCities.addAdjacent(data[0], data[1]);
                            }else{
                                Message.displayMessage("Warning", " Invalid format in line # " + line + " in file " + cities.getName() + "\n Neighbors are the same");
                            }
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
        // The source city not selected
        if (this.combSourceCity.getValue() == null) {
            Message.displayMessage("Warning", "Please select the source city");
            return;
        }
        // The destination city not selected
        if (this.combDestinationCity.getValue() == null) {
            Message.displayMessage("Warning", "Please select the destination city");
            return;
        }

        String sourceCity = this.combSourceCity.getValue();
        String destinationCity = this.combDestinationCity.getValue();

        //the source city is the same destination city
        if (sourceCity.equals(destinationCity)) {
            Message.displayMessage("Warning", "the source city is the same destination city\nso the distance 0.0 Km");
        } else {
            ShortestPath shortestPath = this.graphCities.findShortestPath(sourceCity, destinationCity);
            if (shortestPath != null) {
                this.handleBtAnotherPath();
                this.txtDistance.setText(String.format("%.2f Km", shortestPath.getTotalDistance()));
                String citiesInThePath = getCitiesInThePathAsString(shortestPath.getCitiesInThePath());
                this.txtPath.setText(citiesInThePath);
                this.drawPath(shortestPath.getCitiesInThePath());

            } else {// There is no path between the source and destination
                Message.displayMessage("Warning", "There is no path between " + sourceCity + " and " + destinationCity);
            }
        }

    }

    // Reset everything to default
    public void handleBtAnotherPath() {
        this.txtDistance.setText("0.0 Km");
        this.txtPath.clear();
        if (linesForPath != null) {
            this.anchorPane.getChildren().removeAll(linesForPath); // remove lineFromPath
            this.linesForPath = null;
            this.reSetColorsForCitiesInThePath();
            this.citiesInPath = null;
        }
    }

    // To detect which is the source and destination city
    @FXML
    void handleGetCity(MouseEvent event) {
        short x, y;
        Node node;
        String cityName;
        node = event.getPickResult().getIntersectedNode();
        if (node instanceof Circle) {
            x = (short) node.getLayoutX();
            y = (short) node.getLayoutY();
            // to detect which is the source city
            if (event.getButton() == MouseButton.PRIMARY) {
                this.handleBtAnotherPath();
                cityName = this.citiesInMap.get(x + " " + y).getCityName().getText();
                this.combSourceCity.setValue(cityName);

            }
            // to detect which is the destination city
            if (event.getButton() == MouseButton.SECONDARY) {
                cityName = this.citiesInMap.get(x + " " + y).getCityName().getText();
                this.combDestinationCity.setValue(cityName);
            }
        }

    }

    private void drawPath(LinkedList<City> citiesInThePath) {
        Line line; // line between two city
        this.citiesInPath = new String[citiesInThePath.size()]; // to coloring the cities that in the path
        this.linesForPath = FXCollections.observableArrayList(); // all lines in the path
        int i;
        for (i = 0; i < citiesInThePath.size() - 1; i++) {
            line = new Line();

            citiesInPath[i] = citiesInThePath.get(i).getLayouts_Map();
            this.citiesInMap.get(citiesInThePath.get(i).getLayouts_Map()).getCityPosition().setFill(Color.BLUE);
            this.citiesInMap.get(citiesInThePath.get(i + 1).getLayouts_Map()).getCityPosition().setFill(Color.BLUE);

            CreateCityInTheMap city1 = this.citiesInMap.get(citiesInThePath.get(i).getLayouts_Map());
            CreateCityInTheMap city2 = this.citiesInMap.get(citiesInThePath.get(i + 1).getLayouts_Map());

            line.setStartX(city1.getCityPosition().getLayoutX());
            line.setStartY(city1.getCityPosition().getLayoutY());

            line.setEndX(city2.getCityPosition().getLayoutX());
            line.setEndY(city2.getCityPosition().getLayoutY());
            line.setStroke(Color.BLUE);
            this.linesForPath.add(line);
        }
        citiesInPath[i] = citiesInThePath.get(i).getLayouts_Map(); // add the last city in the path to tha array
        anchorPane.getChildren().addAll(this.linesForPath); // add all line in the anchor pane to draw tha path
    }

    // reset color of the cities that in the path to the origin color
    private void reSetColorsForCitiesInThePath() {
        for (String c : citiesInPath) {
            this.citiesInMap.get(c).getCityPosition().setFill(Color.RED);
        }
    }

    // get cities in the path as a string
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

}
