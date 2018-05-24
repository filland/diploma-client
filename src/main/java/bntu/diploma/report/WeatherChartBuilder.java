package bntu.diploma.report;

import bntu.diploma.classes.WeatherDataStore;
import bntu.diploma.model.Station;
import bntu.diploma.model.WeatherInfo;
import bntu.diploma.utils.Utils;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
                       series.getData().add(new XYChart.Data(Utils.formatDate(weatherInfo.getDateTime()),
                                            method.invoke(weatherInfo)));
//                       series.getData().add(new XYChart.Data(weatherInfo.getDateTime(),method.invoke(weatherInfo)));
                   } catch (IllegalAccessException e) {
                       e.printStackTrace();
                   } catch (InvocationTargetException e) {
                       e.printStackTrace();
                   }
               }

           }
        }

        lineChart.getData().add(series);

        Station station = WeatherDataStore.getInstance().getStationInfo(weatherInfoList.get(0).getStation());
        lineChart.setTitle(parameter.name()+" changes chart, " +station.getNearestTown()+" "+station.getStationsId());

        return lineChart;
    }


    public boolean saveAsImage(String pathAndName, LineChart chart){

        WritableImage image = new WritableImage((int) chart.getWidth(), (int) chart.getHeight());
        chart.snapshot(null, image);
        File outFile = new File(pathAndName);
        System.out.println(outFile);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }

}
