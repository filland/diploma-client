package bntu.diploma.controller;

import bntu.diploma.classes.WeatherDataStore;
import bntu.diploma.model.Station;
import bntu.diploma.model.WeatherInfo;
import bntu.diploma.report.MicrosoftWordDocXGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DocXGeneratorController {

    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private ComboBox<String> stationNameComboBox;
    @FXML
    private Label stationLabel;
    @FXML
    private Label fromDateLabel;
    @FXML
    private Label toDateLabel;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private CheckBox allStationsCheckBox;
    @FXML
    private Button generateDocButton;

    private MicrosoftWordDocXGenerator generator;

    @FXML
    public void initialize() {

        generator = new MicrosoftWordDocXGenerator();

        List<String> stationsNames = new ArrayList<>();
        for (Station station : WeatherDataStore.getInstance().getAllStations()) {
            stationsNames.add(station.getNearestTown() + "_" + station.getStationsId());
        }
        stationNameComboBox.getItems().addAll(stationsNames);

    }

    public void generateDocButtonClicked(ActionEvent actionEvent) {

        XWPFDocument document;

        if (allStationsCheckBox.isSelected()) {

            document = generator.generateDocX(WeatherDataStore.getInstance().getAllStations(),
                    WeatherDataStore.getInstance().getStationsWeatherInfoMap());

        } else {

            String var[] = stationNameComboBox.getSelectionModel().getSelectedItem().split("_");
            long stationID = Long.valueOf(var[1]);

            Map<Long, Set<WeatherInfo>> map = new HashMap<>();
            map.put(stationID, new HashSet<>(WeatherDataStore.getInstance().getAllWeatherInfoForStation(stationID)));

            ArrayList<Station> arrayList = new ArrayList<>();
            arrayList.add(WeatherDataStore.getInstance().getStationInfo(stationID));

            document = generator.generateDocX(arrayList, map);
        }

       if (document != null){

           FileChooser fileChooser = new FileChooser();
           fileChooser.setInitialFileName("report1.docx");
           fileChooser.setTitle("Указание пути для сохранения отчета");
           File file = fileChooser.showSaveDialog(generateDocButton.getScene().getWindow());

           if (file != null) {

               try {
                   if (file.getAbsolutePath().toLowerCase().endsWith(".docx"))
                       generator.saveDocX(file.getAbsolutePath(), document);
                   else
                       generator.saveDocX(file.getAbsolutePath() + ".docx", document);
               } catch (IOException e) {
                   System.err.println("Have not managed to save docx report");
                   //e.printStackTrace();
               }
           }
       }
    }

    public void allStationClicked(ActionEvent actionEvent) {

        if (allStationsCheckBox.isSelected()) {

            stationNameComboBox.setDisable(true);
        } else {

            stationNameComboBox.setDisable(false);

        }

    }
}
