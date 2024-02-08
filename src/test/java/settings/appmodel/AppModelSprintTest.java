package settings.appmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import taiga.model.query.sprint.Sprint;

//Integrate this with AppModelTest when merge
public class AppModelSprintTest {

    private AppModel appModel;

    private Sprint s1;
    private Sprint s2;
    private Sprint s3;

    @BeforeEach
    public void setupSprints() {
        appModel = new AppModel();
        // need to lazy init the sprints... probably fix this for projs and sprints, bad prac
        appModel.getSprints();
        s1 = new Sprint();
        s1.setSlug("sprint-one");
        s1.setId(1);
        s2 = new Sprint();
        s2.setSlug("sprint-two");
        s2.setId(2);
        s3 = new Sprint();
        s3.setSlug("sprint-three");
        s3.setId(3);
    }
    
    @Test
    public void testAddSprint() {
        appModel.addSprint(s1);
        assertTrue(appModel.getSprints().contains(s1));
    }
    
    @Test
    public void testRemoveSprint() {
        appModel.addSprint(s1);
        appModel.removeSprint(s1);
        assertFalse(appModel.getSprints().contains(s1));
    }
}
