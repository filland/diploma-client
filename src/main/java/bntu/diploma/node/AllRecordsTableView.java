package bntu.diploma.node;

import bntu.diploma.domain.WeatherInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class AllRecordsTableView extends TableView {


    private static AllRecordsTableView allRecordsTableView;

    private ObservableList<WeatherInfo> allRecordsFromStationList;

    private long currentStationId;

    public static AllRecordsTableView getInstance() {

        if (allRecordsTableView == null) {
            allRecordsTableView = new AllRecordsTableView();
            return allRecordsTableView;
        }
        return allRecordsTableView;

    }


    private AllRecordsTableView() {


        getColumns().clear();

        // make columns to take all available space
        this.setColumnResizePolicy(javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<WeatherInfo, String> temperature = new TableColumn<>("темп");
        temperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));

        TableColumn<WeatherInfo, String> pressure = new TableColumn<>("давл");
        pressure.setCellValueFactory(new PropertyValueFactory<>("pressure"));

        TableColumn<WeatherInfo, String> humidity = new TableColumn<>("влажн");
        humidity.setCellValueFactory(new PropertyValueFactory<>("humidity"));


        TableColumn<WeatherInfo, String> wind_speed = new TableColumn<>("в_скор");
        wind_speed.setPrefWidth(40);
        wind_speed.setCellValueFactory(new PropertyValueFactory<>("windSpeed"));

        TableColumn<WeatherInfo, String> wind_direction = new TableColumn<>("в_напр");
        wind_direction.setPrefWidth(30);
        wind_direction.setCellValueFactory(new PropertyValueFactory<>("windDirection"));

        TableColumn<WeatherInfo, String> date_time = new TableColumn<>("дат_вр");
        date_time.setMinWidth(80);
        date_time.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        // adding headers
        this.getColumns().addAll(temperature, humidity, pressure, wind_speed, wind_direction, date_time);

    }



    public void populate(List<WeatherInfo> list){

         this.getItems().clear();

        allRecordsFromStationList = FXCollections.observableArrayList(list);

        // adding allRecordsTableView
        this.setItems(allRecordsFromStationList);

    }


    public void setCurrentStationId(long stationId){

        this.currentStationId = stationId;
    }

    public long getCurrentStationId() {
        return currentStationId;
    }

    public ArrayList getAllRecordsFromStationList() {
        return new ArrayList(allRecordsFromStationList);
    }
}
