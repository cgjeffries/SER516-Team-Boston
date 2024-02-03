package settings;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import settings.appmodel.AppModel;
import taiga.model.query.project.Project;

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
        // TODO: maybe add some utility wrapper for easily setting the slug and id
        appModel.addProject(new Project());
        appModel.addProject(new Project());
        appModel.setCurrentProject(new Project());
        settings.appModel = appModel;

        settings.save();
        settings.load();

    }

    @Test
    public void saveAndLoadSettingsEmptyFile() {
        Settings settings = Settings.get();
        // TODO: should be mocked

        settings.save();
        settings.load();

        assertNotNull(settings.getAppModel());
        assertNotNull(settings.getAppModel().getProjects());
    }

    private void deleteTestSettingsFile() {
        // TODO: should be mocked
        Path testSettingsPath = Paths.get(System.getProperty("user.home"), ".boston", TEST_SETTINGS_FILE);
        try {
            Files.deleteIfExists(testSettingsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
