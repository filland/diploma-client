package bntu.diploma.controller;

import bntu.diploma.model.Station;
import bntu.diploma.model.StationInfoNode;
import bntu.diploma.model.WeatherInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *
 * TODO: ...
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
    // --------------------- STRUCTURE -----------------------


    //@FXML
    //private BorderPane mainSplitPane_leftAnchorPane_borderPane;

    @FXML
    private MenuBar menuBar;
    @FXML
    private SplitPane rightSplitPane;
    @FXML
    private AnchorPane rightMenu_SplitPane_upperAnchorPane;

    @FXML
    private AnchorPane rightMenu_SplitPane_lowerAnchorPane;
    @FXML
    private GridPane stationInfoGrid;


    // -----------------------------------------------------------------------------------------------
    private GraphicsContext graphicsContext;
    private Image imageRect;
    private ImageView imageView;

    private TableView<WeatherInfo> rightMenu_upperPane_detailedInfoTable;
    //private TableView<WeatherInfo> rightMenu_lowerPane_allStationsTable;

    private Menu menuStation;
    private Menu menuReport;

    // ------------- CONSTANTS -----------------------------------------
    private final String LINK_TO_MAP = "map.png";
    // ------------- CONSTANTS -----------------------------------------


    public Controller() {

        // RIGHT UPPER PANE
        rightMenu_upperPane_detailedInfoTable = new TableView<>();

        // RIGHT LOWER PANE
        //rightMenu_lowerPane_allStationsTable = new TableView<>();
    }

    @FXML
    public void initialize(){

        initMenuBar();
        initMap();
        initListeners();

        List<WeatherInfo> sysList = new ArrayList<>();
        sysList.add(new WeatherInfo("14052018",1.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new WeatherInfo("14052018",1.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new WeatherInfo("14052018",1.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new WeatherInfo("14052018",1.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new WeatherInfo("14052018",1.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new WeatherInfo("14052018",1.1, 2.2, 10.1, 10.1, 12));sysList.add(new WeatherInfo("14052018",1.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new WeatherInfo("14052018",1.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new WeatherInfo("14052018",1.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new WeatherInfo("14052018",1.1, 2.2, 10.1, 10.1, 12));
        populateRightMenu_upperPane_detailedInfoTable(sysList);


        Station station2 = new Station();
        station2.setStationsId(1L);
        station2.setOblast(4L);
        station2.setInstallationDate("setInstallationDate");
        station2.setLastInspection("setLastInspection");
        station2.setNearestTown("setNearestTown");
        station2.setStationLongitude(1.1);
        station2.setStationLatitude(2.2);
        populateRightMenu_lowerPane_allStationsTable(station2);


        // resize all elements
        rightMenu_SplitPane_upperAnchorPaneHeightChanged();
        mainSplitPane_leftAnchorPaneResized();
        rightAnchorPaneResized();
    }

    private void initMap(){

        StationInfoPane stationInfoPane = new StationInfoPane(mainSplitPane_leftAnchorPane);

        StationInfoNode infoNode = new StationInfoNode(50, 50, "1");
        infoNode.setStationParam("temp", "1");
        infoNode.setStationParam("temp2", "25.5");
        infoNode.setStationParam("temp3", "25.5");

        StationInfoNode infoNode2 = new StationInfoNode(250 , 50, "2");
        infoNode2.setStationParam("temp", "2");
        infoNode2.setStationParam("temp2", "25.5");
        infoNode2.setStationParam("temp3", "25.5");

        stationInfoPane.addStationInfoNode(infoNode);
        stationInfoPane.addStationInfoNode(infoNode2);

        mainSplitPane_leftAnchorPane.getChildren().add(stationInfoPane);

    }

    private void initListeners(){

        rootPane.widthProperty().addListener((w) -> rooPaneWidthChanged());
        rootPane.heightProperty().addListener(h -> rooPaneHeightChanged());

        mainSplitPane_leftAnchorPane.widthProperty().addListener(w -> mainSplitPane_leftAnchorPaneResized());
        mainSplitPane_leftAnchorPane.heightProperty().addListener(h -> mainSplitPane_leftAnchorPaneResized());

        mainSplitPane_rightAnchorPane.widthProperty().addListener(w -> rightAnchorPaneResized());
        //mainSplitPane_rightAnchorPane.heightProperty().addListener(h -> rightAnchorPaneResized());

        rightMenu_SplitPane_upperAnchorPane.heightProperty().addListener(h -> rightMenu_SplitPane_upperAnchorPaneHeightChanged());

        // -------------------- map ------------------
        //imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, imageClicked);
        // -------------------- map ------------------

    }

    private EventHandler<MouseEvent> imageClicked = event -> {

        // when left button pressed and released
        if (event.getButton() == MouseButton.PRIMARY){

            System.out.println("X: "+event.getSceneX()+"\n"+
                    "Y: "+ event.getSceneY());
        }
    };


    private void initMenuBar(){

        // MENU
        menuStation = new Menu("Станция");
        menuReport = new Menu("Отчет");

        MenuItem menuItemAddNewStation = new MenuItem("Добавить новую станцию");
        menuStation.getItems().addAll(menuItemAddNewStation);
        menuBar.getMenus().add(menuStation);

        MenuItem menuItemHTMLReport = new MenuItem("Отчет в формате HTML");
        menuReport.getItems().addAll(menuItemHTMLReport);
        menuBar.getMenus().add(menuReport);

        menuItemAddNewStation.setOnAction(x -> menuItemExportDataClicked());
        menuItemHTMLReport.setOnAction(x -> menuItemHTMLReportClicked());
    }

    private void menuItemExportDataClicked() {
        System.out.println("menuItemExportDataClicked");
    }

    private void menuItemHTMLReportClicked() {
        System.out.println("menuItemHTMLReportClicked");
    }

    private void populateRightMenu_upperPane_detailedInfoTable(List<WeatherInfo> list){

        // make columns to take all available space
        rightMenu_upperPane_detailedInfoTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<WeatherInfo, String> temperature = new TableColumn<>("temp");
        temperature.setCellValueFactory(
                new PropertyValueFactory<>("temperature"));

        TableColumn<WeatherInfo, String> humidity = new TableColumn<>("humid");
        humidity.setCellValueFactory(
                new PropertyValueFactory<>("humidity"));

        TableColumn<WeatherInfo, String> pressure = new TableColumn<>("pres");
        pressure.setCellValueFactory(
                new PropertyValueFactory<>("pressure"));

        TableColumn<WeatherInfo, String> wind_speed = new TableColumn<>("w_speed");
        wind_speed.setCellValueFactory(
                new PropertyValueFactory<>("windSpeed"));

        TableColumn<WeatherInfo, String> wind_direction = new TableColumn<>("w_dir");
        wind_direction.setCellValueFactory(
                new PropertyValueFactory<>("windDirection"));

        ObservableList<WeatherInfo> data = FXCollections.observableArrayList(list);

        // adding headers
        rightMenu_upperPane_detailedInfoTable.getColumns().addAll(temperature, humidity, pressure, wind_speed, wind_direction);
        // adding data
        rightMenu_upperPane_detailedInfoTable.setItems(data);

        rightMenu_SplitPane_upperAnchorPane.getChildren().add(rightMenu_upperPane_detailedInfoTable);
    }


    private void populateRightMenu_lowerPane_allStationsTable(Station station){

        // TODO how to add big GridPane to a ScrollPane

        Label stationsIdLabel = new Label(String.valueOf(station.getStationsId()));
        Label oblastLabel = new Label(String.valueOf(station.getOblast()));
        Label installationDateLabel = new Label(String.valueOf(station.getInstallationDate()));
        Label lastInspectionLabel = new Label(String.valueOf(station.getLastInspection()));
        Label nearestTownLabel = new Label(String.valueOf(station.getNearestTown()));
        Label stationLongitudeLabel = new Label(String.valueOf(station.getStationLongitude()));
        Label stationLatitudeLabel = new Label(String.valueOf(station.getStationLatitude()));

        stationInfoGrid.setAlignment(Pos.CENTER);
        stationInfoGrid.setHgap(10);
        stationInfoGrid.setVgap(10);
        stationInfoGrid.setPadding(new Insets(25, 25, 25, 25));

        stationInfoGrid.add(stationsIdLabel, 1, 1);
        stationInfoGrid.add(oblastLabel, 1, 2);
        stationInfoGrid.add(installationDateLabel, 1, 3);
        stationInfoGrid.add(lastInspectionLabel, 1, 4);
        stationInfoGrid.add(nearestTownLabel, 1, 5);
        stationInfoGrid.add(stationLongitudeLabel, 1, 6);
        stationInfoGrid.add(stationLatitudeLabel, 1, 7);

        stationInfoGrid.add(new Label("id: "), 0, 1);
        stationInfoGrid.add(new Label("oblast: "), 0, 2);
        stationInfoGrid.add(new Label("install date: "), 0, 3);
        stationInfoGrid.add(new Label("last inspect: "), 0, 4);
        stationInfoGrid.add(new Label("nearest town: "), 0, 5);
        stationInfoGrid.add(new Label("longitude: "), 0, 6);
        stationInfoGrid.add(new Label("latitude: "), 0, 7);
        //stationInfoGrid.add(new Label("unique"), 0, 1);


        //rightMenu_SplitPane_lowerAnchorPane.getChildren().add(stationInfoGrid);
    }


    // -------------------------- Listeners' methods ------------------------------------------------------------------------------
    // changes height of upper and lower tables to fit the panes they are wrapped in
    private void rightMenu_SplitPane_upperAnchorPaneHeightChanged() {

        //rightMenu_lowerPane_allStationsTable.setPrefHeight(rightMenu_SplitPane_lowerAnchorPane.getHeight());
        rightMenu_upperPane_detailedInfoTable.setPrefHeight(rightMenu_SplitPane_upperAnchorPane.getHeight());
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
        rightMenu_upperPane_detailedInfoTable.setPrefWidth(mainSplitPane_rightAnchorPane.getWidth());
    }

    private void rooPaneWidthChanged(){

    }

    private void rooPaneHeightChanged(){



    }
    // -------------------------- Listeners' methods ------------------------------------------------------------------------------



    // -------------------------------------- LOGIN -----------------------------------------------------------------------------------
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private CheckBox asAdminCheckbox;
    @FXML
    private Hyperlink needHelpLink;
    public void loginButtonPressed(ActionEvent actionEvent) {

        if(asAdminCheckbox.isSelected()){

            usernameField.setText("login as admin");
            rootBorderPane.setDisable(false);
            rootBorderPane.setVisible(true);
            loginPane.setVisible(false);
            loginPane.setDisable(true);

        } else {

            usernameField.setText("login as user");
        }

    }

    public void needHelpLinkClicked(ActionEvent actionEvent) {
        usernameField.setText("needHelpLinkClicked");
    }
    // ---------------------------------- LOGIN -------------------------------------------------------------------------------------

}
