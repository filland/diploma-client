package bntu.diploma.classes;


import javafx.beans.property.SimpleLongProperty;

/**
 *
 * This class storing all params which are necessary
 * for elements to talk to each other
 *
 * This class makes it simple to access any necessary param wherever
 *
 *
 * */
public class Dispatcher {

    private static Dispatcher ourInstance = new Dispatcher();

    private SimpleLongProperty currentStation;

    private Dispatcher() {
        currentStation = new SimpleLongProperty(1);
    }


    public static Dispatcher getInstance() {
        return ourInstance;
    }


    public void setCurrentStation(SimpleLongProperty currentStation) {
        this.currentStation = currentStation;
    }

    public long getCurrentStationID() {
        return currentStation.get();
    }

    public void setCurrentStationID(long currentStation) {
        this.currentStation.set(currentStation);
    }

}
