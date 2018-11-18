package bntu.diploma.controller;

import bntu.diploma.classes.WeatherAPIWorker;
import bntu.diploma.classes.WeatherDataStore;
import bntu.diploma.domain.Station;
import bntu.diploma.utils.OblastEnum;
import bntu.diploma.utils.SecureTokenGenerator;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddNewStationController {
    @FXML
    private AnchorPane rootAnchorePane;

    @FXML
    private TextField nearestTownField;
    @FXML
    private ComboBox<String> oblastComboBox;
    @FXML
    private TextField secretKeyField;
    @FXML
    private Button generateSecretKeyButton;
    @FXML
    private DatePicker installationDatePicker;
    @FXML
    private DatePicker lastInspectionPicker;
    @FXML
    private TextField latitudeField;
    @FXML
    private TextField longitudeField;
    @FXML
    private TextField batteryLevelField;
    @FXML
    private Button placeStationButton;
    @FXML
    private Label resultLabel;
    @FXML
    private Button addStationButton;

    @FXML
    public void initialize() {


        List<String> oblasts = new ArrayList<>();

        for (OblastEnum oblastEnum : OblastEnum.values()) {
            oblasts.add(oblastEnum.getOblastName());
        }

        oblastComboBox.getItems().addAll(oblasts);

    }


    public void placeStationButtonClicked(ActionEvent actionEvent) {
        // TODO

        List<Station> allStations = WeatherDataStore.getInstance().getAllStations();
        Collections.sort(allStations);
        System.out.println(allStations.get(0).getStationsId());
    }

    public void generateSecretKeyButtonClicked(ActionEvent actionEvent) {
        secretKeyField.setText(SecureTokenGenerator.nextToken().substring(0, 10));
    }

    public void oblastComboBoxClicked(ActionEvent actionEvent) {
        //oblastComboBox.getSelectionModel().getSelectedItem()
    }

    public void addStationButtonClicked(ActionEvent actionEvent) {

        resultLabel.setText("");
        resultLabel.setTextFill(Color.BLACK);

        final String defaultColor = "-fx-border-color:null";
        final String errorColor = "-fx-border-color:red";

        Station station = new Station();

        boolean hasError = false;

        if (!nearestTownField.getText().isEmpty()) {

            nearestTownField.setStyle(defaultColor);
            station.setNearestTown(nearestTownField.getText());

        } else {

            hasError = true;
            nearestTownField.setStyle(errorColor);
        }

        // should be 10 symbols in length
        if (!secretKeyField.getText().isEmpty()) {

            secretKeyField.setStyle(defaultColor);
            station.setStationUniqueKey(secretKeyField.getText());

        } else {

            hasError = true;
            secretKeyField.setStyle(errorColor);

        }

        if (!oblastComboBox.getSelectionModel().isEmpty()) {

            oblastComboBox.setStyle(defaultColor);
            station.setOblast(OblastEnum.getByName(oblastComboBox.getSelectionModel().getSelectedItem()).getId());

        } else {

            hasError = true;
            oblastComboBox.setStyle(errorColor);
        }

        if (installationDatePicker.getValue() != null) {

            installationDatePicker.setStyle(defaultColor);
            station.setInstallationDate(installationDatePicker.getValue().toString());

        } else {

            hasError = true;
            installationDatePicker.setStyle(errorColor);

        }

        if (lastInspectionPicker.getValue() != null){

            station.setLastInspection(lastInspectionPicker.getValue().toString());

        } else {

            if (station.getInstallationDate() != null){

                station.setLastInspection(station.getInstallationDate());
            }
        }

        try {

            station.setStationLongitude(Double.valueOf(longitudeField.getText()));
            longitudeField.setStyle(defaultColor);

        } catch (NumberFormatException e) {

            hasError = true;
            longitudeField.setStyle(errorColor);
            System.out.println("Error while setting Longitude");
//            e.printStackTrace();
        }

        try {

            station.setStationLatitude(Double.valueOf(latitudeField.getText()));
            latitudeField.setStyle(defaultColor);

        } catch (NumberFormatException e) {

            hasError = true;
            latitudeField.setStyle(errorColor);

            System.out.println("Error while setting Latitude");
//            e.printStackTrace();
        }

        try {

            if (!batteryLevelField.getText().trim().isEmpty()) {

                batteryLevelField.setStyle(defaultColor);
                station.setCurrentBatteryLevel(Integer.parseInt(batteryLevelField.getText()));

            } else {

                hasError = true;
                batteryLevelField.setStyle(errorColor);

            }

        } catch (NumberFormatException e) {

            hasError = true;
            batteryLevelField.setStyle(errorColor);
            System.out.println("Error while setting current battery level");
//            e.printStackTrace();
        }

        if (hasError){

            resultLabel.setText("Заполните поля выделенные красным");
            resultLabel.setVisible(true);
            return;

        } else {

            resultLabel.setText("");
            resultLabel.setVisible(false);
        }

        try {

            WeatherAPIWorker.getInstance().addNewStation(station);
            resultLabel.setText("Новая станция добавлена успешно");
            resultLabel.setVisible(true);

        } catch (Exception e) {
//            resultLabel.setTextFill(Color.RED);
            resultLabel.setText("Новая станция нe добавлена");
            resultLabel.setVisible(true);
//            e.printStackTrace();
        }

        //station.setCoordinateXOnInteractiveMap();
        //station.setCoordinateYOnInteractiveMap();

        System.out.println(new Gson().toJson(station));

    }
}
