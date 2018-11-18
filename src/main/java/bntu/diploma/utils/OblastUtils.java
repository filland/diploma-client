package bntu.diploma.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * This class should not exist
 */
@Deprecated
public class OblastUtils {

    private static Map<Long, String> intOblastMap;

    static {

        intOblastMap = new HashMap<>();

        for (OblastEnum oblastEnum : OblastEnum.values()) {
            intOblastMap.put(oblastEnum.getId(), oblastEnum.getOblastName());
        }

    }

    public static String getOblastTextName(long number) {

        if (number > 6 || number < 1)
            return null;

        return intOblastMap.get(number);
    }

}
