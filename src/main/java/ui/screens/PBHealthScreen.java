package ui.screens;

import atlantafx.base.theme.Styles;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import taiga.model.query.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.pbHealth.PBHealth;

public class PBHealthScreen extends BaseMetricConfiguration {
    private PBHealth pbHealth;
    private Spinner<Integer> goodHealthThreshold;
    private Spinner<Integer> fairHealthThreshold;
    private Spinner<Integer> poorHealthThreshold;

    public PBHealthScreen(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
    }

    @Override
    protected void beforeVisualizationMount() {
        this.pbHealth = new PBHealth();
        hideSprintParameter();
    }

    @Override
    protected Pane visualization() {
        return this.pbHealth;
    }

    private Spinner<Integer> createThresholdSpinner(int initial) {
        Spinner<Integer> spinner = new Spinner<>(0, Integer.MAX_VALUE, initial); //TODO nothing i just dont like this
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        return spinner;
    }

    @Override
    protected void beforeParameterMount() {
        this.goodHealthThreshold = createThresholdSpinner(8);
        this.fairHealthThreshold = createThresholdSpinner(5);
        this.poorHealthThreshold = createThresholdSpinner(2);
    }

    private VBox createThresholdBox(String name, Spinner<Integer> thresholdSpinner) {
        return new VBox(new Label(name), thresholdSpinner);
    }

    @Override
    protected Pane parameters() {
        HBox container = new HBox(
                createThresholdBox("Good: ", goodHealthThreshold),
                createThresholdBox("Fair: ", fairHealthThreshold),
                createThresholdBox("Poor: ", poorHealthThreshold)
        );
        container.setSpacing(5);
        return container;
    }

    @Override
    protected void afterVisualizationMount() {


    }

    @Override
    protected void onFocused() {

    }
}
