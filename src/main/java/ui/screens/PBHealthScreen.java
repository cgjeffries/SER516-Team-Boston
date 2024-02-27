package ui.screens;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.components.screens.ScreenManager;
import ui.metrics.pbHealth.PBHealth;

public class PBHealthScreen extends BaseMetricConfiguration {
    private PBHealth pbHealth;

    /**
     * Create a screen instance for PB Health configuration
     *
     * @param screenManager a ScreenManager instance
     * @param id            A unique id for the scene.
     * @param fxmlFilename  The fxml file to load for this screen.
     */
    public PBHealthScreen(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
    }

    @Override
    protected void beforeVisualizationMount() {
        // Initialize PBHealth component
        this.pbHealth = new PBHealth();
    }

    @Override
    protected Pane visualization() {
        // Return PBHealth component as main viz pane
        return this.pbHealth;
    }

    @Override
    protected Pane parameters() {
        // To do: UI for configuring PB Health parameters here?

        VBox parameterBox = new VBox();
        
        return parameterBox;
    }

    @Override
    protected void afterVisualizationMount() {

    }

    @Override
    protected void onFocused() {

    }
}
