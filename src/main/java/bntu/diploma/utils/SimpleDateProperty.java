package bntu.diploma.utils;

import javafx.beans.property.SimpleStringProperty;
import java.util.Date;
import static bntu.diploma.utils.Utils.formatDate;

public class SimpleDateProperty extends SimpleStringProperty {

    public SimpleDateProperty(Date date){

        super(formatDate(date));
    }

    private SimpleDateProperty() {
    }

    private SimpleDateProperty(String initialValue) {
        super(initialValue);
    }
}
