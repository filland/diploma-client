package bntu.diploma.controller;

import bntu.diploma.Main;
import bntu.diploma.beans.StationInfo;
import bntu.diploma.beans.StationWeatherInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 *
 * TODO:
 *
 *
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
    // --------------------- STRUCTURE -----------------------



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

    // -----------------------------------------------------------------------------------------------

    private TableView<StationWeatherInfo> rightMenu_upperPane_detailedInfoTable;
    private TableView<StationInfo> rightMenu_lowerPane_allStationsTable;

    private Menu menuFile;

    private double rootPaneWidth = 0;

    public Controller(){

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
        rootPane.widthProperty().addListener((w) -> resizeComponents());
        rootPane.heightProperty().addListener(h -> resizeComponents());

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
        MenuItem menuItemPrint = new MenuItem("Print");
        MenuItem menuItemExit = new MenuItem("Exit");
        menuFile.getItems().addAll(menuItemExportData, menuItemPrint, menuItemExit);
        menuBar.getMenus().add(menuFile);

    }


    public void populateRightMenu_upperPane_detailedInfoTable(List<StationWeatherInfo> list){

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

        rightMenu_upperScrollPane.setContent(rightMenu_upperPane_detailedInfoTable);
    }


    public void populateRightMenu_lowerPane_allStationsTable(List<StationInfo> list){

        // make columns to take all available space
        rightMenu_lowerPane_allStationsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


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

        rightMenu_lowerPane.getChildren().add(rightMenu_lowerPane_allStationsTable);

    }


    private void resizeComponents(){

        System.out.println("mainSplitPane.getDividers() size -- " + mainSplitPane.getDividers().size());

    }




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
