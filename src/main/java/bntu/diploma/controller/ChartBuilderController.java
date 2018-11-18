package bntu.diploma.controller;

import bntu.diploma.classes.WeatherDataStore;
import bntu.diploma.domain.Station;
import bntu.diploma.report.WeatherChartBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChartBuilderController {


    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ComboBox<String> stationNameComboBox;
    @FXML
    private ComboBox<String> parameterComboBox;
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private Button buildChartButton;
    @FXML
    private Button saveButton;

    @FXML
    private Label stationLabel;
    @FXML
    private Label paramLabel;
    @FXML
    private Label fromDateLabel;
    @FXML
    private Label toDateLabel;

    private WeatherChartBuilder chartBuilder;
    private LineChart chart;


    @FXML
    public void initialize() {

        chartBuilder = new WeatherChartBuilder();

        List<String> stationsNames = new ArrayList<>();
        for (Station station : WeatherDataStore.getInstance().getAllStations()) {
            stationsNames.add(station.getNearestTown() + "_" + station.getStationsId());
        }
        stationNameComboBox.getItems().addAll(stationsNames);


        List<String> params = new ArrayList<>();
        for (WeatherChartBuilder.WeatherParameter weatherParameter : WeatherChartBuilder.WeatherParameter.values()) {
            if (weatherParameter.name().toLowerCase().equals("datetime"))
                continue;
            params.add(weatherParameter.name().toLowerCase());
        }
        parameterComboBox.getItems().addAll(params);

    }

    public void buildChartButtonClicked(ActionEvent actionEvent) {
        if (!stationNameComboBox.getSelectionModel().isEmpty() && !parameterComboBox.getSelectionModel().isEmpty()) {

            // reset style of buttons to default
            stationLabel.setTextFill(Color.BLACK);
            stationLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
            paramLabel.setTextFill(Color.BLACK);
            paramLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));

            if (toDatePicker.getValue() != null && fromDatePicker.getValue() != null) {

                System.out.println(toDatePicker.getValue());
                System.out.println(fromDatePicker.getValue());

            } else {

                String var[] = stationNameComboBox.getSelectionModel().getSelectedItem().split("_");
                int stationID = Integer.parseInt(var[1]);

                System.out.println(toDatePicker.getValue());

                chart = chartBuilder.buildChart(WeatherDataStore.getInstance().getAllWeatherInfoForStation(stationID),
                        WeatherChartBuilder.WeatherParameter.valueOf(parameterComboBox.getSelectionModel().getSelectedItem().toUpperCase()));
                chart.setPadding(new Insets(15, 15, 15, 15));

                mainBorderPane.setCenter(chart);

                saveButton.setDisable(false);
            }


        } else {

            if (stationNameComboBox.getSelectionModel().isEmpty()) {
                stationLabel.setTextFill(Color.RED);
                stationLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

            }
            if (parameterComboBox.getSelectionModel().isEmpty()) {
                paramLabel.setTextFill(Color.RED);
                paramLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

            }
        }


    }

    public void saveButtonClicked(ActionEvent actionEvent) {

        if (chart != null) {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("image.png");
            fileChooser.setTitle("Указание пути для сохранения графика");
            File file = fileChooser.showSaveDialog(mainBorderPane.getScene().getWindow());

            if (file != null) {

                if (file.getAbsolutePath().toLowerCase().endsWith(".png"))
                    chartBuilder.saveAsImage(file.getAbsolutePath(), chart);
                else
                    chartBuilder.saveAsImage(file.getAbsolutePath() + ".png", chart);

            }

        }

    }
}
