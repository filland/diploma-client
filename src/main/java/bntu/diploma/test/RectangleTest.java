package bntu.diploma.test;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RectangleTest extends Application {

    private StationInfoNode infoNode;


    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane pane = new AnchorPane();

        infoNode = new StationInfoNode(50, 50, 100, 100);
        infoNode.setStationParam("temp", "25.5");

        infoNode.set


        Button button = new Button("Move");
        button.setOnMouseClicked(event -> buttonClicked());

        pane.getChildren().addAll(infoNode);
        pane.getChildren().add(button);

        Scene scene = new Scene(pane, 700.0, 700.0);

        primaryStage.setTitle("Rect test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void buttonClicked() {

        System.out.println("infoNode.getX() - "+ infoNode.getTopLeftX());
        System.out.println("infoNode.getY() - "+ infoNode.getTopLeftY());

        infoNode.moveStationInfoNode();

    }

    public static void main(String[] args) {

        launch();

    }
}
