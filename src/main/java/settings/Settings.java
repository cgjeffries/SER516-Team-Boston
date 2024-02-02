package settings;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import settings.appmodel.AppModel;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Settings {
    private final String SETTINGS_FILE = "settings.json";
    private final Path SETTINGS_PATH = Paths.get(System.getProperty("user.home"), "boston");
    private final Path FULL_SETTINGS_PATH = SETTINGS_PATH.resolve(SETTINGS_FILE);
    private static Settings settings;
    private AppModel appModel;

    private Settings() {

    }

    public static Settings get() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    public AppModel getAppModel() {
        return appModel;
    }

    public void load() {
        Gson gson = new Gson();
        try {
            Files.createDirectories(SETTINGS_PATH);
            FULL_SETTINGS_PATH.toFile().createNewFile();
            try (FileReader fileReader = new FileReader(FULL_SETTINGS_PATH.toFile())) {
                JsonReader jsonReader = new JsonReader(fileReader);
                appModel = gson.fromJson(jsonReader, AppModel.class);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void save() {
        // TODO: implment saving the AppModel
    }
}
