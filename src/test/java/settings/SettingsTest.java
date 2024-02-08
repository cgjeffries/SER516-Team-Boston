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
import taiga.model.query.sprint.Sprint;

public class SettingsTest {

    private final String TEST_SETTINGS_FILE = "test_settings.json";

    private AppModel appModel;

    private Project p1;
    private Project p2;
    private Project p3;

    private Sprint s1;
    private Sprint s2;

    
    @BeforeEach
    public void setUp() {
        System.setProperty("user.home", System.getProperty("user.dir"));
        deleteTestSettingsFile();

        appModel = new AppModel();
        // the internal list of projects is lazy loaded, so we do this to initialize it
        appModel.getProjects();
        p1 = new Project();
        p1.setSlug("project-one");
        p1.setId(1);
        p2 = new Project();
        p2.setSlug("project-two");
        p2.setId(2);
        p3 = new Project();
        p3.setSlug("project-three");
        p3.setId(3);
        //Sprint Section for new appending
        appModel.getSprints();
        s1 = new Sprint();
        s1.setSlug("sprint-1");
        s1.setId(1);
        s2 = new Sprint();
        s2.setSlug("sprint-2");
        s2.setId(2);
    }

    @AfterEach
    public void tearDown() {
        deleteTestSettingsFile();
    }

    @Test
    public void saveAndLoadSettings() {
        Settings settings = Settings.get();
        // TODO: maybe add some utility wrapper for easily setting the slug and id
        appModel.addProject(p1);
        appModel.addProject(p2);
        appModel.setCurrentProject(p3);
        //Apending for Sprint testing
        appModel.addSprint(s1);
        appModel.addSprint(s2);
        appModel.setCurrentSprint(s2);

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
