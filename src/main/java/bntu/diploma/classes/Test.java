package bntu.diploma.classes;

public class Test {

    public static void main(String[] args) {

        WeatherAPIWorker weatherAPIWorker = WeatherAPIWorker.getInstance();
        weatherAPIWorker.setNewAddressOfWeatherAPI("http://bot.whatismyipaddress.com");

        System.out.println("Response body - " + weatherAPIWorker.getWeatherData("20180422",
                "20180425",
                "all",
                "qweqweqwe1"));
    }

}
