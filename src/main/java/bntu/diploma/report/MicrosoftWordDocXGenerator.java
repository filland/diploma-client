package bntu.diploma.report;

import bntu.diploma.model.Station;
import bntu.diploma.model.WeatherInfo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bntu.diploma.utils.OblastUtils;
import org.apache.poi.xwpf.usermodel.*;

public class MicrosoftWordDocXGenerator {


    public XWPFDocument generateDocX(List<Station> stationList, Map<Long, Set<WeatherInfo>> stationsWeatherInfoMap) {

        //Blank Document
        XWPFDocument document = new XWPFDocument();

        for (Station station : stationList) {

            //create table that contains info about station
            XWPFTable stationTable = document.createTable();
            stationTable.setCellMargins(30,100,30,100);

            XWPFTableRow stationTableRow = stationTable.getRow(0);
            stationTableRow.setHeight(700);

            stationTableRow.getCell(0).setText("ID станции");
            stationTableRow.addNewTableCell().setText(String.valueOf(station.getStationsId()));

            stationTable.createRow().getCell(0).setText("Ближайший город");
            stationTable.getRow(1).getCell(1).setText(station.getNearestTown());

            stationTable.createRow().getCell(0).setText("Область");
            stationTable.getRow(2).getCell(1).setText(OblastUtils.getOblastTextName(station.getOblast()));

            stationTable.createRow().getCell(0).setText("Дата установки");
            stationTable.getRow(3).getCell(1).setText(station.getInstallationDate());

            stationTable.createRow().getCell(0).setText("Дата последней инспекции");
            stationTable.getRow(4).getCell(1).setText(station.getLastInspection());

            stationTable.createRow().getCell(0).setText("Долгота");
            stationTable.getRow(5).getCell(1).setText(String.valueOf(station.getStationLatitude()));

            stationTable.createRow().getCell(0).setText("Широта");
            stationTable.getRow(6).getCell(1).setText(String.valueOf(station.getStationLongitude()));

            stationTable.createRow().getCell(0).setText("Уровень батареи");
            stationTable.getRow(7).getCell(1).setText(String.valueOf(station.getCurrentBatteryLevel()));


            // add empty line to separate tables
            document.createParagraph().createRun();


            XWPFTable weatherInfoTable = document.createTable();
            weatherInfoTable.setCellMargins(30,50,30,50);

            XWPFTableRow weatherInfoTableRow = weatherInfoTable.getRow(0);

            weatherInfoTableRow.getCell(0).setText("Дата и время");
            weatherInfoTableRow.addNewTableCell().setText("Температура");
            weatherInfoTableRow.addNewTableCell().setText("Влажность");
            weatherInfoTableRow.addNewTableCell().setText("Давление");
            weatherInfoTableRow.addNewTableCell().setText("Скорость ветра");
            weatherInfoTableRow.addNewTableCell().setText("Направление ветра");
            weatherInfoTableRow.addNewTableCell().setText("Уровень батареи");


            for (WeatherInfo weatherInfo : stationsWeatherInfoMap.get(station.getStationsId())) {

                //create first row
                XWPFTableRow tableRowOne = weatherInfoTable.createRow();

                tableRowOne.getCell(0).setText(weatherInfo.getDateTime());
                tableRowOne.getCell(1).setText(String.valueOf(weatherInfo.getTemperature()));
                tableRowOne.getCell(2).setText(String.valueOf(weatherInfo.getHumidity()));
                tableRowOne.getCell(3).setText(String.valueOf(weatherInfo.getPressure()));
                tableRowOne.getCell(4).setText(String.valueOf(weatherInfo.getWindSpeed()));
                tableRowOne.getCell(5).setText(String.valueOf(weatherInfo.getWindDirection()));
                tableRowOne.getCell(6).setText(String.valueOf(weatherInfo.getBatteryLevel()));

            }

            // add empty line to separate tables
            document.createParagraph().createRun();
            document.createParagraph().createRun();

        }

        return document;
    }

    public boolean saveDocX(String pathAndDocName, XWPFDocument document) throws IOException {

        if (pathAndDocName == null){

            throw new IllegalArgumentException("pathAndDocName should not be NULL");
        }

        if (pathAndDocName.trim().isEmpty()){

            throw new IllegalArgumentException("pathAndDocName should not be empty");
        }

        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(pathAndDocName);
        document.write(out);
        out.close();

        return true;
    }
}
