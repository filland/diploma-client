package bntu.diploma.test;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.*;


public class StationInfoNode extends Group {

    private Map<String, Label> params = null;
    private Rectangle rectangle = null;

    private Double topLeftX = null;
    private Double topLeftY = null;

    public StationInfoNode(double x, double y, double width, double height) {
        topLeftX = x;
        topLeftY = y;

        this.setLayoutX(x);
        this.setLayoutY(y);

        rectangle = new Rectangle(width, height);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.TRANSPARENT);

        params = new HashMap<>();

        this.getChildren().add(rectangle);
    }

    public void setStationParam(String paramName, String paramValue) {
        params.put(paramName, new Label(paramName+" : "+paramValue));
        params.get(paramName).setLayoutX(topLeftX-40);
        params.get(paramName).setLayoutY(topLeftY-20);
        this.getChildren().add(params.get(paramName));
    }

    public Double getTopLeftX() {
        return rectangle.getX();
    }

    public Double getTopLeftY() {
        return rectangle.getY();
    }


    public void moveStationInfoNode(double newXcoord, double newYcoord){

        this.setLayoutX(newXcoord);
        this.setLayoutY(newYcoord);
    }

    public void moveStationInfoNode() {

        this.setLayoutX(this.getLayoutX()+10);
        this.setLayoutY(this.getLayoutY()+10);
    }

        public Map<String, Label> getMapWithLabels() {
        return params;
    }
}
