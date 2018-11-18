package bntu.diploma.node;

import bntu.diploma.domain.Station;
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

    private static StationInfoPane ourInstance;

    public static StationInfoPane getInstance(){
        if (ourInstance == null){
            ourInstance = new StationInfoPane();
            return ourInstance;
        }

        return ourInstance;
    }


    private StationInfoPane() {

        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 35, 25, 35));
    }

    public void addInfoRow(Station station){

        this.getChildren().clear();

        currentStation = station;

        addInfoRow("Имя станции: ", station.getNearestTown()+"_"+String.valueOf(station.getStationsId()));
        addInfoRow("ID станции: ", String.valueOf(station.getStationsId()));
        addInfoRow("Область: ", OblastUtils.getOblastTextName(station.getOblast()));
        addInfoRow("Дата установки: ", station.getInstallationDate());
        addInfoRow("Дата последней инспекции: ", station.getLastInspection());
        addInfoRow("Ближайший город: ", station.getNearestTown());
        addInfoRow("Долгота: ", String.valueOf(station.getStationLongitude()));
        addInfoRow("Широта: ", String.valueOf(station.getStationLatitude()));
        addInfoRow("Батарея: ", String.valueOf(station.getCurrentBatteryLevel()));

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
