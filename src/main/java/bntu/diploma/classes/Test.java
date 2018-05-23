package bntu.diploma.classes;

import bntu.diploma.report.WeatherChartBuilder;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Test extends Application {

    static GridPane stationInfoGrid;

    public static void main(String[] args) {


        launch();

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


//        WeatherAPIWorker apiWorker = WeatherAPIWorker.getInstance();
//
//
//        boolean loginResult = apiWorker.login(String.valueOf(1), String.valueOf(666));
//        System.out.println("login result  - "+loginResult);
//        System.out.println();
//
//
//        Station station = new Station();
//        station.setStationsId(1L);
//        station.setOblast(OblastEnum.gomelskaya.getId());
//        station.setNearestTown("Gomel");
//        station.setInstallationDate("yesterday");
//        station.setLastInspection("today");
//        station.setStationLongitude(1.1);
//        station.setStationLatitude(2.2);
//        station.setCurrentBatteryLevel(30);
//
//        if (apiWorker.changeStationInfo(station)){
//            System.out.println("station's info changed");
//        } else
//            System.out.println("station's info was not changed");
//
//        boolean logoutResult = apiWorker.logout();
//        System.out.println("logout result - "+logoutResult);



//        try {
//
//            Map<String, String> map = new HashMap<>();
//            map.put("id", "11303113");
//
//            Map<String, String> params = new HashMap<>();
//            params.put("name", "Oleg");
//
//            Map<String, String> oblast = new HashMap<>();
//            oblast.put("name", "minskaya");
//            oblast.put("id", "2");
//
//
//            CloseableHttpResponse hello = apiWorker.executePostRequest(EntityBuilder.create().setText(new Gson().toJson(oblast)).build(),
//                    map, params, "hello");
//
//            System.out.println("entity - "+hello.getEntity());
//            System.out.println("headers - "+Arrays.toString(hello.getAllHeaders()));
//            System.out.println("what - "+ EntityUtils.toString(hello.getEntity()));
//
//        } catch (URISyntaxException | IOException e) {
//            e.printStackTrace();
//        }


//            Station station = new Station();
//
//            station.setStationsId(2L);
//            station.setCurrentBatteryLevel(40);
//
//            boolean b = apiWorker.changeStationInfo(station);
//
//            System.out.println("");


//        System.out.println("map link - "+ApplicationProperties.prop.getProperty("map"));
//
//
//
//        HttpURLConnection connection = null;
//        try {
//            URL url = new URL("http://localhost:8080/available");
//            connection = (HttpURLConnection)url.openConnection();
//            connection.connect();
//            int httpStatusCode = connection.getResponseCode();
//
//            System.out.println("status code - "+httpStatusCode);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }





    }



    public void start(Stage primaryStage) throws Exception {

//        stationInfoGrid = new GridPane();
//
//        Station station2 = new Station();
//        station2.setStationsId(1L);
//        station2.setOblast(4L);
//        station2.setInstallationDate("setInstallationDate");
//        station2.setLastInspection("setLastInspection");
//        station2.setNearestTown("setNearestTown");
//        station2.setStationLongitude(1.1);
//        station2.setStationLatitude(2.2);
//        //station2.setStationUniqueKey("unique key");
//
//        Label stationsIdLabel = new Label(String.valueOf(station2.getStationsId()));
//        Label oblastLabel = new Label(String.valueOf(station2.getOblast()));
//        Label installationDateLabel = new Label(String.valueOf(station2.getInstallationDate()));
//        Label lastInspectionLabel = new Label(String.valueOf(station2.getLastInspection()));
//        Label nearestTownLabel = new Label(String.valueOf(station2.getNearestTown()));
//        Label stationLongitudeLabel = new Label(String.valueOf(station2.getStationLongitude()));
//        Label stationLatitudeLabel = new Label(String.valueOf(station2.getStationLatitude()));
//        //Label stationUniqueKeyLabel = new Label(String.valueOf(station2.getStationUniqueKey()));
//
//        stationInfoGrid.setAlignment(Pos.CENTER);
//        stationInfoGrid.setHgap(10);
//        stationInfoGrid.setVgap(10);
//        stationInfoGrid.setPadding(new Insets(25, 25, 25, 25));
//
//        stationInfoGrid.add(stationsIdLabel, 1, 1);
//        stationInfoGrid.add(oblastLabel, 1, 2);
//        stationInfoGrid.add(installationDateLabel, 1, 3);
//        stationInfoGrid.add(lastInspectionLabel, 1, 4);
//        stationInfoGrid.add(nearestTownLabel, 1, 5);
//        stationInfoGrid.add(stationLongitudeLabel, 1, 6);
//        stationInfoGrid.add(stationLatitudeLabel, 1, 7);
//        //stationInfoGrid.add(stationUniqueKeyLabel, 1, 7);
//
//        stationInfoGrid.add(new Label("id: "), 0, 1);
//        stationInfoGrid.add(new Label("oblast: "), 0, 2);
//        stationInfoGrid.add(new Label("install date: "), 0, 3);
//        stationInfoGrid.add(new Label("last inspect: "), 0, 4);
//        stationInfoGrid.add(new Label("nearest town: "), 0, 5);
//        stationInfoGrid.add(new Label("longitude: "), 0, 6);
//        stationInfoGrid.add(new Label("latitude: "), 0, 7);


//        for (int i = 1900; i < 2050; i++) {
//            System.out.println("INSERT INTO weather_info\n" +
//                    "(\n" +
//                    "`date_time`,\n" +
//                    "`humidity`,\n" +
//                    "`pressure`,\n" +
//                    "`temperature`,\n" +
//                    "`wind_direction`,\n" +
//                    "`wind_speed`,\n" +
//                    "`stations_id`,\n" +
//                    "battery_level)\n" +
//                    "VALUES\n" +
//                    "(\n" +
//                    "'14-05-"+i+" 15:50',\n" +
//                    "50.1,\n" +
//                    "760.5,\n" +
//                    "28.1,\n" +
//                    "260.1,\n" +
//                    "33.2,\n" +
//                    "7,\n" +
//                    "20);\n" +
//                    "\n" +
//                    "INSERT INTO weather_info\n" +
//                    "(\n" +
//                    "`date_time`,\n" +
//                    "`humidity`,\n" +
//                    "`pressure`,\n" +
//                    "`temperature`,\n" +
//                    "`wind_direction`,\n" +
//                    "`wind_speed`,\n" +
//                    "`stations_id`,\n" +
//                    "battery_level)\n" +
//                    "VALUES\n" +
//                    "(\n" +
//                    "'14-05-"+i+" 16:00',\n" +
//                    "77.1,\n" +
//                    "746.5,\n" +
//                    "16.1,\n" +
//                    "45,\n" +
//                    "11.2,\n" +
//                    "5,\n" +
//                    "90);");
//        }



        WeatherAPIWorker apiWorker = WeatherAPIWorker.getInstance();
        boolean loginResult = apiWorker.login(String.valueOf(1), String.valueOf(666));

        WeatherChartBuilder builder = new WeatherChartBuilder();

        LineChart lineChart = builder.
                buildChart(WeatherDataStore.getInstance().getRecordsForOneStation(5L),
                                    WeatherChartBuilder.WeatherParameter.TEMPERATURE);


        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
//        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);
        scrollPane.setContent(lineChart);

        Scene scene = new Scene(scrollPane);

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            lineChart.setMinWidth(primaryStage.getWidth());
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
