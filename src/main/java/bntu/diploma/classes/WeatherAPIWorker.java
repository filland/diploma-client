package bntu.diploma.classes;

import bntu.diploma.model.Station;
import bntu.diploma.utils.AdvancedEncryptionStandard;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
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

    private static volatile WeatherAPIWorker ourInstance;
    private CloseableHttpClient httpClient;

    private String addressOfWeatherAPI = "localhost";
    private static final String HTTP_SCHEME = "http";
    private int port = 8080;
    private final String ALL_WEATHER_DATA_RESOURCE_PATH = "all_weather";
    private final String ALL_STATIONS_DATA_RESOURCE_PATH = "all_stations";
    private final String ADD_NEW_STATION_RESOURCE_PATH = "add_station";
    private final String LOGIN_RESOURCE_PATH = "login";
    private final String LOGOUT_RESOURCE_PATH = "logout";
    private final String CHANGE_STATION_RESOURCE_PATH = "change_station";
    private final String AVAILABLE_RESOURCE_PATH = "available";


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
        params.put("token", token);
        params.put("scale", scale);

        // these two params are not necessary
        if (from != null && to != null) {
            params.put("from", from);
            params.put("to", to);
        }

        CloseableHttpResponse result = null;

        try {
            result = executeGetRequest(null,
                    params,
                    ALL_WEATHER_DATA_RESOURCE_PATH);
            return EntityUtils.toString(result.getEntity());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *
     * This method is meant to check if the server is available
     *
     * */
    public boolean isServerAvailable(){

        try {

            CloseableHttpResponse request = executeGetRequest(null, null, AVAILABLE_RESOURCE_PATH);

            if (request.getStatusLine().getStatusCode() == 200)
                return true;

        } catch (Exception e) {
           // e.printStackTrace();
        }

        return false;
    }

    public String getAllWeatherData(String sessionToken){

        Map<String, String> params = new HashMap<>();
        params.put("token", sessionToken);

        CloseableHttpResponse result = null;

        try {
            result = executeGetRequest(null,
                    params,
                    ALL_WEATHER_DATA_RESOURCE_PATH);
            return EntityUtils.toString(result.getEntity());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getAllStationsInfo(String sessionToken){

        Map<String, String> params = new HashMap<>();
        params.put("token", sessionToken);

        CloseableHttpResponse result = null;

        try {
            result = executeGetRequest(null,
                    params,
                    ALL_STATIONS_DATA_RESOURCE_PATH);
            return EntityUtils.toString(result.getEntity());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return session token for accessing server API
     *
     * */
    public boolean login(String userId, String secretKey){

        Map<String, String> header = new HashMap<>();

        try {
            header.put("id", userId);

            CloseableHttpResponse response = executePostRequest(AdvancedEncryptionStandard.encrypt(secretKey.getBytes(), AdvancedEncryptionStandard.thisStationEncryptionKey.getBytes()),
                                                            header,
                                                        null,
                                                            LOGIN_RESOURCE_PATH);

            if (response.getStatusLine().getStatusCode() == 200){

                sessionToken = response.getFirstHeader("key").getValue();
                System.out.println(sessionToken);
                return true;
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("fail while login");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error with secretKey while login");
            return false;
        }

        return false;
    }


    public boolean logout (){

       if (sessionToken != null){

           Map<String, String> headers = new HashMap<>();
           headers.put("token", sessionToken);

           CloseableHttpResponse result = null;

           try {
               result = executePostRequest(headers, null, LOGOUT_RESOURCE_PATH);
           } catch (URISyntaxException e) {
               e.printStackTrace();
               System.out.println("fail while logout");
               return false;
           }

           System.out.println("logout status - "+result.getStatusLine().getStatusCode());

           if (result.getStatusLine().getStatusCode() == 200){

               sessionToken = null;
               return true;
           } else
               return false;

       } else
           System.err.println("Error while logout. No session was established");
           return false;
    }


    public boolean addNewStation(Station station){

        if (sessionToken != null){

            Map<String, String> headers = new HashMap<>();
            headers.put("token", sessionToken);
            //headers.put("station_id", String.valueOf(station.getStationsId()));

            CloseableHttpResponse result = null;

            try {
                result = executePostRequest(station,headers, null, ADD_NEW_STATION_RESOURCE_PATH);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                System.out.println("fail while adding new station");
                return false;
            }

            System.out.println("adding station result's status - "+result.getStatusLine().getStatusCode());
            //System.out.println();

            return result.getStatusLine().getStatusCode() == 200;

        } else
            return false;
    }

    public boolean changeStationInfo(Station station){

        if (sessionToken != null){

            Map<String, String> headers = new HashMap<>();
            headers.put("token", sessionToken);
            headers.put("station_id", String.valueOf(station.getStationsId()));

            CloseableHttpResponse result = null;

            try {
                result = executePutRequest(station,headers, null, CHANGE_STATION_RESOURCE_PATH);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                System.out.println("fail while changing station's info");
                return false;
            }

            System.out.println("change station result's status - "+result.getStatusLine().getStatusCode());
            //System.out.println();

            return result.getStatusLine().getStatusCode() == 200;

        } else
            return false;
    }


    private CloseableHttpResponse executeGetRequest(Map<String, String> headers,
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

        //System.out.println("builder.build() -- "+builder.build());

        if(headers != null){
            for (Map.Entry<String, String> header: headers.entrySet()){
                httpget.setHeader(header.getKey(), header.getValue());
            }
        }

        CloseableHttpResponse response = null;
        //String result = null;

        try {
            response = httpClient.execute(httpget);
            //result = EntityUtils.toString(response.getEntity());

        } catch (IOException e) {

            System.out.println("Fail to execute http get request");
            //e.printStackTrace();

        }
//        finally {
//
//            try {
//                if (response != null)
//                    response.close();
//
//            } catch (IOException e) {
//                System.err.println(">>> Have not managed to close CloseableHttpResponse response");
//            }
//        }

        return response;
    }



    public CloseableHttpResponse executePostRequest(HttpEntity entity,
                                     Map<String, String> headers,
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


        HttpPost post = new HttpPost(builder.build());

        //System.out.println("post url -- "+builder.build());

        if(headers != null){
            for (Map.Entry<String, String> header: headers.entrySet()){
                post.setHeader(header.getKey(), header.getValue());
            }
        }

        if (entity != null){

            post.setEntity(entity);
        }


        //String result = null;

        try(CloseableHttpResponse response = httpClient.execute(post)) {

            //result = EntityUtils.toString(response.getEntity());

            return response;

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(">>> Have not managed to close CloseableHttpResponse response");

        }

        return null;
    }


    public CloseableHttpResponse executePostRequest(byte [] bytes,
                                    Map<String, String> headers,
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


        HttpPost post = new HttpPost(builder.build());

        //System.out.println("post url -- "+builder.build());

        if(headers != null){
            for (Map.Entry<String, String> header: headers.entrySet()){
                post.setHeader(header.getKey(), header.getValue());
            }
        }

        if (bytes != null){

            post.setEntity(new ByteArrayEntity(bytes));
        }

        CloseableHttpResponse response = null;
//        String result = null;

        try {
            response = httpClient.execute(post);
//            result = EntityUtils.toString(response.getEntity());

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

        return response;
    }


    public CloseableHttpResponse executePostRequest(Map<String, String> headers,
                                                    Map<String, String> params,
                                                    String path) throws URISyntaxException {

        URIBuilder builder = new URIBuilder();
        builder.setScheme(HTTP_SCHEME);
        builder.setHost(addressOfWeatherAPI);
        builder.setPath(path);
        builder.setPort(port);

        //System.out.println("params - "+params);
        //System.out.println("post url -- "+builder.build());

        if(params != null){
            for (Map.Entry<String, String> param: params.entrySet()){
                builder.addParameter(param.getKey(), param.getValue());
            }
        }


        HttpPost post = new HttpPost(builder.build());

        //System.out.println("post url -- "+builder.build());

        if(headers != null){
            for (Map.Entry<String, String> header: headers.entrySet()){
                post.setHeader(header.getKey(), header.getValue());
            }
        }

        CloseableHttpResponse response = null;
//        String result = null;

        try {
            response = httpClient.execute(post);

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

        return response;
    }


    public CloseableHttpResponse executePostRequest(Station station,
                                                    Map<String, String> headers,
                                                    Map<String, String> params,
                                                    String path) throws URISyntaxException {

        URIBuilder builder = new URIBuilder();
        builder.setScheme(HTTP_SCHEME);
        builder.setHost(addressOfWeatherAPI);
        builder.setPath(path);
        builder.setPort(port);

        //System.out.println("params - "+params);
        //System.out.println("post url -- "+builder.build());

        if(params != null){
            for (Map.Entry<String, String> param: params.entrySet()){
                builder.addParameter(param.getKey(), param.getValue());
            }
        }


        HttpPost post = new HttpPost(builder.build());

        //System.out.println("post url -- "+builder.build());

        if(headers != null){
            for (Map.Entry<String, String> header: headers.entrySet()){
                post.setHeader(header.getKey(), header.getValue());
            }
        }


        if (station != null){

            Gson g = new Gson();
            String stationAsJson = g.toJson(station);

            post.setEntity(EntityBuilder.create().setText(stationAsJson.replace("\\\"", "")).build());
        }

        CloseableHttpResponse response = null;
//        String result = null;

        try {
            response = httpClient.execute(post);

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

        return response;
    }


    public CloseableHttpResponse executePutRequest(Station station,
                                                   Map<String, String> headers,
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

        HttpPut put = new HttpPut(builder.build());

        //System.out.println("put url -- "+builder.build());

        if(headers != null){
            for (Map.Entry<String, String> header: headers.entrySet()){
                put.setHeader(header.getKey(), header.getValue());
            }
        }


        if (station != null){

            Gson g = new Gson();
            String stationAsJson = g.toJson(station);

            //System.out.println("stationAsJson - "+stationAsJson);

            put.setEntity(EntityBuilder.create().setText(stationAsJson.replace("\\\"", "")).build());
        }

        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(put);

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                if (response != null)
                    response.close();

            } catch (IOException e) {
                System.err.println(">>> Have not managed to close CloseableHttpResponse response in executePutRequest()");
            }
        }

        return response;
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
