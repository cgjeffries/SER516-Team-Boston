package settings;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import settings.appmodel.AppModel;
import settings.appmodel.Project;

public class SettingsTest {
    private final String TEST_SETTINGS_FILE = "test_settings.json";

    @BeforeEach
    public void setUp() {
        System.setProperty("user.home", System.getProperty("user.dir"));
        deleteTestSettingsFile();
    }

    @AfterEach
    public void tearDown() {
        deleteTestSettingsFile();
    }

    @Test
    public void saveAndLoadSettings() {
        Settings settings = Settings.get();
        AppModel appModel = new AppModel();
        appModel.addProject(new Project("project1", 1));
        appModel.addProject(new Project("project2", 2));
        appModel.setCurrentProject(new Project("project1", 1));
        settings.appModel = appModel;

        settings.save();
        settings.load();

    }

    @Test
    public void saveAndLoadSettingsEmptyFile() {
        Settings settings = Settings.get();

        settings.save();
        settings.load();

        assertNotNull(settings.getAppModel());
        assertNotNull(settings.getAppModel().getProjects());
    }

    private void deleteTestSettingsFile() {
        Path testSettingsPath = Paths.get(System.getProperty("user.home"), ".boston", TEST_SETTINGS_FILE);
        try {
            Files.deleteIfExists(testSettingsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
