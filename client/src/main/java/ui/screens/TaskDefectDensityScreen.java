package ui.screens;

import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import settings.Settings;
import ui.components.screens.ScreenManager;
import ui.services.TaskDefectDensityService;

public class TaskDefectDensityScreen extends BaseMetricConfiguration {
    private PieChart pieChart;
    //Needs to be used and potentiall replace piechart as main element
    private TaskDefectDensityService taskDefectDensity;

    public TaskDefectDensityScreen(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
        initializeUI();
    }

    private void initializeUI() {
        //TODO PieChart still hardcoded, need service/class to call update? Weird as service has no params
        pieChart = new PieChart();
        pieChart.setTitle("Task Defect Density");
        pieChart.getData().addAll(
            new PieChart.Data("Finished", 30),
            new PieChart.Data("Unfinished", 20),
            new PieChart.Data("Deleted", 50)
        );
        if (taskDefectDensity != null) {
            taskDefectDensity.recalculate(Settings.get().getAppModel().getCurrentProject().get().getId());
        }
        // hideSprintParameter(); Keeps breaking app, need a second set of eyes.
        this.taskDefectDensity = new TaskDefectDensityService();
    }

    @Override
    protected Pane visualization() {
        StackPane pane = new StackPane(pieChart);
        return pane;
        //TODO Needs to return the churn class when connected, but might only need service
    }

    @Override
    protected Pane parameters() {
        // Currently defect density does task info all at once? need a second set of eyes. Might have no parameters in current state
        return null;

    }

    @Override
    protected void onFocused() {
        if (this.taskDefectDensity == null) {
            return;
        }
        taskDefectDensity.recalculate(Settings.get().getAppModel().getCurrentProject().get().getId());
    }
}
