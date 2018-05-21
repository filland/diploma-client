package bntu.diploma.classes.map;

import bntu.diploma.classes.WeatherAPIWorker;
import bntu.diploma.model.Station;
import bntu.diploma.utils.ApplicationProperties;
import javafx.animation.PauseTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import javax.swing.plaf.multi.MultiOptionPaneUI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InteractiveMap extends Pane {

    private ObservableList<StationWeatherInfoNode> dots;
    private MouseActionHandler mouseClicksHandler;

    private final String LINK_TO_MAP = ApplicationProperties.prop.getProperty("map");
    private Image imageRect;
    private ImageView imageView;

    // using for moving a station
    private String lastNodeId;
    private SimpleLongProperty currentSelectedStationsID;

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

        currentSelectedStationsID = new SimpleLongProperty(1);

        mouseClicksHandler = new MouseActionHandler(this, imageView, currentSelectedStationsID);

        this.getChildren().add(imageView);
    }


    public void startMovingStationInfoNode(){
        mouseClicksHandler.startWaitingForPickingStation();
    }

    public void startPickingPlaceForNewStation(DoubleProperty x, DoubleProperty y) {
        mouseClicksHandler.startPickingPlaceForANewStation(x, y);
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


    public SimpleLongProperty getCurrentSelectedStationsID() {
        return currentSelectedStationsID;
    }

    public SimpleLongProperty currentSelectedStationsIDProperty() {
        return currentSelectedStationsID;
    }

    // this class is responsible for handling any actions on InteractionMap
    public class MouseActionHandler {


        private LongProperty currentSelectedStationsID;

        // dot id, station
        private Map<String, StationWeatherInfoNode> dots;

        private Pane parentPane;
        private ImageView imageView;

        /*
        *
        * equals true when user need to pick a dot to move
        *
        * */
        private boolean pickingStation = false;
        /*
        * equals true when user needs to specify a new place for selected dot
        *
        * */
        private boolean pickingNewPlace = false;



        /*
        * When a new station needs to be placed on the map this param is true
        * */
        private boolean pickingPlaceForNewStation = false;
        // coords for a new station
        private DoubleProperty x;
        private DoubleProperty y;


        private EventHandler<MouseEvent> mapMouseClickHandler;
        private EventHandler<MouseEvent> stationMouseClickHandler;

        // adding delay when scaling stationInfoNodes
        private PauseTransition pause = new PauseTransition(Duration.seconds(0.5));

        private double oldHeight;
        private double oldWidth;

        public MouseActionHandler(Pane parentPane, ImageView imageView, SimpleLongProperty currentSelectedStationsID) {

            this.parentPane = parentPane;
            this.imageView = imageView;

            this.currentSelectedStationsID = currentSelectedStationsID;

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

                oldWidth = imageView.boundsInParentProperty().get().getWidth();
                oldHeight = imageView.boundsInParentProperty().get().getHeight();
                return;
            }

            dots.forEach((dotId, stationWeatherInfoNode) -> {

//                System.out.println("actual image height  - "+imageView.getImage().getHeight());
//                System.out.println("actual image width  - "+imageView.getImage().getWidth());
//
//                System.out.println("image height - "+imageView.boundsInParentProperty().get().getHeight());
//                System.out.println("image width - "+imageView.boundsInParentProperty().get().getWidth());

                stationWeatherInfoNode.scaleStationInfoNode(imageView.boundsInParentProperty().get().getWidth()/oldWidth,
                        imageView.boundsInParentProperty().get().getHeight()/oldHeight);

            });

            //parentPane.setScaleX(oldWidth/imageView.getX());
            //parentPane.setScaleY(oldHeight/imageView.getY());


            oldWidth = imageView.boundsInParentProperty().get().getWidth();
            oldHeight = imageView.boundsInParentProperty().get().getHeight();
        }

        private void stationClicked(MouseEvent mouseEvent) {

            String nodeId = ((Styleable)mouseEvent.getTarget()).getId();


            if (nodeId == null)
                return;

            // tell what station is selected to update other components of the application
            if (mouseEvent.getButton() == MouseButton.PRIMARY){

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

            } else if (mouseEvent.getButton() == MouseButton.SECONDARY){

                // make previous selected station's dot green
                dots.get(String.valueOf(currentSelectedStationsID.get())).getDot().setFill(Color.GREEN);

                currentSelectedStationsID.set(Long.parseLong(nodeId));
                dots.get(nodeId).getDot().setFill(Color.ORANGE);
            }

//            System.out.println("stat x - "+ dots.get(nodeId).getLayoutX());
//            System.out.println("stat Y - "+ dots.get(nodeId).getLayoutY()+"\n");
        }


        private void mapClicked(MouseEvent event) {

            // setting a new place for a stationInfoNode
            if (pickingNewPlace){
                moveStation(lastNodeId, event.getX(), event.getY());
                pickingNewPlace = false;
                dots.get(lastNodeId).getDot().setFill(Paint.valueOf("GREEN"));
                return;
            }


            if (pickingPlaceForNewStation){
                this.x.set(event.getX());
                this.y.set(event.getY());
                pickingPlaceForNewStation = false;
            }

            // hide info of each dot on the map
            dots.forEach((circle, stationWeatherInfoNode) -> stationWeatherInfoNode.hideInfo());
        }

        // once the method is called a target stationInfoNode should be selected by clicking
        public void startWaitingForPickingStation(){
            pickingStation = true;
        }

        public void startPickingPlaceForANewStation(DoubleProperty x, DoubleProperty y){

            this.x = x;
            this.y = y;

            pickingPlaceForNewStation = true;
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

        public long getCurrentSelectedStationsID() {
            return currentSelectedStationsID.get();
        }

        public LongProperty currentSelectedStationsIDProperty() {
            return currentSelectedStationsID;
        }

        public void setCurrentSelectedStationsID(SimpleLongProperty currentSelectedStationsID) {
            this.currentSelectedStationsID = currentSelectedStationsID;
        }
    }
}
