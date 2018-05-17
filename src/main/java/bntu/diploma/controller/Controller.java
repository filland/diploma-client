package bntu.diploma.controller;

import bntu.diploma.classes.WeatherDataStore;
import bntu.diploma.classes.map.InteractiveMap;
import bntu.diploma.classes.StationInfoPane;
import bntu.diploma.model.Station;
import bntu.diploma.model.WeatherInfo;
import bntu.diploma.utils.DataUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.List;


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
    //private GraphicsContext graphicsContext;
    //private Image imageRect;
    //private ImageView imageView;

    private TableView<WeatherInfo> rightMenu_upperPane_detailedInfoTable;
    //private TableView<WeatherInfo> rightMenu_lowerPane_allStationsTable;

    private Menu menuStation;
    private Menu menuReport;

    // ------------- CONSTANTS -----------------------------------------
    private final String LINK_TO_MAP = "map.png";
    // ------------- CONSTANTS -----------------------------------------

    private InteractiveMap interactiveMap;
    private StationInfoPane stationInfoPane;
    private WeatherDataStore weatherDataStore;

    public Controller() {

        // RIGHT UPPER PANE
        rightMenu_upperPane_detailedInfoTable = new TableView<>();

        // RIGHT LOWER PANE
        //rightMenu_lowerPane_allStationsTable = new TableView<>();
    }

    @FXML
    public void initialize(){

        weatherDataStore = WeatherDataStore.getInstance();

        initMenuBar();
        initMap();
        initListeners();


//        populateRightMenu_upperPane_detailedInfoTable(DataUtils.getListOfWeatherInfo());
        populateRightMenu_upperPane_detailedInfoTable(weatherDataStore.getAllWeatherInfoForStation(1));
//        populateRightMenu_lowerPane_allStationsTable(DataUtils.getStationInstance());
        populateRightMenu_lowerPane_allStationsTable(weatherDataStore.getStationInfo(1));

        // resize all elements
        rightMenu_SplitPane_upperAnchorPaneHeightChanged();
        mainSplitPane_leftAnchorPaneResized();
        rightAnchorPaneResized();
    }

    private void initMap(){

        interactiveMap = new InteractiveMap(mainSplitPane_leftAnchorPane);

        interactiveMap.addStationInfoNode(DataUtils.getStationInfoNodeInstance());
//        interactiveMap.addStationInfoNode(DataUtils.getStationInfoNodeInstance());
//        interactiveMap.addStationInfoNode(DataUtils.getStationInfoNodeInstance());
//        interactiveMap.addStationInfoNode(DataUtils.getStationInfoNodeInstance());
//        interactiveMap.addStationInfoNode(DataUtils.getStationInfoNodeInstance());

        mainSplitPane_leftAnchorPane.getChildren().add(interactiveMap);

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
        menuItemAddNewStation.setOnAction(x -> menuItemAddNewStationClicked());

        MenuItem menuItemMoveExistingStation = new MenuItem("Переместить станцию");
        menuItemMoveExistingStation.setOnAction(this::menuItemMoveExistingStationClicked);

        MenuItem menuItemChangeStationInfo = new MenuItem("Изменить информацию о станции");
        menuItemChangeStationInfo.setOnAction(this::menuItemChangeStationInfoClicked);

        menuStation.getItems().addAll(menuItemMoveExistingStation, menuItemChangeStationInfo, menuItemAddNewStation);

        MenuItem menuItemHTMLReport = new MenuItem("Отчет в формате HTML");
        menuItemHTMLReport.setOnAction(x -> menuItemHTMLReportClicked());

        menuReport.getItems().addAll(menuItemHTMLReport);
        menuBar.getMenus().addAll(menuStation, menuReport);
    }

    private void menuItemAddNewStationClicked() {
        interactiveMap.addNewStation(null);
    }

    private void menuItemChangeStationInfoClicked(ActionEvent event) {

        stationInfoPane.getCurrentStation();
        System.out.println("changing station");

    }

    private void menuItemMoveExistingStationClicked(ActionEvent x) {

        interactiveMap.startMovingStationInfoNode();

        // show a hint
    }

    private void menuItemExportDataClicked() {

        System.out.println("menuItemExportDataClicked");

    }

    private void menuItemHTMLReportClicked() {
        System.out.println("menuItemHTMLReportClicked");
    }

    private void populateRightMenu_upperPane_detailedInfoTable(List<WeatherInfo> list){

        rightMenu_upperPane_detailedInfoTable.getColumns().clear();

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
        wind_speed.setPrefWidth(40);
        wind_speed.setCellValueFactory(
                new PropertyValueFactory<>("windSpeed"));

        TableColumn<WeatherInfo, String> wind_direction = new TableColumn<>("w_dir");
        wind_direction.setPrefWidth(30);
        wind_direction.setCellValueFactory(
                new PropertyValueFactory<>("windDirection"));

        TableColumn<WeatherInfo, String> date_time = new TableColumn<>("date_time");
        date_time.setMinWidth(70);
        date_time.setCellValueFactory(
                new PropertyValueFactory<>("dateTime"));

        ObservableList<WeatherInfo> data = FXCollections.observableArrayList(list);

        // adding headers
        rightMenu_upperPane_detailedInfoTable.getColumns().addAll(temperature, humidity, pressure, wind_speed, wind_direction, date_time);
        // adding data
        rightMenu_upperPane_detailedInfoTable.setItems(data);

        rightMenu_SplitPane_upperAnchorPane.getChildren().add(rightMenu_upperPane_detailedInfoTable);
    }


    private void populateRightMenu_lowerPane_allStationsTable(Station station){

        stationInfoPane = new StationInfoPane();
        stationInfoPane.addInfoRow(station);

        rightMenu_SplitPane_lowerAnchorPane.getChildren().add(stationInfoPane);

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

    /**
     *
     *
     * THE WAY TO SPLIT CONTROLLERS
     *
     *
     * https://stackoverflow.com/questions/15041760/javafx-open-new-window
     *
     *
     **/

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
