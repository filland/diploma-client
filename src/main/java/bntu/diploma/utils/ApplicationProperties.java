package bntu.diploma.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

    public static Properties prop = new Properties();

    private static InputStream input = null;

    static {

        try {
            input = new FileInputStream("config.properties");
        } catch (FileNotFoundException e) {
            System.out.println("The app will not work as config.properties file not found");
            e.printStackTrace();
        }

        try {
            prop.load(input);
        } catch (IOException e) {
            System.out.println("The app will not work as config.properties was not loaded");
            e.printStackTrace();
        }

    }

}
