package bntu.diploma.beans;



/**
 *
 *
 *
 *
 * */

public class Station {

    private Long stationsId;

    private String stationUniqueKey;

    /**
     * The oblast where the station is located
     * */
    private Long oblastId;

    private String nearestTown;

    /**
     * Coordinates of the station
     * */
    private Double stationLongitude;

    private Double stationLatitude;

    /**
     * The date when the station was set up
     * */
    private String installationDate;

    /**
     * The date when the station was inspected last time
     * */
    private String lastInspection;

    public Station() {
    }

    public Long getStationsId() {
        return stationsId;
    }

    public void setStationsId(Long stationsId) {
        this.stationsId = stationsId;
    }

    public String getStationUniqueKey() {
        return stationUniqueKey;
    }

    public void setStationUniqueKey(String stationUniqueKey) {
        this.stationUniqueKey = stationUniqueKey;
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

}
