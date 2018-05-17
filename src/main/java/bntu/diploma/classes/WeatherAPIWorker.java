package bntu.diploma.classes;

import bntu.diploma.model.Station;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * This API worker allows to query meteodata from the server.
 * The document which describes available API requests is named
 * "Название раздела из твоего диплома, который описывает АПИ сервера"
 *
 *
 * */
public class WeatherAPIWorker {

    private static WeatherAPIWorker ourInstance;

    private CloseableHttpClient httpClient;

    private String addressOfWeatherAPI = "localhost";
    private static final String HTTP_SCHEME = "http";
    private int port = 8080;
    private String ALL_WEATHER_DATA_RESOURCE_PATH = "weather";
    private String ALL_STATIONS_DATA_RESOURCE_PATH = "station";
    private String ADD_NEW_STATION_RESOURCE_PATH = "add_station";

    private String sessionToken;

    public static WeatherAPIWorker getInstance() {
        if (ourInstance == null){
            ourInstance = new WeatherAPIWorker();
            return ourInstance;
        }

        return ourInstance;
    }

    private WeatherAPIWorker() {
        httpClient = HttpClients.createDefault();
    }

    /**
     *
     * @param token a secret key for authentication
     * @param from - beginning date (format - ddmmyyyy)
     * @param to - ending date (format - ddmmyyyy)
     * @param scale specifies the scale (country, oblast, station)
     *
     *
     * */
    public String getWeatherData(String from, String to, String scale, String token){

        Map<String, String> params = new HashMap<>();
        params.put("key", token);
        params.put("scale", scale);

        // these two params are not necessary
        if (from != null && to != null) {
            params.put("from", from);
            params.put("to", to);
        }

        String result = null;

        try {
            result = executeGetRequest(null,
                    params,
                    ALL_WEATHER_DATA_RESOURCE_PATH);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return result;
    }


    public String getAllWeatherData(String sessionToken){

        // TODO token
        Map<String, String> params = new HashMap<>();
        params.put("key", sessionToken);


        String result = null;

        try {
            result = executeGetRequest(null,
                    params,
                    ALL_WEATHER_DATA_RESOURCE_PATH);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return result;
    }


    public String getAllStationsInfo(String sessionToken){

        // TODO token

        Map<String, String> params = new HashMap<>();
        params.put("key", sessionToken);

        String result = null;

        try {
            result = executeGetRequest(null,
                    params,
                    ALL_STATIONS_DATA_RESOURCE_PATH);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     *
     * @return session token for accessing server API
     *
     * */
    public String singin(String login, String pass){

        // todo !!!!

        sessionToken = null;

        return "whatever";
    }


    public String addNewStation(Station station){

        // TODO

        return null;
    }

    public String changeStationInfo(Long stationID, Station station){

        // TODO

        return null;
    }


    private String executeGetRequest(Map<String, String> headers,
                                     Map<String, String> params,
                                     String path) throws URISyntaxException {

        URIBuilder builder = new URIBuilder();
        builder.setScheme(HTTP_SCHEME);
        builder.setHost(addressOfWeatherAPI);
        builder.setPath(path);
        builder.setPort(port);

        if(params != null){
            for (Map.Entry<String, String> param: params.entrySet()){
                builder.addParameter(param.getKey(), param.getValue());
            }
        }

        HttpGet httpget = new HttpGet(builder.build());

        System.out.println("builder.build() -- "+builder.build());

        if(headers != null){
            for (Map.Entry<String, String> header: headers.entrySet()){
                httpget.setHeader(header.getKey(), header.getValue());
            }
        }

        CloseableHttpResponse response = null;
        String result = null;

        try {
            response = httpClient.execute(httpget);
            result = EntityUtils.toString(response.getEntity());

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                if (response != null)
                    response.close();

            } catch (IOException e) {
                System.err.println(">>> Have not managed to close CloseableHttpResponse response");
            }
        }

        return result;
    }


    public String getAddressOfWeatherAPI() {
        return addressOfWeatherAPI;
    }

    public void setNewAddressOfWeatherAPI(String addressOfWeatherAPI) {
        this.addressOfWeatherAPI = addressOfWeatherAPI;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
