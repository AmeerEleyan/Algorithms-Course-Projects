/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 5/12/2021    3:40 PM
 */
package gps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class InterfaceLoader extends Application {
    public static Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainInterface.fxml")));
        window.setTitle("GPS");
        window.setScene(new Scene(root));
        window.setResizable(false);
        window.show();
        Message.displayMessage("Hint", """
                Please select the source and the distinction city from the list
                or click in the map using left mouse button to select the source
                and right mouse button to select the distinction""");
    }
}
