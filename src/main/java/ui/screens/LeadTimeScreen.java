package ui.screens;

import javafx.scene.layout.Pane;
import taiga.model.query.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.leadtime.LeadTime;

public class LeadTimeScreen extends BaseMetricConfiguration {
    private LeadTime leadTime;

    public LeadTimeScreen(ScreenManager screenManager, String id, String fxmlName) {
        super(screenManager, id, fxmlName);
    }

    @Override
    protected void beforeVisualizationMount() {
        this.leadTime = new LeadTime();
    }

    @Override
    protected Pane visualization() {
        return this.leadTime;
    }

    @Override
    protected void afterVisualizationMount() {
        sprint_combobox.setOnAction((e) -> {
            Sprint sprint = sprint_combobox.getValue();
            if (sprint == null) {
                return;
            }
            this.leadTime.switchSprint(sprint);
        });
        sprint_combobox.getSelectionModel().selectLast();
        this.leadTime.switchSprint(sprint_combobox.getValue());
    }

    @Override
    protected Pane parameters() {
        return null;
    }

    @Override
    protected void onFocused() {
        if (this.leadTime == null) {
            return;
        }
        sprint_combobox.getSelectionModel().selectLast();
        this.leadTime.focusFirstTab();
    }
}
