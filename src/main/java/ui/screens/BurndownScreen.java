package ui.screens;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import taiga.model.query.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.burndown.Burndown;

public class BurndownScreen extends BaseMetricConfiguration {

    private Burndown burndown;

    public BurndownScreen(ScreenManager screenManager, String name) {
        super(screenManager, name);
    }

    @Override
    protected void beforeVisualizationMount() {
        this.burndown = new Burndown();
    }

    @Override
    protected StackPane visualization() {
        return this.burndown;
    }

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
