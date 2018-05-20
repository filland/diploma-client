package bntu.diploma.utils;

import bntu.diploma.classes.WeatherDataStore;
import bntu.diploma.classes.map.StationWeatherInfoNode;
import bntu.diploma.model.Station;
import bntu.diploma.model.WeatherInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 *
 * Provides instances of models populated with data
 *
 *
 * */
public class DataUtils {

    private static int counter = 0;
    private static Integer stationInfoNodeShift = 110;



    public static List<WeatherInfo> getListOfWeatherInfo(){

        List<WeatherInfo> sysList = new ArrayList<>();
        sysList.add(new WeatherInfo("14-05-2018 12:00",1.1, 2.2, 10.1, 10.1, 12, 50));
        sysList.add(new WeatherInfo("15-05-2018 12:00",1.1, 2.2, 10.1, 10.1, 12, 50));
        sysList.add(new WeatherInfo("16-05-2018 12:00",1.1, 2.2, 10.1, 10.1, 12, 50));
        sysList.add(new WeatherInfo("17-06-2018 12:00",1.1, 2.2, 10.1, 10.1, 12, 50));
        sysList.add(new WeatherInfo("08-05-2018 12:00",1.1, 2.2, 10.1, 10.1, 12, 50));
        sysList.add(new WeatherInfo("19-05-2018 12:00",1.1, 2.2, 10.1, 10.1, 12, 50));
        sysList.add(new WeatherInfo("14-05-2018 15:00",1.1, 2.2, 10.1, 10.1, 12, 50));
        sysList.add(new WeatherInfo("02-05-2018 12:00",1.1, 2.2, 10.1, 10.1, 12, 50));
        sysList.add(new WeatherInfo("14-05-2018 12:00",1.1, 2.2, 10.1, 10.1, 12, 50));
        sysList.add(new WeatherInfo("10-07-2018 12:00",1.1, 2.2, 10.1, 10.1, 12, 50));
        sysList.add(new WeatherInfo("14-05-2018 12:00",1.1, 2.2, 10.1, 10.1, 12, 50));
        sysList.add(new WeatherInfo("14-01-2018 12:00",1.1, 2.2, 10.1, 10.1, 12, 50));
        sysList.add(new WeatherInfo("14-02-2018 12:00",1.1, 2.2, 10.1, 10.1, 12, 50));
        sysList.add(new WeatherInfo("14-03-2018 12:00",1.1, 2.2, 10.1, 10.1, 12, 50));

        return sysList;
    }

    public static WeatherInfo getWeatherInfoInstance(){

        return new WeatherInfo("14-03-2018 17:00",1.1, 2.2, 10.1, 10.1, 12, 50);
    }

    public static Station getStationInstance(){

        Station station2 = new Station();
        station2.setStationsId(1L);
        station2.setOblast(4L);
        station2.setInstallationDate("setInstallationDate");
        station2.setLastInspection("setLastInspection");
        station2.setNearestTown("setNearestTown");
        station2.setStationLongitude(1.1);
        station2.setStationLatitude(2.2);

        return station2;
    }


    public static StationWeatherInfoNode getStationInfoNodeInstance(){

        StationWeatherInfoNode infoNode = new StationWeatherInfoNode(stationInfoNodeShift, stationInfoNodeShift, ++counter, "vileyka");


        //        infoNode.setStationParam("temp", "1");
//        infoNode.setStationParam("temp2", "25.5");
//        infoNode.setStationParam("temp3", "25.5");

//        infoNode.setStationParam(new WeatherInfo("14052018",1.1, 2.2, 10.1, 10.1, 12, 50));

        infoNode.setStationParam(WeatherDataStore.getInstance().getLastWeatherInfo(1));

        stationInfoNodeShift+=50;

        return infoNode;

    }


}
