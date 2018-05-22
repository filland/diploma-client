package bntu.diploma.classes.map;

import bntu.diploma.model.Station;
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
public class StationWeatherInfoNode extends Group {

    // The ID of the station which is being represented by this Node
    private long id;

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


    public StationWeatherInfoNode(Station station){

        this(station.getCoordinateXOnInteractiveMap(),
                station.getCoordinateYOnInteractiveMap(),
                station.getStationsId(), station.getNearestTown());

    }

    public StationWeatherInfoNode(double x, double y, long id, String name) {

        this.id =id;

        this.setId(String.valueOf(id));

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
        boarder.setId(String.valueOf(this.id));

        dot = new Circle(x,y,10,Paint.valueOf("GREEN"));
        dot.setId(String.valueOf(new Date()));
        dot.setId(String.valueOf(this.id));

        this.getChildren().addAll(nameLabel, boarder, dot);
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

        yShift=0;

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

        topLeftX = newXcoord;
        topLeftY = newYcoord;
    }

    public void scaleStationInfoNode(double scaleX, double scaleY){

        this.relocate(topLeftX*scaleX-10, topLeftY*scaleY-30);

        topLeftX*=scaleX;
        topLeftY*=scaleY;
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

    private void setStationParam(String paramName, String paramValue) {

        // to avoid duplications
        if(params.containsValue(params.get(paramName)))
            return;

        Label label = new Label(paramName+" : "+paramValue);

        label.setLayoutX(topLeftX+10);
        // we need to move only vertically
        label.setLayoutY(topLeftY+10+yShift);
        // all params of the station has the same id
        //label.setId(this.id);

        //System.out.println("label.getId()  - " +label.getId());

        params.put(paramName, label);

        // to put the next param 15 lower vertically
        yShift+=15;

        // hide new params if appears
        if (stationInfoVisible == false)
            params.get(paramName).setVisible(false);

        this.getChildren().addAll(params.get(paramName));


        System.out.println("info node params' size - "+params.values().size());
    }
}
