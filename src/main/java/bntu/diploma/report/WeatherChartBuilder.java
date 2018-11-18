package bntu.diploma.report;

import bntu.diploma.classes.WeatherDataStore;
import bntu.diploma.domain.Station;
import bntu.diploma.domain.WeatherInfo;
import bntu.diploma.utils.Utils;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 *
 *
 *
 * */
public class WeatherChartBuilder {

    // the word each getter method should start with
    private static final String GETTER_PREFIX = "get";

    public enum WeatherParameter {
        TEMPERATURE("temperature"),
        WINDDIRECTION("wind direction"),
        HUMIDITY("humidity"),
        WINDSPEED("wind speed"),
        PRESSURE("pressure"),
        DATETIME("date time"),
        BATTERYLEVEL("battery level");

        private String name;

        WeatherParameter(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public LineChart buildChart(List<WeatherInfo> weatherInfoList, WeatherParameter parameter) {

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(parameter.getName());

        //creating the chart
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setLegendVisible(false);
        lineChart.setVerticalGridLinesVisible(true);
        lineChart.setHorizontalGridLinesVisible(true);

        XYChart.Series<String, Number> series = new XYChart.Series();

        // call the method that matches the passed parameter
        Class<? extends WeatherInfo> aClass = weatherInfoList.get(0).getClass();
        for (Method method : aClass.getMethods()) {

            if (method.getName().toLowerCase().equals(GETTER_PREFIX + parameter.getName().toLowerCase())) {

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
        lineChart.setTitle(parameter.getName() + " changes chart, " + station.getNearestTown() + " " + station.getStationsId());

        return lineChart;
    }


    public boolean saveAsImage(String pathAndName, LineChart chart) {

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
