package bntu.diploma.utils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {

    // this one is from the server project
    final public static DateTimeFormatter DATE_FORMATTER2 = DateTimeFormatter.ofPattern ("dd-MM-yyyy HH:mm");

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

    public static String formatDate(Date date){
        return DATE_FORMATTER.format(date);
    }

}
