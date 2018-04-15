package bntu.diploma.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String formatDate(Date date){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

}
