package ui.screens;

import javafx.scene.layout.Pane;
import taiga.models.sprint.Sprint;
import settings.Settings;
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
        this.taskDD.calculate(Settings.get().getAppModel().getCurrentProject().get().getId());
    }
}
