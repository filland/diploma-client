package bntu.diploma.utils;

import java.util.HashMap;
import java.util.Map;

public class OblastUtils {

    private static Map<Long, String> intOblastMap;

    static {

        intOblastMap = new HashMap<>();

        intOblastMap.put(1L, "Brestskaya");
        intOblastMap.put(2L, "Vitebskaya");
        intOblastMap.put(3L, "Gomelskaya");
        intOblastMap.put(4L, "Grodnenskaya");
        intOblastMap.put(5L, "Minskaya");
        intOblastMap.put(6L, "Mogilevskaya");

    }

    public static String getOlastTextName(long number) {

        if (number > 6 || number < 1)
            return null;

        return intOblastMap.get(number);
    }

}
