package settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.nio.charset.StandardCharsets;
import settings.appmodel.AppModel;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

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
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        try {
            Files.createDirectories(SETTINGS_PATH);
            boolean created = FULL_SETTINGS_PATH.toFile().createNewFile();
            if (created) {
                System.out.println("Created settings.json at " + FULL_SETTINGS_PATH.toUri());
            }
            try (FileReader fileReader = new FileReader(FULL_SETTINGS_PATH.toFile(), StandardCharsets.UTF_8)) {
                JsonReader jsonReader = new JsonReader(fileReader);
                AppModel result = gson.fromJson(jsonReader, AppModel.class);
                appModel = Objects.requireNonNullElseGet(result, () -> new AppModel());
                appModel.loadUser();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void save() {
        System.out.println("Saving settings");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        try (FileWriter fileWriter = new FileWriter(FULL_SETTINGS_PATH.toFile(), StandardCharsets.UTF_8)) {
            gson.toJson(appModel, fileWriter);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
