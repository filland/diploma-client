package bntu.diploma.classes;

import bntu.diploma.classes.map.InteractiveMap;
import bntu.diploma.model.Station;
import javafx.scene.control.Control;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class WeatherPostman extends Thread {


//    private Thread t;
//    private String threadName;

    private volatile WeatherDataStore weatherDataStore;

    private List subscribers;

    public WeatherPostman() {

        weatherDataStore = WeatherDataStore.getInstance();
        subscribers = new ArrayList();
    }



    public void subscribe(AllRecordsTableView allRecordsTableView) {

        subscribers.add(allRecordsTableView);

    }

    public void subscribe(StationInfoPane stationInfoPane) {
        subscribers.add(stationInfoPane);
    }

    public void subscribe(InteractiveMap interactiveMap) {
        subscribers.add(interactiveMap);
    }

    public boolean sendInfoToSubscribers() {

//        System.out.println("old stat battery - "+weatherDataStore.getStationInfo(Dispatcher.getInstance().getCurrentStationID()).getCurrentBatteryLevel());


        weatherDataStore.getRecentChanges();


        //System.out.println("getDateTime - "+weatherDataStore.getLastWeatherInfo(5).getDateTime());
        //System.out.println("Current station's id - "+Dispatcher.getInstance().getCurrentStationID());

        try {
            for (Object subscriber : subscribers) {

                if (subscriber instanceof InteractiveMap) {

                    System.out.println("InteractiveMap");

                    InteractiveMap interactiveMap = (InteractiveMap) subscriber;

                    for (Station station : weatherDataStore.getAllStations()) {

                        interactiveMap.updateNode(station.getStationsId(),
                                weatherDataStore.getLastWeatherInfo(station.getStationsId()));
                    }

                }

                if (subscriber instanceof StationInfoPane) {

                    System.out.println("StationInfoPane");

//                    System.out.println("new stat battery - "+weatherDataStore.getStationInfo(Dispatcher.getInstance().getCurrentStationID()).getCurrentBatteryLevel());


                    StationInfoPane.getInstance().addInfoRow(weatherDataStore.getStationInfo(Dispatcher.getInstance().getCurrentStationID()));

//                    System.out.println("battery stat in DetailedInfoPane - "+StationInfoPane.getInstance().getCurrentStation().getCurrentBatteryLevel());


                }

                if (subscriber instanceof AllRecordsTableView) {

                    System.out.println("TabableView");

                    AllRecordsTableView allRecordsTableView = (AllRecordsTableView) subscriber;
                    allRecordsTableView.populate(weatherDataStore.getAllWeatherInfoForStation(
                            Dispatcher.getInstance().getCurrentStationID()
                    ));

                }

            }
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }


        return true;
    }


    public void startDelivery(){

        try {

            while (true) {

                Thread.sleep(15 * 1000);

                sendInfoToSubscribers();

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        WeatherAPIWorker.getInstance().login(String.valueOf(1), String.valueOf(666));

        WeatherPostman weatherPostman =new  WeatherPostman();
        weatherPostman.subscribe(AllRecordsTableView.getInstance());
        weatherPostman.subscribe(new InteractiveMap(new AnchorPane()));
        weatherPostman.subscribe(StationInfoPane.getInstance());

        weatherPostman.startDelivery();

    }

}
