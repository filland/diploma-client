package bntu.diploma.test;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ShapesTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane pane = new AnchorPane();



        Group group = new Group();

        Circle circle = new Circle(40.0,40.0, 7);
        Rectangle rectangle = new Rectangle(100, 100, 30, 30);

        group.getChildren().addAll(circle, rectangle);


        pane.setOnMouseClicked(click ->{

            System.out.println(click.getX());
            System.out.println(click.getY());
            System.out.println();

            circle.setCenterX(circle.getCenterY()+5);
            circle.setCenterY(circle.getCenterY()+5);

        });

        pane.getChildren().addAll(group);

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
