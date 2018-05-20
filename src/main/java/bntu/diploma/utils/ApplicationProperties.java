package bntu.diploma.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {


    public static Properties prop = new Properties();

    private static InputStream input = null;

    static {

        try {
            input = new FileInputStream("config.properties");

            prop.load(input);


        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }

}
