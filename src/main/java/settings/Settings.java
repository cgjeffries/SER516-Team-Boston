package settings;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import settings.appmodel.AppModel;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Settings {
    private final String SETTINGS_FILE = "settings.json";
    private final Path SETTINGS_PATH = Paths.get(System.getProperty("user.home"), ".boston");
    protected final Path FULL_SETTINGS_PATH = SETTINGS_PATH.resolve(SETTINGS_FILE);
    private static Settings settings;
    protected AppModel appModel;

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
        System.out.println("Loading settings");
        Gson gson = new Gson();
        try {
            Files.createDirectories(SETTINGS_PATH);
            boolean created = FULL_SETTINGS_PATH.toFile().createNewFile();
            if (created) {
                System.out.println("Created settings.json at " + FULL_SETTINGS_PATH.toUri());
            }
            try (FileReader fileReader = new FileReader(FULL_SETTINGS_PATH.toFile())) {
                JsonReader jsonReader = new JsonReader(fileReader);
                appModel = gson.fromJson(jsonReader, AppModel.class);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void save() {
        System.out.println("Saving settings");
        Gson gson = new Gson();
        try (FileWriter fileWriter = new FileWriter(FULL_SETTINGS_PATH.toFile())) {
            gson.toJson(appModel, fileWriter);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
