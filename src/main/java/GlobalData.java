import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GlobalData {


    public static String getTaigaURL() {
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return "default_taiga_url";
            }

            Properties properties = new Properties();
            properties.load(input);
            return properties.getProperty("TAIGA_API_ENDPOINT");
        } catch (IOException e) {
            e.printStackTrace();
            return "default_taiga_url";
        }
    }

        
}
