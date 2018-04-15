package bntu.diploma.controller;

import bntu.diploma.beans.StationInfo;
import bntu.diploma.beans.StationWeatherInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapController {


    @FXML
    private SplitPane mainSplitPane;
    @FXML
    private BorderPane rootBorderPane;
    @FXML
    private ScrollPane rightMenu_upperScrollPane;
    @FXML
    private ScrollPane rightMenu_lowerScrollPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Canvas canvas;
    @FXML
    private SplitPane rightSplitPane;
    @FXML
    private AnchorPane rightMenu_upperPane;
    @FXML
    private AnchorPane rightMenu_lowerPane;

    private TableView<StationWeatherInfo> rightMenu_upperPane_detailedInfoTable;
    private TableView<StationInfo> rightMenu_lowerPane_allStationsTable;

    private Menu menuFile;

    public MapController(){

        // RIGHT UPPER PANE
        rightMenu_upperPane_detailedInfoTable = new TableView<>();

        // RIGHT LOWER PANE
        rightMenu_lowerPane_allStationsTable = new TableView<>();

        // MENU
        menuFile = new Menu("File");

    }

    @FXML
    public void initialize(){

        populateMenuBar();

        List<StationWeatherInfo> sysList = new ArrayList<>();
        sysList.add(new StationWeatherInfo(12.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new StationWeatherInfo(12.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new StationWeatherInfo(12.1, 2.2, 10.1, 10.11, 12));
        populateRightMenu_upperPane_detailedInfoTable(sysList);

        List<StationInfo> sysList2 = new ArrayList<>();
        sysList2.add(new StationInfo("asdf", new Date(2017, 3, 15),"asdf", "123"));
        sysList2.add(new StationInfo("asdf", new Date(2017, 3, 15),"asdf","asdf"));
        sysList2.add(new StationInfo("asdf", new Date(2017, 3, 15),"asdf","asdf"));
        populateRightMenu_lowerPane_allStationsTable(sysList2);

    }

    public void populateMenuBar(){

        MenuItem menuItemExportData = new MenuItem("Export");
        menuItemExportData.setOnAction(event -> method1111());
        MenuItem menuItemPrint = new MenuItem("Print");
        MenuItem menuItemExit = new MenuItem("Exit");
        menuFile.getItems().addAll(menuItemExportData, menuItemPrint, menuItemExit);
        menuBar.getMenus().add(menuFile);

    }


    public void populateRightMenu_upperPane_detailedInfoTable(List<StationWeatherInfo> list){

        TableColumn<StationWeatherInfo, String> temperature = new TableColumn<>("temperature");
        temperature.setCellValueFactory(
                new PropertyValueFactory<>("temperature"));

        TableColumn<StationWeatherInfo, String> humidity = new TableColumn<>("humidity");
        humidity.setCellValueFactory(
                new PropertyValueFactory<>("humidity"));

        TableColumn<StationWeatherInfo, String> pressure = new TableColumn<>("pressure");
        pressure.setCellValueFactory(
                new PropertyValueFactory<>("pressure"));

        TableColumn<StationWeatherInfo, String> wind_speed = new TableColumn<>("wind_speed");
        wind_speed.setCellValueFactory(
                new PropertyValueFactory<>("wind_speed"));

        TableColumn<StationWeatherInfo, String> wind_direction = new TableColumn<>("wind_direction");
        wind_direction.setCellValueFactory(
                new PropertyValueFactory<>("wind_direction"));

        ObservableList<StationWeatherInfo> data = FXCollections.observableArrayList(list);

        // adding headers
        rightMenu_upperPane_detailedInfoTable.getColumns().addAll(temperature, humidity, pressure, wind_speed, wind_direction);
        // adding data
        rightMenu_upperPane_detailedInfoTable.setItems(data);

        rightMenu_upperScrollPane.setContent(rightMenu_upperPane_detailedInfoTable);

        // make columns to take all available space
        rightMenu_upperPane_detailedInfoTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }


    public void populateRightMenu_lowerPane_allStationsTable(List<StationInfo> list){

        TableColumn<StationInfo, String> name = new TableColumn<>("name");
        name.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        TableColumn<StationInfo, String> nearestTown = new TableColumn<>("nearestTown");
        nearestTown.setCellValueFactory(
                new PropertyValueFactory<>("nearestTown"));
        TableColumn<StationInfo, String> creationDate = new TableColumn<>("creationDate");
        creationDate.setCellValueFactory(
                new PropertyValueFactory<>("creationDate"));
        TableColumn<StationInfo, String> coordinates = new TableColumn<>("coordinates");
        coordinates.setCellValueFactory(
                new PropertyValueFactory<>("coordinates"));

        ObservableList<StationInfo> data = FXCollections.observableArrayList(list);

        // adding headers
        rightMenu_lowerPane_allStationsTable.getColumns().setAll(name, nearestTown, creationDate, coordinates);
        // adding data
        rightMenu_lowerPane_allStationsTable.setItems(data);

        rightMenu_lowerScrollPane.setContent(rightMenu_lowerPane_allStationsTable);

        // make columns to take all available space
        rightMenu_lowerPane_allStationsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    private void method1111() {

        rightMenu_lowerPane_allStationsTable.setMinWidth(500);

    }



}
