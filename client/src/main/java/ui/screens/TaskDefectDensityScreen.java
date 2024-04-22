package ui.screens;

import javafx.scene.layout.Pane;
import taiga.models.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.taskDefectDensity.TaskDefectDensity;

public class TaskDefectDensityScreen extends BaseMetricConfiguration {
    private TaskDefectDensity taskDD;

    public TaskDefectDensityScreen(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
    }

    @Override
    protected void beforeVisualizationMount() {
        this.taskDD = new TaskDefectDensity();
    }


    @Override
    protected Pane visualization() {
        return this.taskDD;
    }

    @Override
    protected void afterVisualizationMount() {
        sprint_combobox.setOnAction((e) -> {
            Sprint sprint = sprint_combobox.getValue();
            if (sprint == null) {
                return;
            }
            this.taskDD.recalculate(sprint);
        });
        sprint_combobox.getSelectionModel().selectLast();
        this.taskDD.recalculate(sprint_combobox.getValue());
    }

    @Override
    protected void beforeParameterMount() {
        this.taskDD = new TaskDefectDensity();
    }

    @Override
    protected Pane parameters() {
        return null;
    }

    @Override
    protected void onFocused() {
        if (this.taskDD == null) {
            return;
        }
        sprint_combobox.getSelectionModel().selectLast();
    }
}
