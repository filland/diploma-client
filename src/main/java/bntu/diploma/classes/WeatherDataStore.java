package bntu.diploma.classes;

import bntu.diploma.model.Station;
import bntu.diploma.model.WeatherInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 *
 *
 * - comparator for weatherInfo
 * -
 *
 *
 *
 * */
public class WeatherDataStore {

    private Map<Long, Station> stationMap;
    private Map<Long, List<WeatherInfo>> stationsWeatherInfoMap;

    private WeatherAPIWorker weatherAPIWorker;

    public WeatherDataStore() {

        stationMap = new HashMap<>();
        weatherAPIWorker = WeatherAPIWorker.getInstance();
        stationsWeatherInfoMap = new HashMap<>();
    }

    public WeatherInfo getLastWeatherInfo(long id){

        return null;
    }

    public boolean downloadData(){


        String result = weatherAPIWorker.getWeatherData(null, null, "all", weatherAPIWorker.getSessionToken());


        return false;
    }


    public List<WeatherInfo> getAllWeatherInfoForStation(long id){

        return null;
    }

    public List<WeatherInfo> getOneHundredWeatherInfoRecordsForStation(long id){

        List<WeatherInfo> oneHundredRecords = new ArrayList<>();
        int i= 0;

        for(WeatherInfo w : stationsWeatherInfoMap.get(id)){
            i++;

            if (i == 100) {
                break;
            }

            oneHundredRecords.add(w);
        }

        return oneHundredRecords;
    }





}
