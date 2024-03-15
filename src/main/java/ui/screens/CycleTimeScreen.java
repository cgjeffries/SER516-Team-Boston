package ui.screens;

import javafx.scene.layout.Pane;
import taiga.model.query.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.cycletime.CycleTime;

public class CycleTimeScreen extends BaseMetricConfiguration {
    private CycleTime cycleTime;

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param id            A unique id for the scene.
     * @param fxmlFilename  The fxml file to load for this screen.
     */
    public CycleTimeScreen(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
    }

    @Override
    protected void beforeVisualizationMount() {
        this.cycleTime = new CycleTime();
    }

    @Override
    protected Pane visualization() {
        return this.cycleTime;
    }

    /**
     * Adds the switch sprint functionality to the sprint dropdown after the page is rendered.
     */
    @Override
    protected void afterVisualizationMount() {
        sprint_combobox.setOnAction((e) -> {
            Sprint sprint = sprint_combobox.getValue();
            if (sprint == null) {
                return;
            }
            this.cycleTime.switchSprint(sprint);
            sprint_name.textProperty().set(sprint_combobox.getValue().getName());
        });
        sprint_combobox.getSelectionModel().selectLast();
        this.cycleTime.switchSprint(sprint_combobox.getValue());
        sprint_name.textProperty().set(sprint_combobox.getValue().getName());
    }

    @Override
    protected Pane parameters() {
        return null;
    }

    @Override
    protected void onFocused() {
        if (this.cycleTime == null) {
            return;
        }
        sprint_combobox.getSelectionModel().selectLast();
        this.cycleTime.focusFirstTab();
    }
}
