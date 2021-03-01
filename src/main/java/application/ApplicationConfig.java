package application;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {
    static ApplicationConfig applicationConfig;
    static final String propertiesFile = "config.properties";
    Properties properties = new Properties();

    public static ApplicationConfig getInstance() {
        if (applicationConfig == null) {
            applicationConfig = new ApplicationConfig();
        }
        return applicationConfig;
    }

    public void loadConfig() throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFile);
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException(String.format("%s file not found", propertiesFile));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            assert inputStream != null;
            inputStream.close();
        }
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
