package bntu.diploma.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

    public static String formatDate(Date date){
        return DATE_FORMATTER.format(date);
    }

}
