package bntu.diploma.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String formatDate(Date date){
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
        return dt1.format(date);
    }

}
