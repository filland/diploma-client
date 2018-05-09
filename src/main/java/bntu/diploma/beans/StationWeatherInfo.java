package bntu.diploma.beans;

import javafx.beans.property.*;

// TODO replace this class with the one from Server project
public class StationWeatherInfo {

    public enum Direction{

        SOUTH, NORTH, WEST, EAST
    }

    private DoubleProperty temperature;
    private DoubleProperty humidity;
    private DoubleProperty pressure;
    private DoubleProperty wind_speed;

    // number of degrees starting from NORTH clockwise
    private DoubleProperty wind_direction;

    public StationWeatherInfo(double temp, double humid, double wind_speed, double pressure, double wind_direction) {
        this.temperature = new SimpleDoubleProperty(temp);
        this.humidity = new SimpleDoubleProperty(humid);
        this.wind_speed = new SimpleDoubleProperty(wind_speed);
        this.pressure = new SimpleDoubleProperty(pressure);
        this.wind_direction = new SimpleDoubleProperty(wind_direction);
    }

    public double getTemperature() {
        return temperature.get();
    }

    public void setTemperature(double temperature) {
        this.temperature.set(temperature);
    }

    public double getHumidity() {
        return humidity.get();
    }

    public void setHumidity(double humidity) {
        this.humidity.set(humidity);
    }

    public double getPressure() {
        return pressure.get();
    }


    public void setPressure(double pressure) {
        this.pressure.set(pressure);
    }

    public double getWind_speed() {
        return wind_speed.get();
    }


    public void setWind_speed(double wind_speed) {
        this.wind_speed.set(wind_speed);
    }

    public double getWind_direction() {
        return wind_direction.get();
    }

    public void setWind_direction(double wind_direction) {
        this.wind_direction.set(wind_direction);
    }


}
