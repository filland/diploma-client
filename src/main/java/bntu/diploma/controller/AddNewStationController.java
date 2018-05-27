package bntu.diploma.controller;

import bntu.diploma.classes.WeatherAPIWorker;
import bntu.diploma.classes.WeatherDataStore;
import bntu.diploma.model.Station;
import bntu.diploma.utils.OblastEnum;
import bntu.diploma.utils.OblastUtils;
import bntu.diploma.utils.SecureTokenGenerator;
import bntu.diploma.utils.Utils;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class AddNewStationController {
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
    public void initialize(){


        List<String> oblasts = new ArrayList<>();

        for (OblastEnum oblastEnum : OblastEnum.values()) {
            oblasts.add(oblastEnum.name());
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

        Station station = new Station();

        // TODO add check to all fields
        station.setNearestTown(nearestTownField.getText());

        // should be 10 symbols in length
        station.setStationUniqueKey(secretKeyField.getText());

        station.setOblast(OblastEnum.valueOf(oblastComboBox.getSelectionModel().getSelectedItem()).getId());

        station.setInstallationDate(installationDatePicker.getValue().toString());

        station.setLastInspection(lastInspectionPicker.getValue().toString());

        try {
            station.setStationLongitude(Double.valueOf(longitudeField.getText()));
        } catch (NumberFormatException e) {
            System.out.println("Error while setting Longitude");
            e.printStackTrace();
        }

        try {
            station.setStationLatitude(Double.valueOf(latitudeField.getText()));
        } catch (NumberFormatException e) {
            System.out.println("Error while setting Latitude");
            e.printStackTrace();
        }

        try {
            if (!batteryLevelField.getText().trim().isEmpty() &&
                    Integer.parseInt(batteryLevelField.getText()) > 1 &&
                    Integer.parseInt(batteryLevelField.getText()) <100)
                station.setCurrentBatteryLevel(Integer.parseInt(batteryLevelField.getText()));
            else
                station.setCurrentBatteryLevel(-1);
        } catch (NumberFormatException e) {
            System.out.println("Error while setting current battery level");
            e.printStackTrace();
        }

        try {
            WeatherAPIWorker.getInstance().addNewStation(station);
            resultLabel.setText("Новая станция добавлена успешно");
            resultLabel.setVisible(true);
        } catch (Exception e) {
            resultLabel.setTextFill(Color.RED);
            resultLabel.setText("Новая станция НЕ добавлена");
            resultLabel.setVisible(true);
            e.printStackTrace();
        }

        //station.setCoordinateXOnInteractiveMap();
        //station.setCoordinateYOnInteractiveMap();

        System.out.println(new Gson().toJson(station));

    }
}
