package ui.services;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import taiga.model.query.common.Project;
import taiga.model.query.sprint.Sprint;
import ui.util.DateUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CycleTimeServiceTest {

    @BeforeAll
    public static void initJavaFXToolkit() {
        // Initialize JavaFX Toolkit, else won't run
        Platform.startup(() -> {});
    }

    @Test
    public void testGetTasks() {
        CycleTimeService cycleTimeService = new CycleTimeService();
        assertNotNull(cycleTimeService.getTasks());
    }

    @Test
    public void testGetStories() {
        CycleTimeService cycleTimeService = new CycleTimeService();
        assertNotNull(cycleTimeService.getStories());
    }

    @Test
    public void testRecalculateWithSprint() {
        CycleTimeService cycleTimeService = new CycleTimeService();
        Sprint sprint = new Sprint();
        Integer projectId = 1;
        Date startDate = DateUtil.toDate(LocalDate.now().minusDays(7));
        Date endDate = DateUtil.toDate(LocalDate.now());
        sprint.setProject(projectId);
        sprint.setEstimatedStart(startDate);
        sprint.setEstimatedFinish(endDate);
        cycleTimeService.recalculate(sprint);
        assertEquals(sprint.getProject(), cycleTimeService.getProjectId());
        assertEquals(sprint.getEstimatedStart(), cycleTimeService.getStartDate());
        assertEquals(sprint.getEstimatedFinish(), cycleTimeService.getEndDate());
    }

    @Test
    public void testRecalculateWithProjectIdAndDates() {
        CycleTimeService cycleTimeService = new CycleTimeService();
        Integer projectId = 1;
        Date startDate = DateUtil.toDate(LocalDate.now().minusDays(7));
        Date endDate = DateUtil.toDate(LocalDate.now());
        cycleTimeService.recalculate(projectId, startDate, endDate);
        assertEquals(projectId, cycleTimeService.getProjectId());
        assertEquals(startDate, cycleTimeService.getStartDate());
        assertEquals(endDate, cycleTimeService.getEndDate());
    }
}
