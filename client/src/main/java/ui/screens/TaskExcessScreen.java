package ui.screens;

import javafx.scene.layout.Pane;
import taiga.models.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.taskexcess.TaskExcess;

public class TaskExcessScreen extends BaseMetricConfiguration{


    private TaskExcess taskExcess;
    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param id            A unique id for the scene.
     * @param fxmlName      The fxml file to load for this screen.
     */
    public TaskExcessScreen(ScreenManager screenManager, String id, String fxmlName) {
        super(screenManager, id, fxmlName);
    }

    @Override
    protected void beforeVisualizationMount(){
        this.taskExcess = new TaskExcess();
    }

    @Override
    protected Pane visualization() {
        return this.taskExcess;
    }

    @Override
    protected Pane parameters() {
        return null;
    }

    @Override
    protected void afterVisualizationMount() {
        sprint_combobox.setOnAction((e) -> {
            Sprint sprint = sprint_combobox.getValue();
            if (sprint == null) {
                return;
            }
            this.taskExcess.recalculate(sprint);
        });
        sprint_combobox.getSelectionModel().selectLast();
        this.taskExcess.recalculate(sprint_combobox.getValue());
    }

    @Override
    protected void onFocused() {
        if (this.taskExcess == null) {
            return;
        }
        sprint_combobox.getSelectionModel().selectLast();
    }
}

