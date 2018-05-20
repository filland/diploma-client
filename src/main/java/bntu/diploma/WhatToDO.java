package bntu.diploma;


/**
 *
 * TODO
 *
 * AGENDA: AddingNewStationsToMapTableView.coordinateIsSet()
 *
 * - When  changing map's width by dragging vertical split line Scale Y always equals 1
 *  - add shift to coordinates while scaling (make moving stationIntoNode's coordinates more accurate while scaling)
 *  - add separate table of all stations. Open a separate tables with data by click.
 *  - a feature for adding new stations to the InteractiveMap (a popup menu with a list of new station to place on the map)
 * - change data in upper and lower panes when click by a green dot
 * - API Worker !!!!
 * - upload recent data from the server
 * - Storing coordinates of station's dots on the server. If this fields are null then user needs to add them via the app
 * - HTML report generation
 * - Remove boilerplate code in WeatherAPIWorker
 *
 *
 * Not so ASAP:
 * - Move all initializations of view in FXML
 * - Add a logo above Id/secretKey fields in LoginForm
 *
 *
 * DONE:
 * - split login and main controller (see how to do this here https://stackoverflow.com/questions/15041760/javafx-open-new-window)
 * - Fix exception invoking by clicking stationInfoNode's params
 * - Why apiWorker cannot download data ?
 *  - StationWeatherInfoNode's info show be at front
 * - change the lower table to a list of fields
 * - scaling windows and dots on it and dots' size (probably) - mostly
 */
public class WhatToDO {
}
