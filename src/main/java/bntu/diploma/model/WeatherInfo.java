package bntu.diploma.model;

import bntu.diploma.model.Station;

public class WeatherInfo {

    private Double temperature;
    private Double pressure;
    private Double humidity;
    private Double windSpeed;
    private Integer windDirection;
    private Integer batteryLevel;

    private Long weatherInfoId;
    private Station station;
    private String dateTime;

    public WeatherInfo() {  }

    public WeatherInfo(String dateTime, double temperature, double pressure, double humidity, double windSpeed, int windDirection, int batteryLevel) {

        this.dateTime = dateTime;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.batteryLevel = batteryLevel;
    }


    public Long getWeatherInfoId() {
        return weatherInfoId;
    }

    public void setWeatherInfoId(Long weatherInfoId) {
        this.weatherInfoId = weatherInfoId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
}
