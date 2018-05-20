package bntu.diploma.controller;

import bntu.diploma.classes.WeatherDataStore;
import bntu.diploma.classes.map.InteractiveMap;
import bntu.diploma.classes.StationInfoPane;
import bntu.diploma.classes.map.StationWeatherInfoNode;
import bntu.diploma.model.Station;
import bntu.diploma.model.WeatherInfo;
import bntu.diploma.utils.DataUtils;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.UUID;


/**
 *
 *
 *
 *  DONE:
 *  1. make tables resize once the win size is changed
 *
 * */

public class Controller {

    // --------------------- STRUCTURE -----------------------
    @FXML
    private StackPane rootPane;
    @FXML
    private BorderPane rootBorderPane;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private SplitPane mainSplitPane;
    @FXML
    private AnchorPane mainSplitPane_leftAnchorPane;
    @FXML
    private AnchorPane mainSplitPane_rightAnchorPane;
    @FXML
    private StackPane mainSplitPane_right_stackPane;
    // --------------------- STRUCTURE -----------------------


    @FXML
    private MenuBar menuBar;
    @FXML
    private SplitPane rightSplitPane;
    @FXML
    private AnchorPane rightMenu_SplitPane_upperAnchorPane;
    @FXML
    private TableView<WeatherInfo> rightMenu_upperPane_allRecordsFromStationTable;
    @FXML
    private ListView stationsToBeAddedListView;

    @FXML
    private AnchorPane rightMenu_SplitPane_lowerAnchorPane;




    // -----------------------------------------------------------------------------------------------

    private Menu menuStation;
    private Menu menuReport;

    // ------------- CONSTANTS -----------------------------------------


    // ------------- CONSTANTS -----------------------------------------

    private InteractiveMap interactiveMap;
    private StationInfoPane stationInfoPane;
    private WeatherDataStore weatherDataStore;


    private SimpleLongProperty currentSelectedStationsID;
    private ObservableList<WeatherInfo> allRecordsFromStationList;

    public Controller() {


    }

    @FXML
    public void initialize(){

        weatherDataStore = WeatherDataStore.getInstance();

        initMenuBar();
        initMap();
        initListeners();


        mainSplitPane_right_stackPane.getChildren().get(0).toBack();


        populateAllRecordsFromStationTable(weatherDataStore.getAllWeatherInfoForStation(weatherDataStore.getAllStations().get(0).getStationsId()));
        populateStationsDetailedInformation(weatherDataStore.getStationInfo(weatherDataStore.getAllStations().get(0).getStationsId()));
//        populateAllRecordsFromStationTable(DataUtils.getListOfWeatherInfo());
//        populateStationsDetailedInformation(DataUtils.getStationInstance());

        // resize all elements
        rightMenu_SplitPane_upperAnchorPaneHeightChanged();
        mainSplitPane_leftAnchorPaneResized();
        rightAnchorPaneResized();
    }

    private void initMap(){

        interactiveMap = new InteractiveMap(mainSplitPane_leftAnchorPane);

        currentSelectedStationsID = interactiveMap.getCurrentSelectedStationsID();

        double x = 30;
        double y = 30;
        for (Station station : weatherDataStore.getAllStations()) {

            StationWeatherInfoNode infoNode = new StationWeatherInfoNode(
                    x,
                    y,
                    station.getStationsId(),
                    String.valueOf(x+y));

            x+=20;
            y+=35;

            infoNode.setStationParam(weatherDataStore.getLastWeatherInfo(2));
            interactiveMap.addStationInfoNode(infoNode);

        }


        Group group = new Group();
        group.getChildren().add(new Label("Station 1", new Button("Add this")));

        stationsToBeAddedListView.getItems().add(group);
        stationsToBeAddedListView.getItems().add(new Button("Add"));
        stationsToBeAddedListView.getItems().add(new Button("Add"));


        // TODO change this. Get coordinates from Station's model
        /*StationWeatherInfoNode infoNode = new StationWeatherInfoNode(
                Double.valueOf(ApplicationProperties.prop.getProperty("station.2.x")),
                Double.valueOf(ApplicationProperties.prop.getProperty("station.2.y")),
                2,
                "vileyka");

        infoNode.setStationParam(weatherDataStore.getLastWeatherInfo(2));
        interactiveMap.addStationInfoNode(infoNode);*/


//        interactiveMap.addStationInfoNode(DataUtils.getStationInfoNodeInstance());
//        interactiveMap.addStationInfoNode(DataUtils.getStationInfoNodeInstance());
//        interactiveMap.addStationInfoNode(DataUtils.getStationInfoNodeInstance());
//        interactiveMap.addStationInfoNode(DataUtils.getStationInfoNodeInstance());

        mainSplitPane_leftAnchorPane.getChildren().add(interactiveMap);
    }

    private void initListeners(){

        currentSelectedStationsID.addListener((observable, oldValue, newValue) -> selectedStationChanged());

        rootBorderPane.widthProperty().addListener((w) -> rooPaneWidthChanged());
        rootBorderPane.heightProperty().addListener(h -> rooPaneHeightChanged());

        mainSplitPane_leftAnchorPane.widthProperty().addListener(w -> mainSplitPane_leftAnchorPaneResized());
        mainSplitPane_leftAnchorPane.heightProperty().addListener(h -> mainSplitPane_leftAnchorPaneResized());

        mainSplitPane_rightAnchorPane.widthProperty().addListener(w -> rightAnchorPaneResized());
        //mainSplitPane_rightAnchorPane.heightProperty().addListener(h -> rightAnchorPaneResized());

        rightMenu_SplitPane_upperAnchorPane.heightProperty().addListener(h -> rightMenu_SplitPane_upperAnchorPaneHeightChanged());

        // -------------------- map ------------------
        //imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, imageClicked);
        // -------------------- map ------------------

    }


    private void initMenuBar(){

        // MENU
        menuStation = new Menu("Станция");
        menuReport = new Menu("Отчет");

        MenuItem menuItemAddNewStation = new MenuItem("Добавить новую станцию");
        menuItemAddNewStation.setOnAction(x -> menuItemAddNewStationClicked());

        MenuItem menuItemMoveExistingStation = new MenuItem("Переместить станцию");
        menuItemMoveExistingStation.setOnAction(this::menuItemMoveExistingStationClicked);

        MenuItem menuItemChangeStationInfo = new MenuItem("Изменить информацию о станции");
        menuItemChangeStationInfo.setOnAction(this::menuItemChangeStationInfoClicked);

        menuStation.getItems().addAll(menuItemMoveExistingStation, menuItemChangeStationInfo, menuItemAddNewStation);

        MenuItem menuItemHTMLReport = new MenuItem("Отчет в формате HTML");
        menuItemHTMLReport.setOnAction(x -> menuItemGenerateHTMLReportClicked());

        menuReport.getItems().addAll(menuItemHTMLReport);
        menuBar.getMenus().addAll(menuStation, menuReport);
    }


    private void selectedStationChanged() {


        populateAllRecordsFromStationTable(weatherDataStore.getRecordsForOneStation(currentSelectedStationsID.get()));
        populateStationsDetailedInformation(weatherDataStore.getStationInfo(currentSelectedStationsID.get()));


        System.out.println("A new dot is selected. Its id is - "+currentSelectedStationsID.getValue());

    }

    private void populateAllRecordsFromStationTable(List<WeatherInfo> list){


        rightMenu_upperPane_allRecordsFromStationTable.getColumns().clear();

        // make columns to take all available space
        rightMenu_upperPane_allRecordsFromStationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<WeatherInfo, String> temperature = new TableColumn<>("temp");
        temperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));

        TableColumn<WeatherInfo, String> humidity = new TableColumn<>("humid");
        humidity.setCellValueFactory(new PropertyValueFactory<>("humidity"));

        TableColumn<WeatherInfo, String> pressure = new TableColumn<>("pres");
        pressure.setCellValueFactory(new PropertyValueFactory<>("pressure"));

        TableColumn<WeatherInfo, String> wind_speed = new TableColumn<>("w_speed");
        wind_speed.setPrefWidth(40);
        wind_speed.setCellValueFactory(new PropertyValueFactory<>("windSpeed"));

        TableColumn<WeatherInfo, String> wind_direction = new TableColumn<>("w_dir");
        wind_direction.setPrefWidth(30);
        wind_direction.setCellValueFactory(new PropertyValueFactory<>("windDirection"));

        TableColumn<WeatherInfo, String> date_time = new TableColumn<>("date_time");
        date_time.setMinWidth(70);
        date_time.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        allRecordsFromStationList = FXCollections.observableArrayList(list);

        // adding headers
        rightMenu_upperPane_allRecordsFromStationTable.getColumns().addAll(temperature, humidity, pressure, wind_speed, wind_direction, date_time);
        // adding allRecordsFromStationList
        rightMenu_upperPane_allRecordsFromStationTable.setItems(allRecordsFromStationList);

    }


    private void populateStationsDetailedInformation(Station station){

        stationInfoPane = new StationInfoPane();
        stationInfoPane.addInfoRow(station);

        rightMenu_SplitPane_lowerAnchorPane.getChildren().clear();
        rightMenu_SplitPane_lowerAnchorPane.getChildren().add(stationInfoPane);
    }



    // ------------------------------- MENU BAR METHODS START ---------------------------------------------------------
    private void menuItemAddNewStationClicked() {
        interactiveMap.addNewStation(null);
    }

    private void menuItemChangeStationInfoClicked(ActionEvent event) {

        System.out.println("changing station");
    }

    private void menuItemMoveExistingStationClicked(ActionEvent x) {

        interactiveMap.startMovingStationInfoNode();

        // show a hint
    }


    private void menuItemGenerateHTMLReportClicked() {

        // add a new row to the allRecords table
        allRecordsFromStationList.add(0, DataUtils.getWeatherInfoInstance());

        // replace detailed station's info
        Station s = DataUtils.getStationInstance();
        s.setNearestTown(UUID.randomUUID().toString().substring(0, 5));
        populateStationsDetailedInformation(s);

        System.out.println("menuItemGenerateHTMLReportClicked");
    }
    // ------------------------------- MENU BAR METHODS END ---------------------------------------------------------


    // -------------------------- Listeners' methods ------------------------------------------------------------------------------
    // changes height of upper and lower tables to fit the panes they are wrapped in
    private void rightMenu_SplitPane_upperAnchorPaneHeightChanged() {

        //rightMenu_lowerPane_allStationsTable.setPrefHeight(rightMenu_SplitPane_lowerAnchorPane.getHeight());
        rightMenu_upperPane_allRecordsFromStationTable.setPrefHeight(rightMenu_SplitPane_upperAnchorPane.getHeight());
    }


    // changes the size of canvas to fit the anchor it is wrapped in
    private void mainSplitPane_leftAnchorPaneResized() {

        //canvas.setHeight(mainSplitPane_leftAnchorPane.getHeight());
        //canvas.setWidth(mainSplitPane_leftAnchorPane.getWidth());

        //imageView.setFitWidth(mainSplitPane_leftAnchorPane.getWidth());
        //imageView.setFitHeight(mainSplitPane_leftAnchorPane.getHeight());
    }

    // changes width of upped and lower tables to fit the panes they are wrapped in
    private void rightAnchorPaneResized() {
        //rightMenu_lowerPane_allStationsTable.setPrefWidth(mainSplitPane_rightAnchorPane.getWidth());
        rightMenu_upperPane_allRecordsFromStationTable.setPrefWidth(mainSplitPane_rightAnchorPane.getWidth());
    }

    private void rooPaneWidthChanged(){

    }

    private void rooPaneHeightChanged(){

    }
    // -------------------------- Listeners' methods ------------------------------------------------------------------------------


}
