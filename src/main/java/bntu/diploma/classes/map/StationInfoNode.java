package bntu.diploma.classes.map;

import bntu.diploma.model.WeatherInfo;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.*;



/**
 *
 * This class is designed to graphically represent current data
 * received from a weather station
 *
 * Visually this class looks like a table of parameters.
 *
 * */
public class StationInfoNode extends Group {


    private String id;

    // visible name of the node
    private Label nameLabel;

    // storing node's parameters
    private Map<String, Label> params = null;

    // dot that represents a station
    private Circle dot = null;

    // shape that containing params
    private Rectangle boarder = null;

    // coords of the infoNode
    private double topLeftX;
    private double topLeftY;

    // shifts for params in the infoNode
    private double yShift = 0;

    // status of visibility of all components except the dot
    private boolean stationInfoVisible = false;

    // width on the rectangle which works as a frame
    private double boarderWidth = 140;
    private double boarderHeight = 120;

    public StationInfoNode(double x, double y, long id, String name) {

        this.id = String.valueOf(id);

        params = new HashMap<>();

        topLeftX = x;
        topLeftY = y;

        nameLabel = new Label(name+" "+this.id);
        nameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15.0));
        nameLabel.setStyle("-fx-background-color: #90EE90;");
        nameLabel.setLayoutX(topLeftX+5);
        nameLabel.setLayoutY(topLeftY-30);

        boarder = new Rectangle(x, y, boarderWidth, boarderHeight);
        boarder.setStroke(Color.BLACK);
        boarder.setFill(Color.LIGHTGREEN);
        boarder.setVisible(stationInfoVisible);
        boarder.setId(this.id);

        dot = new Circle(x,y,10,Paint.valueOf("GREEN"));
        dot.setId(String.valueOf(new Date()));
        dot.setId(this.id);

        this.getChildren().addAll(nameLabel, boarder, dot);
    }

    private void setStationParam(String paramName, String paramValue) {

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

    public void setStationParam(WeatherInfo weatherInfo){

        params.forEach((s, label) -> this.getChildren().remove(label));
        params.clear();

        setStationParam("temp", String.valueOf(weatherInfo.getTemperature())+" g.");
        setStationParam("press", String.valueOf(weatherInfo.getPressure())+" м.р.т");
        setStationParam("humid", String.valueOf(weatherInfo.getHumidity())+" %");
        setStationParam("wind_s", String.valueOf(weatherInfo.getWindSpeed())+" m/s");
        setStationParam("wind_dir" , String.valueOf(weatherInfo.getWindDirection()));
        setStationParam("battery", String.valueOf(weatherInfo.getBatteryLevel())+" %");

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

        this.relocate(newXcoord-10, newYcoord-30);

//        this.setLayoutX(newXcoord-topLeftX);
//        this.setLayoutY(newYcoord-topLeftY);

        topLeftX = newXcoord;
        topLeftY = newYcoord;
    }

    public void scaleStationInfoNode(double scaleX, double scaleY){

        System.out.println("scale x - "+scaleX);
        System.out.println("scale y - "+scaleY);
        System.out.println("old topLeftX - "+topLeftX);
        System.out.println("old topLeftY - "+topLeftY);

        this.relocate(topLeftX*scaleX-10, topLeftY*scaleY-30);

        topLeftX*=scaleX;
        topLeftY*=scaleY;

        System.out.println("new topLeftX - "+topLeftX);
        System.out.println("new topLeftY - "+topLeftY+"\n");
    }

    public Double getTopLeftX() {
        return topLeftX;
    }

    public Double getTopLeftY() {
        return topLeftY;
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
