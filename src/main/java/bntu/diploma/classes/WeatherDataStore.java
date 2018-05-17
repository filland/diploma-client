package bntu.diploma.classes;

import bntu.diploma.model.Station;
import bntu.diploma.model.WeatherInfo;
import bntu.diploma.utils.OblastEnum;
import bntu.diploma.utils.Utils;
import com.google.gson.*;

import java.text.ParseException;
import java.util.*;


/**
 *
 * This class is supposed to be responsible for handling data received
 * form the server might be redundant
 *
 *
 *
 * - comparator for weatherInfo
 *
 *
 * */
public class WeatherDataStore {

    // the key is a station's id
    // value is an instance of the station
    private Map<Long, Station> stationMap;

    // key is the id of a station
    // value is all records from that station
    private Map<Long, Set<WeatherInfo>> stationsWeatherInfoMap;

    private WeatherAPIWorker weatherAPIWorker;

    private static WeatherDataStore weatherDataStoreInstance;

    public static WeatherDataStore getInstance() {
        if (weatherDataStoreInstance == null){
            weatherDataStoreInstance = new WeatherDataStore();
            return weatherDataStoreInstance;
        }

        return weatherDataStoreInstance;
    }


    private WeatherDataStore() {

        stationMap = new HashMap<>();
        weatherAPIWorker = WeatherAPIWorker.getInstance();
        stationsWeatherInfoMap = new HashMap<>();

        downloadAndParseData();
    }

    private boolean downloadAndParseData(){

        // TODO session TOKEN
        String result = null;
        try {
            result = weatherAPIWorker.getAllWeatherData( "222");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try {
            parseWeatherInfoData(result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error while parsing a json string with WeatherInfo's data");
            return false;
        }

        // TODO session TOKEN
        result = null;
        try {
            result = weatherAPIWorker.getAllStationsInfo( "222");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try {
            parseStationsInfoData(result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error while parsing a json string with Stations' data");
            return false;
        }

        return true;
    }

    private void parseWeatherInfoData(String dataAsJson){

        JsonParser parser = new JsonParser();
        JsonObject rootElement = parser.parse(dataAsJson).getAsJsonObject();

        // retrieve data of each station
        for (OblastEnum oblastEnum : OblastEnum.values()) {

            JsonArray stationsOfOblast = rootElement.getAsJsonArray(oblastEnum.name());

            for (JsonElement station : stationsOfOblast.getAsJsonArray()) {

                Long stationId = Long.valueOf(station.getAsJsonObject().get("station_id").toString());
                stationsWeatherInfoMap.put(stationId, new TreeSet<>());

                station.getAsJsonObject().get("data").getAsJsonArray().forEach((weatherInfoRecord) -> {

                    JsonObject record = weatherInfoRecord.getAsJsonObject();

                    WeatherInfo weatherInfo = new WeatherInfo();
                    weatherInfo.setPressure(Double.valueOf(record.get("pressure").toString()));
                    weatherInfo.setTemperature(Double.valueOf(record.get("temperature").toString()));
                    weatherInfo.setHumidity(Double.valueOf(record.get("humidity").toString()));
                    weatherInfo.setWindSpeed(Double.valueOf(record.get("windSpeed").toString()));
                    weatherInfo.setWindDirection(Integer.valueOf(record.get("windDirection").toString()));
                    weatherInfo.setDateTime(record.get("dateTime").getAsString());
                    weatherInfo.setBatteryLevel(Integer.valueOf(record.get("batteryLevel").toString()));

                    stationsWeatherInfoMap.get(stationId).add(weatherInfo);

                });
            }
        }


       /* stationsWeatherInfoMap.forEach((aLong, weatherInfos) -> {


            System.out.println("station id - "+aLong);
           weatherInfos.forEach(weatherInfo -> {

               System.out.println(weatherInfo.getDateTime());

           });


        });*/
    }

    private void parseStationsInfoData(String dataAsJson){

        for (JsonElement stationJson : new JsonParser().parse(dataAsJson).getAsJsonArray()) {

            JsonObject object = stationJson.getAsJsonObject();

            Station station = new Station();
            station.setStationsId(Long.valueOf(object.get("stationsId").toString()));
            station.setOblast(Long.valueOf(object.get("oblast").getAsJsonObject().get("oblastsId").toString()));
            station.setNearestTown(object.get("nearestTown").toString());
            station.setInstallationDate(object.get("installationDate").toString());
            station.setLastInspection(object.get("lastInspection").toString());
            station.setStationLatitude(Double.valueOf(object.get("stationLatitude").toString()));
            station.setStationLongitude(Double.valueOf(object.get("stationLongitude").toString()));

            // make a list of the set, then get first el (it is the latest record and get its battery level)
            try {
                station.setCurrentBatteryLevel(new ArrayList<>(stationsWeatherInfoMap.
                                            get(Long.valueOf(object.get("stationsId").toString()))).
                                            get(0).getBatteryLevel());
            } catch (Exception e) {
                station.setCurrentBatteryLevel(-1);
            }

            stationMap.put(Long.valueOf(object.get("stationsId").toString()), station);
        }


       /* stationMap.forEach((aLong, station) ->{

            System.out.println("station id - "+aLong);
            System.out.println("town+id - "+station.getNearestTown()+station.getStationsId());
            System.out.println();

        });*/
    }

    public Station getStationInfo(long stationId){

        return stationMap.get(stationId);
    }

    public WeatherInfo getLastWeatherInfo(long stationId){

        return new ArrayList<>(stationsWeatherInfoMap.get(stationId)).get(stationsWeatherInfoMap.get(stationId).size()-1);
    }


    public List<WeatherInfo> getAllWeatherInfoForStation(long stationId){

        return new ArrayList<>(stationsWeatherInfoMap.get(stationId));
    }


    public List<WeatherInfo> getOneHundredWeatherInfoRecordsForStation(long stationId){

        if (stationsWeatherInfoMap.get(stationId).size() < 100)
            return new ArrayList<>(stationsWeatherInfoMap.get(stationId));
        else {

            return new ArrayList<>(stationsWeatherInfoMap.get(stationId)).subList(0, 99);
        }
    }



}
