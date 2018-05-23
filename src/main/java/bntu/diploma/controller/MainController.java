package bntu.diploma.controller;

import bntu.diploma.classes.*;
import bntu.diploma.node.AddingNewStationsToMapTableView;
import bntu.diploma.node.AllRecordsTableView;
import bntu.diploma.node.StationInfoPane;
import bntu.diploma.node.map.InteractiveMap;
import bntu.diploma.node.map.StationWeatherInfoNode;
import bntu.diploma.model.Station;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 *
 *
 *  DONE:
 *  1. make tables resize once the win size is changed
 *
 * */

public class MainController {

    // --------------------- STRUCTURE -----------------------

    @FXML
    private BorderPane rootBorderPane;

    @FXML
    private SplitPane mainSplitPane;
    @FXML
    private AnchorPane mainSplitPane_leftAnchorPane;
    @FXML
    private AnchorPane mainSplitPane_rightAnchorPane;

    @FXML
    private StackPane rightSplitPane_upper_stackPane;
    // --------------------- STRUCTURE -----------------------


    @FXML
    private MenuBar menuBar;
    @FXML
    private SplitPane rightSplitPane;
    @FXML
    private AnchorPane rightMenu_SplitPane_upperAnchorPane;


    @FXML
    private AnchorPane rightMenu_SplitPane_lowerAnchorPane;




    // -----------------------------------------------------------------------------------------------

    private Menu menuUser;
    private Menu menuStation;
    private volatile Menu menuReport;

    private AddingNewStationsToMapTableView addingNewStationsToMapTableView;
    private AllRecordsTableView allRecordsFromStationTable;

    // ------------- CONSTANTS -----------------------------------------


    // ------------- CONSTANTS -----------------------------------------

    private volatile InteractiveMap interactiveMap;
    private volatile StationInfoPane stationInfoPane;
    private volatile WeatherDataStore weatherDataStore;

    private List<Stage> stageList;
    private Stage mainStage;

    private SimpleLongProperty currentSelectedStationsID;

    public MainController() {

        stageList = new ArrayList<>();
    }

    @FXML
    public void initialize(){


        weatherDataStore = WeatherDataStore.getInstance();
        stationInfoPane = StationInfoPane.getInstance();


        allRecordsFromStationTable = AllRecordsTableView.getInstance();
        rightSplitPane_upper_stackPane.getChildren().add(allRecordsFromStationTable);


        initMenuBar();
        initMap();



        initListeners();



        WeatherPostman weatherPostman = new WeatherPostman();
        weatherPostman.subscribe(allRecordsFromStationTable);
        weatherPostman.subscribe(interactiveMap);
        weatherPostman.subscribe(stationInfoPane);

        // start ???? a new thread and call the method for updating app's elements
        KeyFrame frame1 = new KeyFrame(Duration.seconds(15), event ->  weatherPostman.sendInfoToSubscribers());
        Timeline timeline = new Timeline(frame1);
        timeline.setCycleCount(Animation.INDEFINITE);
        Platform.runLater(timeline::play);


        allRecordsFromStationTable.populate(weatherDataStore.getAllWeatherInfoForStation(weatherDataStore.getAllStations().get(0).getStationsId()));
        populateStationsDetailedInformation(weatherDataStore.getStationInfo(weatherDataStore.getAllStations().get(0).getStationsId()));


        // resize all elements
        rightMenu_SplitPane_upperAnchorPaneHeightChanged();
        mainSplitPane_leftAnchorPaneResized();
        rightAnchorPaneResized();

        Platform.runLater(() -> menuBar.getScene().getWindow().setOnCloseRequest(event -> {

            System.out.println("closing opened sub-frames");
            for (Stage stage : stageList) {
                stage.close();
            }

        }));

    }


    private void initMap(){

        interactiveMap = new InteractiveMap(mainSplitPane_leftAnchorPane);
        mainSplitPane_leftAnchorPane.getChildren().add(interactiveMap);

        currentSelectedStationsID = interactiveMap.getCurrentSelectedStationsID();

        addingNewStationsToMapTableView = new AddingNewStationsToMapTableView(interactiveMap, stationInfoPane);
        addingNewStationsToMapTableView.getAllStationsHaveCoordinates().addListener((observable, oldValue, newValue) -> {

            if (addingNewStationsToMapTableView.getAllStationsHaveCoordinates().get()){

                rightSplitPane_upper_stackPane.getChildren().get(0).toFront();

                for (AddingNewStationsToMapTableView.Row row : addingNewStationsToMapTableView.getRows()) {

                    StationWeatherInfoNode node = new StationWeatherInfoNode(weatherDataStore.getStationInfo(Long.parseLong(row.getId())));
                    node.setStationParam(weatherDataStore.getLastWeatherInfo(Long.parseLong(row.getId())));

                    interactiveMap.addStationInfoNode(node);

                }

            }

        });

        rightSplitPane_upper_stackPane.getChildren().add(addingNewStationsToMapTableView);


        for (Station station : weatherDataStore.getAllStations()) {

            // station does not have coords for interactive map
            if (station.getCoordinateXOnInteractiveMap() == null ||
                    station.getCoordinateYOnInteractiveMap() == null){

                AddingNewStationsToMapTableView.Row row = new AddingNewStationsToMapTableView.Row(station);
                addingNewStationsToMapTableView.addRow(row);

            } else {

                StationWeatherInfoNode node = new StationWeatherInfoNode(station);
                node.setStationParam(weatherDataStore.getLastWeatherInfo(station.getStationsId()));

                interactiveMap.addStationInfoNode(node);
            }

        }

        if (!addingNewStationsToMapTableView.getItems().isEmpty()){

            rightSplitPane_upper_stackPane.getChildren().get(1).toFront();

        } else {
            rightSplitPane_upper_stackPane.getChildren().get(0).toFront();
        }


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

        // -------------------- map ------------------

    }

    private void initMenuBar(){

        // MENU
        menuUser = new Menu("Пользователь");
        menuStation = new Menu("Станция");
        menuReport = new Menu("Отчет");

        MenuItem menuItemLogout = new MenuItem("Выйти из кабинета");
        menuItemLogout.setOnAction(this::menuItemLogoutClicked);

        menuUser.getItems().add(menuItemLogout);

        MenuItem menuItemAddNewStation = new MenuItem("Добавить новую станцию");
        menuItemAddNewStation.setOnAction(x -> menuItemAddNewStationClicked());

        MenuItem menuItemMoveExistingStation = new MenuItem("Переместить станцию");
        menuItemMoveExistingStation.setOnAction(this::menuItemMoveExistingStationClicked);

        MenuItem menuItemChangeStationInfo = new MenuItem("Изменить информацию о станции");
        menuItemChangeStationInfo.setOnAction(this::menuItemChangeStationInfoClicked);

        menuStation.getItems().addAll(menuItemMoveExistingStation, menuItemChangeStationInfo, menuItemAddNewStation);

        MenuItem menuItemHTMLReport = new MenuItem("Отчет в формате HTML");
        menuItemHTMLReport.setOnAction(x -> menuItemGenerateHTMLReportClicked());

        MenuItem menuItemChartBuilder = new MenuItem("Построить график");
        menuItemChartBuilder.setOnAction(x -> menuItemBuildChartClicked());

        menuReport.getItems().addAll(menuItemHTMLReport, menuItemChartBuilder);
        menuBar.getMenus().addAll(menuUser, menuStation, menuReport);
    }

    private void menuItemBuildChartClicked() {

        Parent root;
        try {
            Stage stage = new Stage();
            stageList.add(stage);

            root = FXMLLoader.load(getClass().getResource("/fxml/chartPane.fxml"));

            stage.setTitle("Построение графиков");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("An error while opening ChartPane");
        }

    }

    private void menuItemLogoutClicked(ActionEvent actionEvent) {

        boolean logout = WeatherAPIWorker.getInstance().logout();

        if (logout){

            System.out.println(stageList.size());

            for (Stage stage : stageList) {

                stage.close();
            }

            Parent root;
            try {
                Stage stage = new Stage();

                root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));

                stage.setTitle("Авторизация");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();

                // Hide this current window (if this is what you want)
                ((Stage)mainSplitPane.getScene().getWindow()).close();

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("An error while closing MainFrame and opening LoginFrame");
            }

        }
    }


    private void selectedStationChanged() {

        allRecordsFromStationTable.populate(weatherDataStore.getRecordsForOneStation(currentSelectedStationsID.get()));
        populateStationsDetailedInformation(weatherDataStore.getStationInfo(currentSelectedStationsID.get()));

    }

    private void populateStationsDetailedInformation(Station station){

        stationInfoPane.addInfoRow(station);

        rightMenu_SplitPane_lowerAnchorPane.getChildren().clear();
        rightMenu_SplitPane_lowerAnchorPane.getChildren().add(stationInfoPane);
    }



    // ------------------------------- MENU BAR METHODS START ---------------------------------------------------------
    private void menuItemAddNewStationClicked() {

        Parent root;
        try {
            Stage stage = new Stage();
            stageList.add(stage);

            root = FXMLLoader.load(getClass().getResource("/fxml/addNewStation.fxml"));
            stage.setTitle("Добавить новую станцию");
            stage.setScene(new Scene(root));

            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("An error while closing AddNewStation frame");
        }

    }

    private void menuItemChangeStationInfoClicked(ActionEvent event) {

        System.out.println("changing station");
    }

    private void menuItemMoveExistingStationClicked(ActionEvent x) {

        interactiveMap.startMovingStationInfoNode();

        // show a hint
    }


    private void menuItemGenerateHTMLReportClicked() {
//        System.out.println("elements are updated - "+Thread..sendInfoToSubscribers());
    }
    // ------------------------------- MENU BAR METHODS END ---------------------------------------------------------


    // -------------------------- Listeners' methods ------------------------------------------------------------------------------
    // changes height of upper and lower tables to fit the panes they are wrapped in
    private void rightMenu_SplitPane_upperAnchorPaneHeightChanged() {

        //rightMenu_lowerPane_allStationsTable.setPrefHeight(rightMenu_SplitPane_lowerAnchorPane.getHeight());
        allRecordsFromStationTable.setPrefHeight(rightMenu_SplitPane_upperAnchorPane.getHeight());
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
        allRecordsFromStationTable.setPrefWidth(mainSplitPane_rightAnchorPane.getWidth());
    }

    private void rooPaneWidthChanged(){

    }

    private void rooPaneHeightChanged(){

    }
    // -------------------------- Listeners' methods ------------------------------------------------------------------------------


}