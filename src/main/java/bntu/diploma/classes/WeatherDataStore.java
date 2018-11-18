package bntu.diploma.classes;

import bntu.diploma.domain.Station;
import bntu.diploma.domain.WeatherInfo;
import bntu.diploma.utils.DataUtils;
import bntu.diploma.utils.OblastEnum;
import com.google.gson.*;

import java.util.*;


/**
 * This class is supposed to be responsible for handling data received
 * form the server might be redundant
 * <p>
 * <p>
 * <p>
 * - comparator for weatherInfo
 */
public class WeatherDataStore {

    // the key is a station's id
    // value is an instance of the station
    private Map<Long, Station> stationMap;

    // key is the id of a station
    // value is all records from that station
    private Map<Long, Set<WeatherInfo>> stationsWeatherInfoMap;

    private WeatherAPIWorker weatherAPIWorker;

    private volatile static WeatherDataStore weatherDataStoreInstance;

    private static final WeatherInfo emptyWeatherInfo = new WeatherInfo();

    public static WeatherDataStore getInstance() {
        if (weatherDataStoreInstance == null) {
            weatherDataStoreInstance = new WeatherDataStore();
            return weatherDataStoreInstance;
        }
        return weatherDataStoreInstance;
    }


    private WeatherDataStore() {

        //stationMap = new HashMap<>();
        weatherAPIWorker = WeatherAPIWorker.getInstance();
        //stationsWeatherInfoMap = new HashMap<>();

        downloadAndParseData();
    }

    private boolean downloadAndParseData() {

        String result = null;
        try {

            result = weatherAPIWorker.getAllWeatherData(weatherAPIWorker.getSessionToken());
            this.stationsWeatherInfoMap = parseWeatherInfoData(result);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error while parsing a json string with WeatherInfo's data");
            return false;
        }

        result = null;
        try {

            result = weatherAPIWorker.getAllStationsInfo(weatherAPIWorker.getSessionToken());
            stationMap = parseStationsInfoData(result);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error while parsing a json string with Stations' data");
            return false;
        }


//        stationMap.values().forEach((station -> {
//
//            System.out.println(station.getStationsId()+" - "+station.getCurrentBatteryLevel());
//
//        }));

        return true;
    }

    private Map<Long, Set<WeatherInfo>> parseWeatherInfoData(String dataAsJson) {

        System.out.println("weather info data = " + dataAsJson);

        Map<Long, Set<WeatherInfo>> map = new HashMap<>();

        JsonParser parser = new JsonParser();
        JsonObject rootElement = parser.parse(dataAsJson).getAsJsonObject();

        // retrieve data of each station
        for (OblastEnum oblastEnum : OblastEnum.values()) {

            JsonArray stationsOfOblast = rootElement.getAsJsonArray(oblastEnum.getOblastName());

            for (JsonElement station : stationsOfOblast.getAsJsonArray()) {

                Long stationId = Long.valueOf(station.getAsJsonObject().get("station_id").toString());
                map.put(stationId, new TreeSet<>());

                station.getAsJsonObject().get("data").getAsJsonArray().forEach((weatherInfoRecord) -> {

                    JsonObject record = weatherInfoRecord.getAsJsonObject();

                    WeatherInfo weatherInfo = new WeatherInfo();
                    weatherInfo.setStation(stationId);
                    weatherInfo.setPressure(Double.valueOf(record.get("pressure").toString().replace("\"", "")));
                    weatherInfo.setTemperature(Double.valueOf(record.get("temperature").toString().replace("\"", "")));
                    weatherInfo.setHumidity(Double.valueOf(record.get("humidity").toString().replace("\"", "")));
                    weatherInfo.setWindSpeed(Double.valueOf(record.get("windSpeed").toString().replace("\"", "")));
                    weatherInfo.setWindDirection(Integer.valueOf(record.get("windDirection").toString().replace("\"", "")));
                    weatherInfo.setDateTime(record.get("dateTime").getAsString().replace("\"", ""));
                    weatherInfo.setBatteryLevel(Integer.valueOf(record.get("batteryLevel").toString().replace("\"", "")));


                    map.get(stationId).add(weatherInfo);

                });
            }
        }

        return map;


    }

    private Map<Long, Station> parseStationsInfoData(String dataAsJson) {

        Map<Long, Station> map = new HashMap<>();

        for (JsonElement stationJson : new JsonParser().parse(dataAsJson).getAsJsonArray()) {

            JsonObject object = (JsonObject) new JsonParser().parse(stationJson.toString());

            Station station = new Station();
            station.setStationsId(Long.valueOf(object.get("stationsId").toString().replace("\"", "")));
            station.setOblast(Long.valueOf(object.get("oblast").getAsJsonObject().get("oblastsId").toString().replace("\"", "")));
            station.setNearestTown(object.get("nearestTown").toString().replace("\"", ""));
            station.setInstallationDate(object.get("installationDate").toString().replace("\"", ""));
            station.setLastInspection(object.get("lastInspection").toString().replace("\"", ""));
            station.setStationLatitude(Double.valueOf(object.get("stationLatitude").toString().replace("\"", "")));
            station.setStationLongitude(Double.valueOf(object.get("stationLongitude").toString().replace("\"", "")));


            if (!object.get("coordinateXOnInteractiveMap").toString().toLowerCase().equals("null"))
                station.setCoordinateXOnInteractiveMap(Double.valueOf(object.get("coordinateXOnInteractiveMap").toString().replace("\"", "")));
            if (!object.get("coordinateYOnInteractiveMap").toString().toLowerCase().equals("null"))
                station.setCoordinateYOnInteractiveMap(Double.valueOf(object.get("coordinateYOnInteractiveMap").toString().replace("\"", "")));

            // make a list of the set, then get first el (it is the latest record and get its battery level)
            try {
                station.setCurrentBatteryLevel(Integer.valueOf(object.get("currentBatteryLevel").toString()));
            } catch (Exception e) {
                station.setCurrentBatteryLevel(-1);
            }

            map.put(Long.valueOf(object.get("stationsId").toString()), station);
        }


        return map;
    }

    // theoretically added info will be showed automatically as i will change existing instance
    public boolean getRecentChanges() {

        try {
            downloadAndParseData();

        } catch (Exception e) {
            System.out.println("Fail while updating weather data");
            e.printStackTrace();
            return false;
        }


        // check if any new station was created
        // this can be done by a separate method (????)
        // if so offer to add its dot to the map


        // to get all recent data i can use station's ids
        // then iterate through the map
        return true;
    }


    public Station getStationInfo(long stationId) {

        return stationMap.get(stationId);
    }

    public WeatherInfo getLastWeatherInfo(long stationId) {

        if (stationsWeatherInfoMap.get(stationId).size() != 0) {

            return new ArrayList<>(stationsWeatherInfoMap.get(stationId)).get(0);

        } else {

            return DataUtils.getEmptyWeatherInfo();
        }
    }

    public List<WeatherInfo> getAllWeatherInfoForStation(long stationId) {

        return new ArrayList<>(stationsWeatherInfoMap.get(stationId));
    }


    public List<WeatherInfo> getOneHundredWeatherInfoRecordsForStation(long stationId) {

        if (stationsWeatherInfoMap.get(stationId).size() < 100)
            return new ArrayList<>(stationsWeatherInfoMap.get(stationId));
        else {

            return new ArrayList<>(stationsWeatherInfoMap.get(stationId)).subList(0, 99);
        }
    }


    public List<Station> getAllStations() {

        return new ArrayList<>(stationMap.values());
    }

    public Map<Long, Set<WeatherInfo>> getStationsWeatherInfoMap() {
        return stationsWeatherInfoMap;
    }
}
