package settings.appmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AppModelTest {
    private AppModel appModel;
    
    @Test
    public void testAddProject() {
        appModel = new AppModel();
        Project project = new Project("test",1);
        appModel.addProject(project);
        assertTrue(appModel.getProjects().contains(project));
    }
    
    @Test
    public void testRemoveProject() {
        appModel = new AppModel();
        Project project = new Project("test2",2);
        appModel.addProject(project);
        appModel.removeProject(project);
        assertFalse(appModel.getProjects().contains(project));
    }
    
    @Test
    public void testSetCurrentProject() {
        appModel = new AppModel();
        Project project1 = new Project("test",1);
        Project project2 = new Project("test",2);
        
        appModel.addProject(project1);
        appModel.addProject(project2);
        
        appModel.setCurrentProject(project2);
        
        assertEquals(project2, appModel.getCurrentProject());
    }
}