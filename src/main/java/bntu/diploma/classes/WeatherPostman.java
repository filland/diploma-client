package bntu.diploma.classes;

import bntu.diploma.node.AllRecordsTableView;
import bntu.diploma.node.StationInfoPane;
import bntu.diploma.node.map.InteractiveMap;
import bntu.diploma.model.Station;

import java.util.ArrayList;
import java.util.List;

public class WeatherPostman {


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

        if (WeatherAPIWorker.getInstance().isServerAvailable()){

            weatherDataStore.getRecentChanges();

            try {
                for (Object subscriber : subscribers) {

                    if (subscriber instanceof InteractiveMap) {

                        InteractiveMap interactiveMap = (InteractiveMap) subscriber;

                        for (Station station : weatherDataStore.getAllStations()) {

                            interactiveMap.updateNode(station.getStationsId(),
                                    weatherDataStore.getLastWeatherInfo(station.getStationsId()));
                        }

                    }

                    if (subscriber instanceof StationInfoPane) {

                        StationInfoPane.getInstance().addInfoRow(weatherDataStore.getStationInfo(GlobalContext.getInstance().getCurrentStationID()));

                    }

                    if (subscriber instanceof AllRecordsTableView) {

                        AllRecordsTableView allRecordsTableView = (AllRecordsTableView) subscriber;
                        allRecordsTableView.populate(weatherDataStore.getAllWeatherInfoForStation(
                                GlobalContext.getInstance().getCurrentStationID()
                        ));

                    }

                }
            } catch (Exception e) {

                e.printStackTrace();
                return false;
            }

            return true;

        }

        return false;

    }


}
