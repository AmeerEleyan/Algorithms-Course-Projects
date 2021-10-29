/**
 *      @author: Ameer Eleyan
 *      ID: 1191076
 *      At: 10/30/2021   1:00 AM
 */
package lcs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GUI extends Application {

    public Parent root;
    public static Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainInterface.fxml")));
        window.setTitle("Interface");
        window.setScene(new Scene(root));
        window.setResizable(false);
        window.show();
    }
}
