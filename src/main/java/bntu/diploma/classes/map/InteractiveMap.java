package bntu.diploma.classes.map;

import bntu.diploma.classes.WeatherAPIWorker;
import bntu.diploma.model.Station;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Styleable;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InteractiveMap extends Pane {

    private ObservableList<StationWeatherInfoNode> dots;
    private MouseActionHandler mouseClicksHandler;

    private final String LINK_TO_MAP = "map.png";
    private Image imageRect;
    private ImageView imageView;

    // using for moving a station
    private String lastNodeId;

    private Pane parentPane;

    private WeatherAPIWorker weatherAPIWorker = WeatherAPIWorker.getInstance();

    public InteractiveMap(Pane parentPane) {

        this.parentPane = parentPane;

        dots = FXCollections.observableArrayList();

        imageRect = new Image(LINK_TO_MAP);
        imageView = new ImageView(imageRect);
        imageView.setPreserveRatio(true);
        imageView.toBack();
        imageView.fitHeightProperty().bind(parentPane.heightProperty());
        imageView.fitWidthProperty().bind(parentPane.widthProperty());

        mouseClicksHandler = new MouseActionHandler(this, imageView);

        this.getChildren().add(imageView);
    }


    public void startMovingStationInfoNode(){
        mouseClicksHandler.startWaitingForPickingStation();
    }

    public void addStationInfoNode(StationWeatherInfoNode node){

        dots.add(node);
        mouseClicksHandler.addOneStationInfoNode(node);
        this.getChildren().add(node);
    }

    public void addAllStationInfoNodes(List<StationWeatherInfoNode> nodes){

        dots.addAll(nodes);
        mouseClicksHandler.addStationInfoNodes(dots);
        this.getChildren().addAll(nodes);
    }

    public void addNewStation(Station station){

        // working with APIWorker
        // sent post request with data from station's instance
        weatherAPIWorker.addNewStation(station);



    }



    // TODO move all code handling events to this class
    public class MouseActionHandler {

        // dot id, station
        private Map<String, StationWeatherInfoNode> dots;

        private Pane parentPane;
        private ImageView imageView;

        private boolean pickingStation = false;
        private boolean pickingNewPlace = false;

        private EventHandler<MouseEvent> mapMouseClickHandler;
        private EventHandler<MouseEvent> stationMouseClickHandler;

        // adding delay when scaling stationInfoNodes
        private PauseTransition pause = new PauseTransition(Duration.seconds(0.5));

        private double oldHeight;
        private double oldWidth;

        public MouseActionHandler(Pane parentPane, ImageView imageView) {

            this.parentPane = parentPane;
            this.imageView = imageView;

            oldHeight = parentPane.getHeight();
            oldWidth = parentPane.getWidth();

            dots = new HashMap<>();

            this.mapMouseClickHandler = event -> {
                mapClicked(event);
                event.consume();
            };

            this.imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, mapMouseClickHandler);


            ChangeListener<Number> parentPaneSizeListener = (observable, oldValue, newValue) -> {

                pause.setOnFinished(event -> interactiveMapResized(observable, oldValue, newValue));
                pause.playFromStart();
            };

            this.parentPane.widthProperty().addListener(parentPaneSizeListener);
            this.parentPane.heightProperty().addListener(parentPaneSizeListener);

            stationMouseClickHandler = this::stationClicked;

        }


        private void interactiveMapResized(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){


            if (oldHeight == 0 || oldWidth == 0){

                oldHeight = imageView.getFitHeight();
                oldWidth = imageView.getFitWidth();
                return;
            }

            dots.forEach((dotId, stationWeatherInfoNode) -> {


                //System.out.println("scale x - "+parentPane.getWidth()/oldWidth);
                //System.out.println("scale y - "+parentPane.getHeight()/oldHeight);

                stationWeatherInfoNode.scaleStationInfoNode(imageView.getFitWidth()/oldWidth,
                                                imageView.getFitHeight()/oldHeight);

            });

            //parentPane.setScaleX(oldWidth/imageView.getX());
            //parentPane.setScaleY(oldHeight/imageView.getY());


            oldHeight = imageView.getFitHeight();
            oldWidth = imageView.getFitWidth();
        }

        private void stationClicked(MouseEvent mouseEvent) {

            String nodeId = ((Styleable)mouseEvent.getTarget()).getId();

           /* dots.get("1").getMapWithLabels().forEach((key, value) ->{

                System.out.println(key + " - " +value.getId());
            });*/

            if (nodeId == null)
                return;

            // while moving a stationInfo node
            if (pickingStation){

                lastNodeId = nodeId;
                dots.get(lastNodeId).getDot().setFill(Paint.valueOf("RED"));
                pickingStation = false;
                pickingNewPlace = true;
                return;
            }

            // hiding/showing stationInfoNode's info by click
            if (dots.get(nodeId).isStationInfoVisible()) {

                dots.get(nodeId).hideInfo();
                dots.get(nodeId).setStationInfoVisible(false);

            } else {

                dots.get(nodeId).toFront();
                dots.get(nodeId).showInfo();
                dots.get(nodeId).setStationInfoVisible(true);
            }

//            System.out.println("stat x - "+ dots.get(nodeId).getLayoutX());
//            System.out.println("stat Y - "+ dots.get(nodeId).getLayoutY()+"\n");
        }


        private void mapClicked(MouseEvent event) {

            //System.out.println("x - "+event.getX());
            //System.out.println("y - "+event.getY());

            // setting a new place for a stationInfoNode
            if (pickingNewPlace){
                moveStation(lastNodeId, event.getX(), event.getY());
                pickingNewPlace = false;
                dots.get(lastNodeId).getDot().setFill(Paint.valueOf("GREEN"));
                return;
            }

            dots.forEach((circle, stationWeatherInfoNode) -> stationWeatherInfoNode.hideInfo());
        }

        // once the method is called a target stationInfoNode should be selected by clicking
        public void startWaitingForPickingStation(){
            pickingStation = true;
        }

        // called when a new place for the selected stationInfoNode was specified by clicking
        public void moveStation(String id, double x, double y){
            dots.get(id).moveStationInfoNode(x, y);
        }

        public void addStationInfoNodes(ObservableList<StationWeatherInfoNode> dots){

            // add dots
            dots.forEach(stationWeatherInfoNode -> this.dots.put(stationWeatherInfoNode.getDot().getId(), stationWeatherInfoNode));

            // add one eventHandler to all dots
            dots.forEach(stationWeatherInfoNode -> stationWeatherInfoNode.addEventHandler(MouseEvent.MOUSE_PRESSED, stationMouseClickHandler));
        }

        public void addOneStationInfoNode(StationWeatherInfoNode node){

            this.dots.put(node.getDot().getId(), node);
            node.addEventHandler(MouseEvent.MOUSE_PRESSED, stationMouseClickHandler);
        }

    }
}
