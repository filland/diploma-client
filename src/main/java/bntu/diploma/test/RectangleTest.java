package bntu.diploma.test;

import bntu.diploma.classes.map.InteractiveMap;
import bntu.diploma.classes.map.StationInfoNode;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class RectangleTest extends Application {

    private StationInfoNode infoNode;
    private InteractiveMap group;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();

        infoNode = new StationInfoNode(50, 50, 1, "vileyka");
//        infoNode.setStationParam("temp", "1");
//        infoNode.setStationParam("temp2", "25.5");
//        infoNode.setStationParam("temp3", "25.5");

        StationInfoNode infoNode2 = new StationInfoNode(250 , 50, 2, "gomel");
//        infoNode2.setStationParam("temp", "2");
//        infoNode2.setStationParam("temp2", "25.5");
//        infoNode2.setStationParam("temp3", "25.5");

        group = new InteractiveMap(borderPane);
        group.addStationInfoNode(infoNode);
        group.addStationInfoNode(infoNode2);

        EventHandler<MouseEvent> mousePressedEventHandler = this::groupClicked;
        group.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedEventHandler);

        Button buttonRight = new Button("changeCoord");
        buttonRight.setOnMouseClicked(event -> right());

        borderPane.setTop(buttonRight);
        borderPane.setCenter(group);

        Scene scene = new Scene(borderPane, 700.0, 700.0);

        primaryStage.setTitle("Interactive map");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void groupClicked(MouseEvent event){

        // as an example of using EventHandler

    }



    private void right() {



        group.startMovingStationInfoNode();

    }

    public static void main(String[] args) {

        launch();

    }
}
