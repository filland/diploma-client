package bntu.diploma.classes;

import bntu.diploma.classes.map.InteractiveMap;
import bntu.diploma.model.Station;
import bntu.diploma.utils.OblastUtils;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class AddingNewStationsToMapTableView extends TableView {

    public class Row extends Group{

        private Button addButton;
        private Label label;

        public Row(Station station) {

            this.setId(String.valueOf(station.getStationsId()));

            addButton = new Button();
            addButton.setId(String.valueOf(station.getStationsId()));

            label = new Label(station.getNearestTown()+" "+station.getStationsId()+"_"+ OblastUtils.getOblastTextName(station.getOblast()));
            label.setId(String.valueOf(station.getStationsId()));

            getChildren().addAll(addButton, label);
        }

        public Button getAddButton(){
            return addButton;
        }

        public void coordinatesAddedSuccessfully(boolean b){
            if (!b){
                label.setTextFill(Color.TRANSPARENT);

            } else {

                label.setTextFill(Color.RED);
            }
        }
    }

    private InteractiveMap interactiveMap;
    private WeatherAPIWorker weatherAPIWorker;

    private List<Row> rows;
    private EventHandler<MouseEvent> mouseClickHandler;

    private long currentStationID;
    private DoubleProperty x;
    private DoubleProperty y;

    public AddingNewStationsToMapTableView(InteractiveMap interactiveMap) {

        this.interactiveMap = interactiveMap;
        this.weatherAPIWorker = WeatherAPIWorker.getInstance();

        rows = new ArrayList<>();

        x = new SimpleDoubleProperty(1);
        y = new SimpleDoubleProperty(1);

        x.addListener(this::coordinateIsSet);
        y.addListener(this::coordinateIsSet);

        mouseClickHandler = event -> {
            mouseClicked(event);
            event.consume();
        };
    }

    public void addRow(Row row){
        getChildren().add(row);
        row.getAddButton().addEventHandler(MouseEvent.MOUSE_PRESSED, mouseClickHandler);
        rows.add(row);
    }

    public void addRows(List<Row> rows){
        getChildren().addAll(rows);
        this.rows.addAll(rows);

        rows.forEach((row) -> row.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseClickHandler));
    }

    private void mouseClicked(MouseEvent event) {

        currentStationID = Long.valueOf(((Button) event.getTarget()).getId());

        // disable all buttons until a place for the selected one is not specified
        for (Row row : rows) {
            row.getAddButton().setDisable(true);
        }

        interactiveMap.startPickingPlaceForNewStation(x, y);
    }

    private void coordinateIsSet(ObservableValue<? extends Number> observable,
                                 Number oldValue,
                                 Number newValue) {

        // get this station's info
        Station station = WeatherDataStore.getInstance().getStationInfo(currentStationID);
        // set given coordinated
        station.setCoordinateXOnInteractiveMap(x.get());
        station.setCoordinateYOnInteractiveMap(y.get());

        // upload changes to the server
        boolean changingResult = weatherAPIWorker.changeStationInfo(station);

        // if uploaded successfully
        if (changingResult) {

            // make all rows's button active
            for (Row row : rows) {
                row.getAddButton().setDisable(false);

                // if setting station's coordinates is successful then disable the button
                if (Long.valueOf(row.getId()) == currentStationID) {
                    row.coordinatesAddedSuccessfully(true);
                    row.setDisable(true);
                }
            }

        } else {

            for (Row row : rows) {
                row.getAddButton().setDisable(false);

                if (Long.valueOf(row.getId()) == currentStationID) {
                    row.coordinatesAddedSuccessfully(false);
                }
            }
        }

    }

}
