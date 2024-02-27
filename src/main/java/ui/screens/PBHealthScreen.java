package ui.screens;

import javafx.scene.layout.Pane;
import taiga.model.query.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.pbHealth.PBHealth;

public class PBHealthScreen extends BaseMetricConfiguration {
    private PBHealth pbHealth;

    public PBHealthScreen(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
    }

    @Override
    protected void beforeVisualizationMount() {
        this.pbHealth = new PBHealth();
    }

    @Override
    protected Pane visualization() {
        return this.pbHealth;
    }

    @Override
    protected Pane parameters() {
        return null;
    }

    @Override
    protected void afterVisualizationMount() {
        

    }

    @Override
    protected void onFocused() {

    }
}
