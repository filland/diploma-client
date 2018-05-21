package bntu.diploma.classes;

import bntu.diploma.model.Station;
import bntu.diploma.utils.OblastUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;



/**
 *
 * This class is meant to show system information about
 * a station in a convenient way.
 *
 *
 * */
public class StationInfoPane extends GridPane {


    private int rowCounter = 0;

    private Station currentStation;

    public StationInfoPane() {

        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 35, 25, 35));
    }

    public void addInfoRow(Station station){

        this.getChildren().clear();

        currentStation = station;

        addInfoRow("Station's name: ", station.getNearestTown()+"_"+String.valueOf(station.getStationsId()));
        addInfoRow("Station's id: ", String.valueOf(station.getStationsId()));
        addInfoRow("Oblast: ", OblastUtils.getOblastTextName(station.getOblast()));
        addInfoRow("Installation date: ", station.getInstallationDate());
        addInfoRow("Last inspection date: ", station.getLastInspection());
        addInfoRow("Nearest town: ", station.getNearestTown());
        addInfoRow("Longitude: ", String.valueOf(station.getStationLongitude()));
        addInfoRow("Latitude: ", String.valueOf(station.getStationLatitude()));
        addInfoRow("Battery: ", String.valueOf(station.getCurrentBatteryLevel()));

        // make counter equal to zero to add the next station's info starting from the beginning
        rowCounter = 0;
    }

    private void addInfoRow(String rowTitle, String rowContent){

        Label title = new Label(rowTitle);
        Label content = new Label(rowContent);

        this.add(title, 0, rowCounter);
        this.add(content, 1, rowCounter);

        rowCounter++;

    }

    public Station getCurrentStation() {
        return currentStation;
    }

}
