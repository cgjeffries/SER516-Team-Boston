package settings.appmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taiga.models.project.Project;

import static org.junit.jupiter.api.Assertions.*;

public class AppModelTest {
    private AppModel appModel;

    private Project p1;
    private Project p2;
    private Project p3;

    @BeforeEach
    public void setupProjects() {
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
    }

    @Test
    public void testAddProject() {
        appModel.addProject(p1);
        assertTrue(appModel.getProjects().contains(p1));
    }

    @Test
    public void testRemoveProject() {
        appModel.addProject(p1);
        appModel.removeProject(p1);
        assertFalse(appModel.getProjects().contains(p1));
    }

    @Test
    public void testSetCurrentProject() {
        appModel.addProject(p1);
        appModel.addProject(p2);
        appModel.addProject(p3);
        appModel.setCurrentProject(p2);
        assertEquals(p2, appModel.getCurrentProject().get());
    }
}