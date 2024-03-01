package ui.screens;

import javafx.scene.layout.Pane;
import ui.components.screens.ScreenManager;
import ui.metrics.scopechange.ScopeChange;

public class ScopeChangeScreen extends BaseMetricConfiguration {
    private ScopeChange scopeChange;

    public ScopeChangeScreen(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
        beforeVisualizationMount();
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
        // Any post-mount actions
    }

    @Override
    protected void onFocused() {
        // Actions when the screen is focused
    }
}
