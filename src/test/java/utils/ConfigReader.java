package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try{
            properties=new Properties();

            InputStream input = ConfigReader.class.getClassLoader()
                    .getResourceAsStream("application.properties");

            properties.load(input);
        }catch (Exception e){
            throw new RuntimeException("failed to load properties");
        }
    }

    public static String get(String key){
        return properties.getProperty(key);
    }
}
