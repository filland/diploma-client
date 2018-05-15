package bntu.diploma.controller;

import bntu.diploma.model.StationInfoNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationInfoPane extends Pane {

    private ObservableList<StationInfoNode> dots;
    private MouseActionHandler mouseClicksHandler;

    private final String LINK_TO_MAP = "map.png";
    private Image imageRect;
    private ImageView imageView;

    // using for moving a station
    private String lastNodeId;

    private Pane parentPane;

    public StationInfoPane(Pane parentPane) {

        this.parentPane = parentPane;

        dots = FXCollections.observableArrayList();

        imageRect = new Image(LINK_TO_MAP);
        imageView = new ImageView(imageRect);
        imageView.setPreserveRatio(true);
        imageView.toBack();
        imageView.fitHeightProperty().bind(parentPane.heightProperty());
        imageView.fitWidthProperty().bind(parentPane.widthProperty());

        mouseClicksHandler = new MouseActionHandler(imageView);

        this.getChildren().add(imageView);
    }


    public void moveStationInfo(){

        mouseClicksHandler.startWaitingForPickingStation();

    }

    public void addStationInfoNode(StationInfoNode node){

        dots.add(node);
        mouseClicksHandler.addOneStationInfoNode(node);
        this.getChildren().add(node);
    }

    public void addAllStationInfoNodes(List<StationInfoNode> nodes){

        dots.addAll(nodes);
        mouseClicksHandler.addStationInfoNodes(dots);
        this.getChildren().addAll(nodes);
    }

    // TODO move all code handling events to this class
    public class MouseActionHandler {

        // dot id, station
        private Map<String, StationInfoNode> dots;

        private ImageView imageView;

        private boolean pickingStation = false;
        private boolean pickingNewPlace = false;

        private EventHandler<MouseEvent> mapMouseClickHandler;
        private EventHandler<MouseEvent> stationMouseClickHandler;

        public MouseActionHandler(ImageView imageView) {

            this.imageView = imageView;

            dots = new HashMap<>();

            this.mapMouseClickHandler = event -> {

                mapClicked(event);
                event.consume();
            };

            this.imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, mapMouseClickHandler);

            stationMouseClickHandler = this::stationClicked;

        }

        private void stationClicked(MouseEvent mouseEvent) {
            String nodeId = ((Shape)mouseEvent.getTarget()).getId();

            if (pickingStation){

                lastNodeId = nodeId;
                dots.get(lastNodeId).getDot().setFill(Paint.valueOf("RED"));
                pickingStation = false;
                pickingNewPlace = true;
                return;
            }

            if (dots.get(nodeId).isStationInfoVisible()) {

                dots.get(nodeId).hideInfo();
                dots.get(nodeId).setStationInfoVisible(false);

            } else {

                dots.get(nodeId).showInfo();
                dots.get(nodeId).setStationInfoVisible(true);
            }
        }


        private void mapClicked(MouseEvent event) {

            if (pickingNewPlace){
                moveStation(lastNodeId, event.getX(), event.getY());
                pickingNewPlace = false;
                dots.get(lastNodeId).getDot().setFill(Paint.valueOf("GREEN"));
                return;
            }

            dots.forEach((circle, stationInfoNode) -> stationInfoNode.hideInfo());
        }

        public void startWaitingForPickingStation(){

            pickingStation = true;
        }

        public void moveStation(String id, double x, double y){

            dots.get(id).moveStationInfoNode(x, y);
        }

        public void addStationInfoNodes(ObservableList<StationInfoNode> dots){

            // add dots
            dots.forEach(stationInfoNode -> this.dots.put(stationInfoNode.getDot().getId(), stationInfoNode));

            // add one eventHandler to all dots
            dots.forEach(stationInfoNode -> stationInfoNode.addEventHandler(MouseEvent.MOUSE_PRESSED, stationMouseClickHandler));
        }

        public void addOneStationInfoNode(StationInfoNode node){

            this.dots.put(node.getDot().getId(), node);
            node.addEventHandler(MouseEvent.MOUSE_PRESSED, stationMouseClickHandler);
        }

    }
}
