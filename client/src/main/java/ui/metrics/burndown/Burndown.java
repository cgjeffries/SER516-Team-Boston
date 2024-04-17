package ui.metrics.burndown;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import taiga.models.sprint.Sprint;
import ui.components.Icon;
import ui.services.BurndownService;

import java.util.List;
import java.util.stream.Collectors;

public class Burndown extends StackPane {
    private final BurndownService service;
    private final TabPane tabPane;

    public Burndown() {
        this.service = new BurndownService();
        this.tabPane = new TabPane();
        this.init();
    }

    private void init() {
        CategoryAxis dateAxis = new CategoryAxis();
        dateAxis.setLabel("Date");
        NumberAxis pointsAxis = new NumberAxis();
        pointsAxis.setLabel("Points");
        AreaChart<String, Number> combinedChart = new AreaChart<>(dateAxis, pointsAxis);

        Tab taskBurndownTab = createBurndownTab("Task", "Fractional Story Points", new Icon(BoxiconsRegular.CLIPBOARD),
                this.service.getTaskData(), combinedChart);
        Tab usBurndownTab = createBurndownTab("User Story", "Full Story Points", new Icon(BoxiconsRegular.USER),
                this.service.getUserStoryData(), combinedChart);
        Tab bvBurndownTab = createBurndownTab("Business Value", "Business Value Points",
                new Icon(BoxiconsRegular.BRIEFCASE), this.service.getBusinessValueData(), combinedChart);
        Tab combinedBurndownTab = createCombinedBurndownTab(combinedChart);

        tabPane.getTabs().setAll(taskBurndownTab, usBurndownTab, bvBurndownTab, combinedBurndownTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        getChildren().add(tabPane);
        this.service.start();
    }

    private Tab createBurndownTab(String name, String valueUnits, Icon icon,
            ObservableMap<Sprint, BurndownService.Data> dataMap, AreaChart<String, Number> combinedChart) {
        StackPane root = new StackPane();

        Tab tab = new Tab(name);
        tab.setGraphic(icon);

        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(this.service.runningProperty());

        CategoryAxis date = new CategoryAxis();
        date.setLabel("Date");
        NumberAxis value = new NumberAxis();
        value.setLabel(valueUnits);

        AreaChart<String, Number> chart = new AreaChart<>(date, value);
        chart.setCreateSymbols(true);

        dataMap.addListener((MapChangeListener.Change<? extends Sprint, ? extends BurndownService.Data> change) -> {
            if (change.wasRemoved()) {
                chart.getData().removeIf(series -> {
                    return series.getName().equals("Ideal (" + change.getKey().getName() + ")")
                            || series.getName().equals("Current (" + change.getKey().getName() + ")");
                });
                combinedChart.getData().removeIf(series -> {
                    return series.getName().equals(name + " (" + change.getKey().getName() + ")");
                });
            }
            if (change.wasAdded()) {
                Sprint sprint = change.getKey();
                BurndownService.Data data = change.getValueAdded();

                XYChart.Series<String, Number> ideal = new XYChart.Series<>(data.getIdeal());
                ideal.setName("Ideal (" + sprint.getName() + ")");
                XYChart.Series<String, Number> tabCurrent = new XYChart.Series<>(data.getCalculated());
                tabCurrent.setName("Current (" + sprint.getName() + ")");

                // XYChart.Data CANNOT be shared, hence why tabCurrent isn't
                // passed directly to combinedChart
                // - why it doesn't work: https://stackoverflow.com/a/49774137
                // - how to copy: https://stackoverflow.com/a/53807286
                XYChart.Series<String, Number> combinedCurrent = new XYChart.Series<>(
                        name + " (" + sprint.getName() + ")",
                        data.getCalculated()
                                .stream()
                                .map(d -> new XYChart.Data<String, Number>(d.getXValue(), d.getYValue()))
                                .collect(Collectors.toCollection(FXCollections::observableArrayList)));

                chart.getData().add(ideal);
                chart.getData().add(tabCurrent);
                combinedChart.getData().add(combinedCurrent);
            }
        });

        chart.setAnimated(false);
        chart.visibleProperty().bind(this.service.runningProperty().not());

        root.getChildren().add(chart);
        root.getChildren().add(progress);

        tab.setContent(root);

        return tab;
    }

    /**
     * Choose which sprints will be displayed in the burndown graphs
     *
     * @param sprints the list of sprints to display the various burndown graphs for
     */
    public void selectSprints(List<Sprint> sprints) {
        this.selectSprints(sprints, false);
    }

    /**
     * Choose which sprints will be displayed in the burndown graphs
     *
     * @param sprints the list of sprints to display the various burndown graphs for
     * @param overlay Whether or not to make the burndown charts overlay one
     *                another. if false the
     *                burndown charts will be displayed chronologically.
     */
    public void selectSprints(List<Sprint> sprints, boolean overlay) {
        this.service.recalculate(sprints, overlay);
    }

    private Tab createCombinedBurndownTab(AreaChart<String, Number> combinedChart) {
        Tab tab = new Tab("Combined");
        tab.setGraphic(new Icon(BoxiconsRegular.CHART));

        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(this.service.runningProperty());

        // combinedChart.setTitle("Combined Burndown Chart");
        combinedChart.setAnimated(false);
        // combinedChart.setCreateSymbols(false);
        combinedChart.visibleProperty().bind(this.service.runningProperty().not());

        StackPane root = new StackPane(combinedChart, progress);
        tab.setContent(root);

        return tab;
    }

    public void focusFirstTab() {
        tabPane.getSelectionModel().selectFirst();
    }

    public ReadOnlyBooleanProperty serviceRunning() {
        return this.service.runningProperty();
    }

    public void cancel() {
        this.service.cancel();
    }
}
