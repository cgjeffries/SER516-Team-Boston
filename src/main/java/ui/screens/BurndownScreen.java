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
    protected StackPane visualization() {
        return this.burndown;
    }

    @Override
    protected VBox parameters() {
        return null;
    }

    @Override
    protected void beforeVisualizationMount() {
        this.burndown = new Burndown();
        sprint_combobox.setOnAction((e) -> {
            Sprint sprint = sprint_combobox.getValue();
            if (sprint == null) {
                return;
            }
            System.out.println(sprint_combobox.getValue().getProjectExtraInfo().getName());
            System.out.println(sprint_combobox.getValue().getName());
            this.burndown.switchSprint(sprint_combobox.getValue());
        });
    }

    @Override
    protected void onFocused() {
        super.onFocused();
        if (this.burndown == null) {
            return;
        }
        this.burndown.clear();
    }
}
