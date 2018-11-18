package bntu.diploma.domain;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 *
 *
 *
 *
 * */
public class Station implements Comparable<Station> {

    @Min(0)
    @NotNull
    private Long stationsId;

    @NotNull
    private String stationUniqueKey;

    /**
     * The oblast where the station is located
     */
    @Min(0)
    @NotNull
    private Long oblastId;

    @NotNull
    private String nearestTown;

    /**
     * Coordinates of the station
     */
    @Min(0)
    @NotNull
    private Double stationLongitude;
    @Min(0)
    @NotNull
    private Double stationLatitude;

    /**
     * The date when the station was set up
     */
    @NotNull
    private String installationDate;

    /**
     * The date when the station was inspected last time
     */
    @NotNull
    private String lastInspection;

    /**
     * Shows the current level of the station's battery
     * <p>
     * The param can equal the value from 1 to 100.
     */
    @Min(0)
    @NotNull
    private int currentBatteryLevel;

    /**
     * Used to place a representation of the station as a dot on a map
     */
    private Double coordinateXOnInteractiveMap;


    /**
     * Used to place a representation of the station as a dot on a map
     */
    private Double coordinateYOnInteractiveMap;

    public Station() {
    }

    public Long getStationsId() {
        return stationsId;
    }

    public void setStationsId(Long stationsId) {
        this.stationsId = stationsId;
    }

    public Long getOblast() {
        return oblastId;
    }

    public void setOblast(Long oblast) {
        this.oblastId = oblast;
    }

    public String getNearestTown() {
        return nearestTown;
    }

    public void setNearestTown(String nearestTown) {
        this.nearestTown = nearestTown;
    }

    public Double getStationLongitude() {
        return stationLongitude;
    }

    public void setStationLongitude(Double stationLongitude) {
        this.stationLongitude = stationLongitude;
    }

    public Double getStationLatitude() {
        return stationLatitude;
    }

    public void setStationLatitude(Double stationLatitude) {
        this.stationLatitude = stationLatitude;
    }

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    public String getLastInspection() {
        return lastInspection;
    }

    public void setLastInspection(String lastInspection) {
        this.lastInspection = lastInspection;
    }

    public Integer getCurrentBatteryLevel() {
        return currentBatteryLevel;
    }

    public void setCurrentBatteryLevel(Integer currentBatteryLevel) {
        this.currentBatteryLevel = currentBatteryLevel;
    }

    public Double getCoordinateXOnInteractiveMap() {
        return coordinateXOnInteractiveMap;
    }

    public void setCoordinateXOnInteractiveMap(Double coordinateXOnInteractiveMap) {
        this.coordinateXOnInteractiveMap = coordinateXOnInteractiveMap;
    }

    public Double getCoordinateYOnInteractiveMap() {
        return coordinateYOnInteractiveMap;
    }

    public void setCoordinateYOnInteractiveMap(Double coordinateYOnInteractiveMap) {
        this.coordinateYOnInteractiveMap = coordinateYOnInteractiveMap;
    }

    public String getStationUniqueKey() {
        return stationUniqueKey;
    }

    public void setStationUniqueKey(String stationUniqueKey) {
        this.stationUniqueKey = stationUniqueKey;
    }

    @Override
    public int compareTo(Station o) {
        return o.getStationsId().compareTo(this.getStationsId());
    }
}
