package bntu.diploma;


/**
 *
 *
 *
 * TODO AGENDA: once a station is located it should become visible
 *
 * - REFACTOR NODE PACKAGE
 * - Is it possible to move application.properties file out of .jar executable
 * - add separate table of all stations. Open a separate tables with data by click.
 * - handle all exceptions in AddNewStationController
 * - Suspend Postman while user is logged out (timeline object needs to be accessed in two Controllers)
 * - Find out the reason of the exception arising once Postman updates elements
 * - Center StationInfoPane
 * - Make encryption real AES implementation
 * - custom exceptions
 * - BUG. app keeps getting all data info after logout
 * - add button for manual weather info update
 *
 * Not so ASAP:
 * - separate property files for GUI text messages and and for parameter used in code (like temperature, windspeed etc.)
 * - Move all text in properties to make localization as simple as possible
 * - HTML report generation
 * - When user logged in and then server shut down in this case user cannot log off !!!
 * - Process dates in ChartBuilderController
 * - Once connection with the server lost notify the user about this
 * - Remove boilerplate code in WeatherAPIWorker
 * - Setting critical threshold. When it is exceeded al alert is popped up
 * - Create a separate thread for APIWorker !!!!!
 * - Move all initializations of view in FXML
 * - Add a logo above Id/secretKey fields in LoginForm
 * - Swap date and time in AllRecordsTable
 * - Replace local "current station's ids" with GlobalContext class
 *
 * DONE:
 * - Handle the exception which arises when you added a new station and it does not have
 * any weatherInfo - now if a new station added one empty WeatherInfo record will be added until a real one exists
 * - Implement the "Разместить" button in AddNewStationController
 * - host and port should be moved to the prop file
 * - Implement Doc report generator
 * - Implement ChartBuilder
 * - !!!! A class for building charts and diagrams (Creating a chart and a diagram for stations)
 * - Handle case when Postman loses connection with the server
 * - upload recent data from the server
 * - ADD TO GIT REPO add region's centers on the map
 * - Check if the server is available in LoginController
 * - When  changing map's width by dragging vertical split line Scale Y always equals 1
 *  - add shift to coordinates while scaling (make moving stationIntoNode's coordinates more accurate while scaling)
 *  the problem solved by finding the right method for getting actual image H/W
 * - change data in upper and lower panes when click by a green dot
 * - Storing coordinates of station's dots on the server. If this fields are null then user needs to add them via the app
 *  - a feature for adding new stations to the InteractiveMap (a popup menu with a list of new station to place on the map)
 *  was implemented as a ListView that is placed instead of AllRecords table until all stations are specified
 * - split login and main controller (see how to do this here https://stackoverflow.com/questions/15041760/javafx-open-new-window)
 * - Fix exception invoking by clicking stationInfoNode's params
 * - Why apiWorker cannot download data ?
 *  - StationWeatherInfoNode's info show be at front
 * - change the lower table to a list of fields
 * - scaling windows and dots on it and dots' size (probably) - mostly
 */
public class WhatToDO {
}
