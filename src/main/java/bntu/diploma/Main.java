package bntu.diploma;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/view.fxml"));

        primaryStage.setTitle("WeatherTower");
        primaryStage.setScene(new Scene(root));

        primaryStage.setMinWidth(1050);
        primaryStage.setMinHeight(750);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
