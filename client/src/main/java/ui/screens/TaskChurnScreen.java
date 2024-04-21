package ui.screens;

import javafx.scene.layout.Pane;
import taiga.models.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.taskchurn.TaskChurn;
import ui.metrics.taskexcess.TaskExcess;

public class TaskChurnScreen extends BaseMetricConfiguration{

    private TaskChurn taskChurn;
    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param id            A unique id for the scene.
     * @param fxmlName      The fxml file to load for this screen.
     */
    public TaskChurnScreen(ScreenManager screenManager, String id, String fxmlName) {
        super(screenManager, id, fxmlName);
    }
    @Override
    protected void beforeVisualizationMount(){
        this.taskChurn = new TaskChurn();
    }
    @Override
    protected Pane visualization() {
        return this.taskChurn;
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
            this.taskChurn.recalculate(sprint);
        });
        sprint_combobox.getSelectionModel().selectLast();
        this.taskChurn.recalculate(sprint_combobox.getValue());
    }

    @Override
    protected void onFocused() {
        if (this.taskChurn == null) {
            return;
        }
        sprint_combobox.getSelectionModel().selectLast();
    }
}
