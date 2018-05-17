package bntu.diploma.classes;

import bntu.diploma.model.Station;
import bntu.diploma.model.WeatherInfo;
import bntu.diploma.utils.DataUtils;
import bntu.diploma.utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Set;
import java.util.TreeSet;

public class Test {

    static GridPane stationInfoGrid;

    public static void main(String[] args) {


        WeatherDataStore weatherDataStore = WeatherDataStore.getInstance();

        //weatherDataStore.downloadAndParseData();

        //System.out.println(weatherDataStore.getLastWeatherInfo(2).getDateTime());

        for (WeatherInfo weatherInfo : weatherDataStore.getOneHundredWeatherInfoRecordsForStation(5)) {

            System.out.println(weatherInfo.getDateTime());

        }


        /*Set<WeatherInfo> weatherInfoSet = new TreeSet<>(DataUtils.getListOfWeatherInfo());


        for (WeatherInfo weatherInfo : weatherInfoSet) {

            System.out.println(weatherInfo.getDateTime());

        }*/

    }


    public void start(Stage primaryStage) throws Exception {

        stationInfoGrid = new GridPane();

        Station station2 = new Station();
        station2.setStationsId(1L);
        station2.setOblast(4L);
        station2.setInstallationDate("setInstallationDate");
        station2.setLastInspection("setLastInspection");
        station2.setNearestTown("setNearestTown");
        station2.setStationLongitude(1.1);
        station2.setStationLatitude(2.2);
        //station2.setStationUniqueKey("unique key");

        Label stationsIdLabel = new Label(String.valueOf(station2.getStationsId()));
        Label oblastLabel = new Label(String.valueOf(station2.getOblast()));
        Label installationDateLabel = new Label(String.valueOf(station2.getInstallationDate()));
        Label lastInspectionLabel = new Label(String.valueOf(station2.getLastInspection()));
        Label nearestTownLabel = new Label(String.valueOf(station2.getNearestTown()));
        Label stationLongitudeLabel = new Label(String.valueOf(station2.getStationLongitude()));
        Label stationLatitudeLabel = new Label(String.valueOf(station2.getStationLatitude()));
        //Label stationUniqueKeyLabel = new Label(String.valueOf(station2.getStationUniqueKey()));

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
        //stationInfoGrid.add(stationUniqueKeyLabel, 1, 7);

        stationInfoGrid.add(new Label("id: "), 0, 1);
        stationInfoGrid.add(new Label("oblast: "), 0, 2);
        stationInfoGrid.add(new Label("install date: "), 0, 3);
        stationInfoGrid.add(new Label("last inspect: "), 0, 4);
        stationInfoGrid.add(new Label("nearest town: "), 0, 5);
        stationInfoGrid.add(new Label("longitude: "), 0, 6);
        stationInfoGrid.add(new Label("latitude: "), 0, 7);

        Scene scene = new Scene(stationInfoGrid);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
