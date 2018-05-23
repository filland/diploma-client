package bntu.diploma.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {

    // this one is from the server project
    final public static DateTimeFormatter DATE_FORMATTER2 = DateTimeFormatter.ofPattern ("dd-MM-yyyy HH:mm");
    final public static SimpleDateFormat SIMPLE_DATE_FORMATTER2 = new SimpleDateFormat("dd-MM-yyyy HH:mm");


    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat WITHOUT_YEAR_DATE_FORMATTER = new SimpleDateFormat("dd/MM HH:mm");

    public static String formatDate(Date date){
        return DATE_FORMATTER.format(date);
    }

    public static String formatDate(String date){
        Date date2 = null;
        try {
            date2 = SIMPLE_DATE_FORMATTER2.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return WITHOUT_YEAR_DATE_FORMATTER.format(date2);
    }

}
