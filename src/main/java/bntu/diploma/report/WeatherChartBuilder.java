package bntu.diploma.report;

import bntu.diploma.classes.WeatherDataStore;
import bntu.diploma.model.Station;
import bntu.diploma.model.WeatherInfo;
import bntu.diploma.utils.Utils;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 *
 *
 *
 *
 * */
public class WeatherChartBuilder {

    public enum WeatherParameter{
        TEMPERATURE,
        WINDDIRECTION,
        HUMIDITY,
        WINDSPEED,
        PRESSURE,
        DATETIME,
        BATTERYLEVEL
    }

    public LineChart buildChart(List<WeatherInfo> weatherInfoList, WeatherParameter parameter){

        CategoryAxis xAxis = new CategoryAxis ();
        xAxis.setLabel("Date");


        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(parameter.name());


        //creating the chart
        LineChart<String,Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setLegendVisible(false);
        lineChart.setVerticalGridLinesVisible(true);
        lineChart.setHorizontalGridLinesVisible(true);

        XYChart.Series<String, Number> series = new XYChart.Series();

       // call the method that matches the passed parameter
        Class<? extends WeatherInfo> aClass = weatherInfoList.get(0).getClass();
        for (Method method : aClass.getMethods()) {

           if (method.getName().toLowerCase().equals("get"+parameter.name().toLowerCase())){

               for (WeatherInfo weatherInfo : weatherInfoList) {

                   try {
                       series.getData().add(new XYChart.Data(Utils.formatDate(weatherInfo.getDateTime()),method.invoke(weatherInfo)));
//                       series.getData().add(new XYChart.Data(weatherInfo.getDateTime(),method.invoke(weatherInfo)));
                   } catch (IllegalAccessException e) {
                       e.printStackTrace();
                   } catch (InvocationTargetException e) {
                       e.printStackTrace();
                   }
               }

           }
        }

//        lineChart.setCreateSymbols(false);
        lineChart.getData().add(series);

        Station station = WeatherDataStore.getInstance().getStationInfo(1L);
        lineChart.setTitle(parameter.name()+" changes chart, " +station.getNearestTown()+" "+station.getStationsId());

        return lineChart;
    }

}
