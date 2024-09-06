package permissions.utilities.config_utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream file = new FileInputStream("config.properties");
            properties.load(file);

            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param keyWord
     * @return String value of .properties file's key pair
     */
    public static String readProperty(String keyWord) {
        return properties.getProperty(keyWord);
    }

    /**
     * @param keyWord
     * @return Integer value of .properties file's key pair
     */
    public static Integer readPropertyInt(String keyWord) {
        return Integer.parseInt(properties.getProperty(keyWord));
    }
}
