package ui.screens;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import taiga.model.query.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.burndown.Burndown;

public class BurndownScreen extends BaseMetricConfiguration {

    private Burndown burndown;

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param id            A unique id for the scene.
     * @param fxmlFilename  The fxml file to load for this screen.
     */
    public BurndownScreen(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
    }

    @Override
    protected void beforeVisualizationMount() {
        this.burndown = new Burndown();
    }

    @Override
    protected StackPane visualization() {
        return this.burndown;
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
            this.burndown.switchSprint(sprint);
        });
        sprint_combobox.getSelectionModel().selectLast();
        this.burndown.switchSprint(sprint_combobox.getValue());
    }

    @Override
    protected VBox parameters() {
        return null;
    }

    @Override
    protected void onFocused() {
        if (this.burndown == null) {
            return;
        }
        sprint_combobox.getSelectionModel().selectLast();
        this.burndown.focusFirstTab();
    }
}
