package bntu.diploma.todelete;

import bntu.diploma.utils.SimpleDateProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.Date;


public class StationInfo {

    private StringProperty name;
    private StringProperty nearestTown;
    private SimpleDateProperty creationDate;
    private StringProperty coordinates;

    public StationInfo(String name, Date creationDate, String coordinates, String nearestTown) {
        this.name = new SimpleStringProperty(name);
        this.creationDate = new SimpleDateProperty(creationDate);
        this.coordinates = new SimpleStringProperty(coordinates);
        this.nearestTown = new SimpleStringProperty(nearestTown);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCreationDate() {
        return creationDate.get();
    }

    public void setCreationDate(String creationDate) {
        this.creationDate.set(creationDate);
    }

    public String getCoordinates() {
        return coordinates.get();
    }

    public void setCoordinates(String coordinates) {
        this.coordinates.set(coordinates);
    }

    public String getNearestTown() {
        return nearestTown.get();
    }

    public void setNearestTown(String nearestTown) {
        this.nearestTown.set(nearestTown);
    }
}
