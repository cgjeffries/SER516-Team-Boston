package ui.screens;

import javafx.scene.layout.Pane;
import taiga.models.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.pbchange.PBChange;

public class PBChangeScreen extends BaseMetricConfiguration {
    private PBChange pbChange;

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param id            A unique id for the scene.
     * @param fxmlFilename  The fxml file to load for this screen.
     */
    public PBChangeScreen(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
    }

    @Override
    protected void beforeVisualizationMount() {
        this.pbChange = new PBChange();
    }

    @Override
    protected Pane visualization() {
        return this.pbChange;
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
            this.pbChange.switchSprint(sprint);

        });
        sprint_combobox.getSelectionModel().selectLast();
        this.pbChange.switchSprint(sprint_combobox.getValue());
    }

    @Override
    protected Pane parameters() {
        return null;
    }

    @Override
    protected void onFocused() {
        if (this.pbChange == null) {
            return;
        }
        sprint_combobox.getSelectionModel().selectLast();
        this.pbChange.focusFirstTab();
    }
}
