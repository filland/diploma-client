package bntu.diploma.classes;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * This API worker allows to query meteodata from the server.
 * The document which describes available API calls is named
 * "Название раздела из твоего диплома, который описывает АПИ сервера"
 *
 *
 * */
public class WeatherAPIWorker {

    private static WeatherAPIWorker ourInstance;

    private CloseableHttpClient httpClient;
    private String addressOfWeatherAPI = "http://site.byyy/weather";

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
     * @param key a secret key for authentication
     * @param from - beginning date (format - ddmmyyyy)
     * @param to - ending date (format - ddmmyyyy)
     *
     * */
    public String getWeatherData(String from, String to, String scale, String key){

        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        params.put("from", from);
        params.put("to", to);
        params.put("scale", scale);

        String result = null;

        try {
            result = executeGetRequest(null,
                    params,
                    addressOfWeatherAPI);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return result;
    }


    public String singin(String login, String pass){

        // todo !!!!

        return null;
    }


    private String executeGetRequest(Map<String, String> headers,
                                     Map<String, String> params,
                                     String url) throws URISyntaxException {

        URIBuilder builder = new URIBuilder(url);

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
}