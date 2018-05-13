package bntu.diploma.controller;

import bntu.diploma.beans.StationInfo;
import bntu.diploma.beans.StationWeatherInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Date;
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



    @FXML
    private MenuBar menuBar;
    @FXML
    private Group group;
    @FXML
    private Canvas canvas;
    @FXML
    private SplitPane rightSplitPane;
    @FXML
    private AnchorPane rightMenu_SplitPane_upperAnchorPane;
    @FXML
    private AnchorPane rightMenu_SplitPane_lowerAnchorPane;


    // -----------------------------------------------------------------------------------------------
    private GraphicsContext graphicsContext;
    private Image imageRect;
    private ImageView imageView;

    private TableView<StationWeatherInfo> rightMenu_upperPane_detailedInfoTable;
    private TableView<StationInfo> rightMenu_lowerPane_allStationsTable;

    private Menu menuStation;
    private Menu menuReport;

    // ------------- CONSTANTS -----------------------------------------
    private final String LINK_TO_MAP = "map.png";
    // ------------- CONSTANTS -----------------------------------------


    public Controller() {

        // RIGHT UPPER PANE
        rightMenu_upperPane_detailedInfoTable = new TableView<>();

        // RIGHT LOWER PANE
        rightMenu_lowerPane_allStationsTable = new TableView<>();
    }

    @FXML
    public void initialize(){

        initMenuBar();
        initMap();
        initListeners();

        List<StationWeatherInfo> sysList = new ArrayList<>();
        sysList.add(new StationWeatherInfo(1.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new StationWeatherInfo(2.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new StationWeatherInfo(3.1, 2.2, 10.1, 10.11, 12));
        sysList.add(new StationWeatherInfo(4.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new StationWeatherInfo(5.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new StationWeatherInfo(6.1, 2.2, 10.1, 10.11, 12));
        sysList.add(new StationWeatherInfo(7.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new StationWeatherInfo(8.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new StationWeatherInfo(9.1, 2.2, 10.1, 10.11, 12));
        sysList.add(new StationWeatherInfo(10.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new StationWeatherInfo(11.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new StationWeatherInfo(12.1, 2.2, 10.1, 10.11, 12));
        sysList.add(new StationWeatherInfo(13.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new StationWeatherInfo(12342.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new StationWeatherInfo(14444442.1, 2.2, 10.1, 10.11, 12));
        sysList.add(new StationWeatherInfo(1333333332.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new StationWeatherInfo(12.1, 2.2, 10.1, 10.1, 12));
        sysList.add(new StationWeatherInfo(12.1, 2.2, 10.1, 10.11, 12));
        populateRightMenu_upperPane_detailedInfoTable(sysList);

        List<StationInfo> sysList2 = new ArrayList<>();
        sysList2.add(new StationInfo("asdf", new Date(2017, 2, 15),"asdf", "123"));
        sysList2.add(new StationInfo("asdf", new Date(2017, 3, 15),"asdf","asdf"));
        sysList2.add(new StationInfo("asdf", new Date(2017, 4, 15),"asdf","asdf"));
        sysList2.add(new StationInfo("asdf", new Date(2017, 5, 15),"asdf", "123"));
        sysList2.add(new StationInfo("asdf", new Date(2017, 6, 15),"asdf","asdf"));
        sysList2.add(new StationInfo("asdf", new Date(2017, 7, 15),"asdf","asdf"));
        sysList2.add(new StationInfo("asdf", new Date(2017, 8, 15),"asdf", "123"));
        populateRightMenu_lowerPane_allStationsTable(sysList2);


        // resize all elements
        rightMenu_SplitPane_upperAnchorPaneHeightChanged();
        mainSplitPane_leftAnchorPaneResized();
        rightAnchorPaneResized();
    }

    private void initMap(){

        graphicsContext = canvas.getGraphicsContext2D();

        imageRect = new Image(LINK_TO_MAP);
        imageView = new ImageView(imageRect);

        imageView.setPreserveRatio(true);
        group.getChildren().add(imageView);

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
        imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, imageClicked);
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

    private void menuItemExitClicked() {
        //graphicsContext.setFill(Color.RED);
        //graphicsContext.fillRect(100, 100, 100, 100);

        Circle circle1 = new Circle( 300, 300, 50);
        circle1.setStroke(Color.ORANGE);
        circle1.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.5));
        circle1.setOnMouseClicked(c -> circle1.setFill(Color.GREEN));

        group.getChildren().add(circle1);
        circle1.toFront();
    }



    private void populateRightMenu_upperPane_detailedInfoTable(List<StationWeatherInfo> list){

        // make columns to take all available space
        rightMenu_upperPane_detailedInfoTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<StationWeatherInfo, String> temperature = new TableColumn<>("temp");
        temperature.setCellValueFactory(
                new PropertyValueFactory<>("temperature"));

        TableColumn<StationWeatherInfo, String> humidity = new TableColumn<>("humid");
        humidity.setCellValueFactory(
                new PropertyValueFactory<>("humidity"));

        TableColumn<StationWeatherInfo, String> pressure = new TableColumn<>("pres");
        pressure.setCellValueFactory(
                new PropertyValueFactory<>("pressure"));

        TableColumn<StationWeatherInfo, String> wind_speed = new TableColumn<>("w_speed");
        wind_speed.setCellValueFactory(
                new PropertyValueFactory<>("wind_speed"));

        TableColumn<StationWeatherInfo, String> wind_direction = new TableColumn<>("w_dir");
        wind_direction.setCellValueFactory(
                new PropertyValueFactory<>("wind_direction"));

        ObservableList<StationWeatherInfo> data = FXCollections.observableArrayList(list);

        // adding headers
        rightMenu_upperPane_detailedInfoTable.getColumns().addAll(temperature, humidity, pressure, wind_speed, wind_direction);
        // adding data
        rightMenu_upperPane_detailedInfoTable.setItems(data);

        rightMenu_SplitPane_upperAnchorPane.getChildren().add(rightMenu_upperPane_detailedInfoTable);
    }


    private void populateRightMenu_lowerPane_allStationsTable(List<StationInfo> list){

        // TODO how to add big GridPane to a ScrollPane

        // make columns to take all available space
        rightMenu_lowerPane_allStationsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TableColumn<StationInfo, String> name = new TableColumn<>("name");
        name.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        TableColumn<StationInfo, String> nearestTown = new TableColumn<>("nearest");
        nearestTown.setCellValueFactory(
                new PropertyValueFactory<>("nearestTown"));
        TableColumn<StationInfo, String> creationDate = new TableColumn<>("date");
        creationDate.setCellValueFactory(
                new PropertyValueFactory<>("creationDate"));
        TableColumn<StationInfo, String> coordinates = new TableColumn<>("coord");
        coordinates.setCellValueFactory(
                new PropertyValueFactory<>("coordinates"));

        ObservableList<StationInfo> data = FXCollections.observableArrayList(list);

        // adding headers
        rightMenu_lowerPane_allStationsTable.getColumns().setAll(name, nearestTown, creationDate, coordinates);
        // adding data
        rightMenu_lowerPane_allStationsTable.setItems(data);

        rightMenu_SplitPane_lowerAnchorPane.getChildren().add(rightMenu_lowerPane_allStationsTable);

    }


    // -------------------------- Listeners' methods ------------------------------------------------------------------------------
    // changes height of upper and lower tables to fit the panes they are wrapped in
    private void rightMenu_SplitPane_upperAnchorPaneHeightChanged() {

        rightMenu_lowerPane_allStationsTable.setPrefHeight(rightMenu_SplitPane_lowerAnchorPane.getHeight());
        rightMenu_upperPane_detailedInfoTable.setPrefHeight(rightMenu_SplitPane_upperAnchorPane.getHeight());
    }


    // changes the size of canvas to fit the anchor it is wrapped in
    private void mainSplitPane_leftAnchorPaneResized() {
        canvas.setHeight(mainSplitPane_leftAnchorPane.getHeight());
        canvas.setWidth(mainSplitPane_leftAnchorPane.getWidth());

        imageView.setFitWidth(mainSplitPane_leftAnchorPane.getWidth());
        imageView.setFitHeight(mainSplitPane_leftAnchorPane.getHeight());
    }

    // changes width of upped and lower tables to fit the panes they are wrapped in
    private void rightAnchorPaneResized() {
        rightMenu_lowerPane_allStationsTable.setPrefWidth(mainSplitPane_rightAnchorPane.getWidth());
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
