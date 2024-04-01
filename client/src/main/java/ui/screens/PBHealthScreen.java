package ui.screens;

import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import settings.Settings;
import ui.components.screens.ScreenManager;
import ui.metrics.pbHealth.PBHealth;

public class PBHealthScreen extends BaseMetricConfiguration {
    private PBHealth pbHealth;
    private Spinner<Double> goodHealthThreshold;
    private Spinner<Double> fairHealthThreshold;
    private Spinner<Double> poorHealthThreshold;

    public PBHealthScreen(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
    }

    @Override
    protected void beforeVisualizationMount() {
        hideSprintParameter();
    }

    @Override
    protected Pane visualization() {
        return this.pbHealth;
    }

    private Spinner<Double> createThresholdSpinner(double initial) {
        Spinner<Double> spinner = new Spinner<>();
        spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, initial, 0.1));
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        return spinner;
    }

    @Override
    protected void beforeParameterMount() {
        this.poorHealthThreshold = createThresholdSpinner(0.1);
        this.fairHealthThreshold = createThresholdSpinner(0.5);
        this.goodHealthThreshold = createThresholdSpinner(0.8);
        this.pbHealth = new PBHealth(this.poorHealthThreshold.valueProperty(), this.fairHealthThreshold.valueProperty(), this.goodHealthThreshold.valueProperty());
    }

    private VBox createThresholdBox(String name, Spinner<Double> thresholdSpinner) {
        return new VBox(new Label(name), thresholdSpinner);
    }

    @Override
    protected Pane parameters() {
        HBox container = new HBox(
                createThresholdBox("Poor: ", poorHealthThreshold),
                createThresholdBox("Fair: ", fairHealthThreshold),
                createThresholdBox("Good: ", goodHealthThreshold)
        );
        container.setSpacing(5);
        return container;
    }

    @Override
    protected void onFocused() {
        if (this.pbHealth == null) {
            return;
        }
        this.pbHealth.calculate(Settings.get().getAppModel().getCurrentProject().get().getId());
    }
}
