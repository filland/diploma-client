package bntu.diploma.node;

import bntu.diploma.classes.WeatherAPIWorker;
import bntu.diploma.classes.WeatherDataStore;
import bntu.diploma.node.map.InteractiveMap;
import bntu.diploma.domain.Station;
import bntu.diploma.utils.OblastUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class AddingNewStationsToMapTableView extends ListView {

    public static class Row extends HBox {

        private Button addButton;
        private Label label;

        public Row(Station station) {

            this.setId(String.valueOf(station.getStationsId()));
            this.setAlignment(Pos.CENTER_LEFT);


            addButton = new Button("Разместить");
            addButton.setId(String.valueOf(station.getStationsId()));
            HBox.setMargin(addButton, new Insets(0,15,0,5));

            label = new Label(station.getNearestTown()+" "+station.getStationsId()+"_"+ OblastUtils.getOblastTextName(station.getOblast()));
            label.setId(String.valueOf(station.getStationsId()));

            getChildren().addAll(addButton, label);
        }


        public Button getAddButton(){
            return addButton;
        }

        public void coordinatesAddedSuccessfully(boolean b){
            if (b){
                label.setTextFill(Color.BLACK);

            } else {

                label.setTextFill(Color.RED);
            }
        }
    }

    private InteractiveMap interactiveMap;
    private StationInfoPane stationInfoPane;
    private WeatherAPIWorker weatherAPIWorker;

    private List<Row> rows;
    private EventHandler<MouseEvent> addButtonClickHandler;
    private EventHandler<MouseEvent> hBoxClickHandler;

    private long currentStationID;
    private DoubleProperty x;
    private DoubleProperty y;

    // used by main controller for listening if the table with all records from a station should be
    // showed instead of AddingNewStationsToMapTableView
    private BooleanProperty allStationsHaveCoordinates;

    private int specifiedStations = 0;

    public AddingNewStationsToMapTableView(InteractiveMap interactiveMap, StationInfoPane stationInfoPane) {

        this.interactiveMap = interactiveMap;
        this.stationInfoPane = stationInfoPane;
        this.weatherAPIWorker = WeatherAPIWorker.getInstance();

        rows = new ArrayList<>();

        x = new SimpleDoubleProperty(1);
        y = new SimpleDoubleProperty(1);

        allStationsHaveCoordinates = new SimpleBooleanProperty(false);

        y.addListener(this::coordinateIsSet);

        // this one is excluded as X and Y properties changes at the same time
        //x.addListener(this::coordinateIsSet);

        addButtonClickHandler = event -> {
            addButtonClicked(event);
            event.consume();
        };


        hBoxClickHandler = event -> {
            hBoxClicked(event);
            event.consume();
        };
    }

    private void hBoxClicked(MouseEvent event) {

        if (event.getButton()== MouseButton.PRIMARY){

            stationInfoPane.addInfoRow(
                        WeatherDataStore.getInstance().
                                getStationInfo(Long.parseLong(((HBox)event.getSource()).getId())));

        }

    }

    public void addRow(Row row){
        getItems().add(row);
        rows.add(row);

        row.getAddButton().setOnMouseClicked(addButtonClickHandler);
        row.addEventHandler(MouseEvent.MOUSE_PRESSED, hBoxClickHandler);
    }

    public void addRows(List<Row> rows){
        setItems(FXCollections.observableArrayList(rows));
        this.rows.addAll(rows);

        rows.forEach((row) -> row.getAddButton().setOnMouseClicked(addButtonClickHandler));
        rows.forEach((row) -> row.addEventHandler(MouseEvent.MOUSE_PRESSED, hBoxClickHandler));
    }

    private void addButtonClicked(MouseEvent event) {

        currentStationID = Long.valueOf(((Button) event.getSource()).getId());

        // disable all buttons until a place for the selected one is not specified
        for (Row row : rows) {
            row.getAddButton().setDisable(true);
        }

        interactiveMap.startPickingPlaceForNewStation(x, y);
    }

    private void coordinateIsSet(ObservableValue<? extends Number> observable,
                                 Number oldValue,
                                 Number newValue) {

//        System.out.println("oldValue - "+oldValue);
//        System.out.println("newValue - "+newValue);
//        System.out.println("coordinate x "+x.get());
//        System.out.println("coordinate y "+y.get());

        // get this station's info
        Station station = WeatherDataStore.getInstance().getStationInfo(currentStationID);
        // set given coordinated
        station.setCoordinateXOnInteractiveMap(x.get());
        station.setCoordinateYOnInteractiveMap(y.get());

        // upload changes to the server
        boolean changingResult = weatherAPIWorker.changeStationInfo(station);

        // if uploaded successfully
        if (changingResult) {


            // make all rows's buttons active
            for (Row row : rows) {

                // if setting station's coordinates is successful then disable the button
                if (Long.valueOf(row.getId()) == currentStationID) {

                    row.coordinatesAddedSuccessfully(true);
                    this.getItems().remove(row);
                    specifiedStations++;
                    continue;
                }

                row.getAddButton().setDisable(false);
            }


        } else {

            for (Row row : rows) {
                row.getAddButton().setDisable(false);

                if (Long.valueOf(row.getId()) == currentStationID) {
                    row.coordinatesAddedSuccessfully(false);
                }
            }
        }

        if (rows.size() == specifiedStations){

            allStationsHaveCoordinates.set(true);

        }

    }

    public BooleanProperty getAllStationsHaveCoordinates() {
        return allStationsHaveCoordinates;
    }

    public List<Row> getRows() {
        return rows;
    }
}
