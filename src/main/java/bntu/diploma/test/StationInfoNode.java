package bntu.diploma.test;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.*;



public class StationInfoNode extends Group {

    private String id;

    private Map<String, Label> params = null;

    // dot that represents a station

    private Circle dot = null;
    // shape that containing params

    private Rectangle boarder = null;
    // coords of the infoNode

    private double topLeftX;
    private double topLeftY;
    // shifts for params in the infoNode

    //private final double xShift = 10;
    private double yShift = 0;

    // status of visibility of all components except the dot
    private boolean stationInfoVisible = false;

    // width on the rectangle which works as a frame
    private double boarderWidth = 100;
    private double boarderHeight = 100;

    public StationInfoNode(double x, double y, String id) {

        this.id = id;

        params = new HashMap<>();

        topLeftX = x;
        topLeftY = y;

        boarder = new Rectangle(x, y, boarderWidth, boarderHeight);
        boarder.setStroke(Color.BLACK);
        boarder.setFill(Color.LIGHTGREEN);
        boarder.setVisible(stationInfoVisible);
        boarder.setId(id);

        dot = new Circle(x,y,10,Paint.valueOf("GREEN"));
        dot.setId(String.valueOf(new Date()));
        dot.setId(this.id);

        this.getChildren().addAll(boarder, dot);
    }

    public void setStationParam(String paramName, String paramValue) {

        // to avoid duplications
        if(params.containsValue(params.get(paramName)))
            return;

        params.put(paramName, new Label(paramName+" : "+paramValue));
        params.get(paramName).setLayoutX(topLeftX+10);
        // we need to move only vertically
        params.get(paramName).setLayoutY(topLeftY+10+yShift);
        // all params of the station has the same id
        params.get(paramName).setId(id);

        // to put the next param 15 lower vertically
        yShift+=15;

        // hide new params if appears
        if (!stationInfoVisible)
            params.get(paramName).setVisible(stationInfoVisible);

        this.getChildren().addAll(params.get(paramName));
    }

    // hide all shapes except dot
    public void hideInfo(){

        boarder.setVisible(false);
        params.forEach((s, label) -> label.setVisible(false));
    }

    // show all shapes except dot
    public void showInfo(){

        boarder.setVisible(true);
        params.forEach((s, label) -> label.setVisible(true));
    }

    public void moveStationInfoNode(double newXcoord, double newYcoord){
        this.setLayoutX(newXcoord-topLeftX);
        this.setLayoutY(newYcoord-topLeftY);
    }

    public Double getTopLeftX() {
        return boarder.getX();
    }

    public Double getTopLeftY() {
        return boarder.getY();
    }

    public Map<String, Label> getMapWithLabels() {
        return params;
    }

    public Circle getDot() {
        return dot;
    }

    public boolean isStationInfoVisible() {
        return stationInfoVisible;
    }

    public void setStationInfoVisible(boolean stationInfoVisible) {
        this.stationInfoVisible = stationInfoVisible;
    }
}
