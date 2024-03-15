package ui.screens;

import javafx.scene.layout.Pane;
import taiga.model.query.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.scopechange.ScopeChange;

public class ScopeChangeScreen extends BaseMetricConfiguration {
    private ScopeChange scopeChange;

    public ScopeChangeScreen(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
    }

    @Override
    protected void beforeVisualizationMount() {
        // Assuming ScopeChange is similar to PBHealth but for displaying scope change info
        this.scopeChange = new ScopeChange();
    }

    @Override
    protected Pane visualization() {
        return this.scopeChange;
    }

    @Override
    protected Pane parameters() {
        // Implement if there are any parameters to configure
        return null;
    }

    @Override
    protected void afterVisualizationMount() {
        sprint_combobox.setOnAction((e) -> {
            Sprint sprint = sprint_combobox.getValue();
            if (sprint == null) {
                return;
            }
            this.scopeChange.recalculate(sprint);
            sprint_name.textProperty().set(sprint_combobox.getValue().getName());
        });
        sprint_combobox.getSelectionModel().selectLast();
        this.scopeChange.recalculate(sprint_combobox.getValue());
        sprint_name.textProperty().set(sprint_combobox.getValue().getName());
    }

    @Override
    protected void onFocused() {
        if (this.scopeChange == null) {
            return;
        }
        sprint_combobox.getSelectionModel().selectLast();
    }
}
