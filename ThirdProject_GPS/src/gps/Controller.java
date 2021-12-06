
package gps;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    private HashMap<String, CreateCityInTheMap> citiesInMap;
    private Graph graphCities;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CreateCityInTheMap city1 = new CreateCityInTheMap("Ramallah", 390, 351);
        CreateCityInTheMap city2 = new CreateCityInTheMap("Jenen", 244, 428);
        CreateCityInTheMap city3 = new CreateCityInTheMap("Jerusalem", 316, 322);
        anchorPane.getChildren().addAll(city1.getCityName(), city1.getCityPosition(),
                city2.getCityName(), city2.getCityPosition(), city3.getCityName(), city3.getCityPosition());
        Line l = new Line();
        l.setStartX(city1.getCityPosition().getLayoutX());
        l.setStartY(city1.getCityPosition().getLayoutY());
        l.setEndX(city2.getCityPosition().getLayoutX());
        l.setEndY(city2.getCityPosition().getLayoutY());

        Line l2 = new Line();
        l2.setStartX(city1.getCityPosition().getLayoutX());
        l2.setStartY(city1.getCityPosition().getLayoutY());
        l2.setEndX(city3.getCityPosition().getLayoutX());
        l2.setEndY(city3.getCityPosition().getLayoutY());
        anchorPane.getChildren().addAll(l, l2);

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
                while (input.hasNext()) { // read line of data
                    try {
                        String lineOfData = input.nextLine();
                        if (line == 1) {
                            // get number of cities and initialize the graph and hashMap
                            numberOfCities = Integer.parseInt(lineOfData.trim());
                            this.graphCities = new Graph(numberOfCities);
                            this.citiesInMap = new HashMap<>(numberOfCities);

                        } else if (line > 1 && line < numberOfCities + 2) {
                            // get data of this city
                            data = lineOfData.split(" ");
                            cityName = data[0].trim();
                            longitude = Float.parseFloat(data[1].trim());
                            latitude = Float.parseFloat(data[2].trim());
                            city = new City(cityName, longitude, latitude);
                            this.graphCities.addNewCities(city);

                            // add this city in the map and store in hash to access it later
                            layout_X_Map = city.getLayout_X_Map();
                            layout_Y_Map = city.getLayout_Y_Map();
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

}
