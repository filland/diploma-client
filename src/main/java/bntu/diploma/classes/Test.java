package bntu.diploma.classes;

import bntu.diploma.model.Station;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Test {

    static GridPane stationInfoGrid;

    public static void main(String[] args) {


//        WeatherDataStore weatherDataStore = WeatherDataStore.getInstance();

        //weatherDataStore.downloadAndParseData();

        //System.out.println(weatherDataStore.getLastWeatherInfo(2).getDateTime());

//        for (WeatherInfo weatherInfo : weatherDataStore.getOneHundredWeatherInfoRecordsForStation(5)) {
//
//            System.out.println(weatherInfo.getDateTime());
//
//        }



        /*Set<WeatherInfo> weatherInfoSet = new TreeSet<>(DataUtils.getListOfWeatherInfo());


        for (WeatherInfo weatherInfo : weatherInfoSet) {

            System.out.println(weatherInfo.getDateTime());

        }*/


        WeatherAPIWorker apiWorker = WeatherAPIWorker.getInstance();


        boolean loginResult = apiWorker.login(String.valueOf(666), String.valueOf(1));
        System.out.println("login result  - "+loginResult);

//        boolean logoutResult = apiWorker.logout();
//        System.out.println("logout result - "+logoutResult);





        try {

            CloseableHttpResponse hello = apiWorker.executePostRequest(null, null, "hello");
            System.out.println(hello.getEntity());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }




//            Station station = new Station();
//
//            station.setStationsId(2L);
//            station.setCurrentBatteryLevel(40);
//
//            boolean b = apiWorker.changeStationInfo(station);
//
//            System.out.println("");
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
