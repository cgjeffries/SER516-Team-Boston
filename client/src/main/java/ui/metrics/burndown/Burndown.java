package ui.metrics.burndown;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import taiga.models.sprint.Sprint;
import ui.components.Icon;
import ui.services.BurndownService;

import java.util.List;

public class Burndown extends StackPane {
    private final BurndownService service;
    private final TabPane tabPane;

    public Burndown() {
        this.service = new BurndownService();
        this.tabPane = new TabPane();
        this.init();
    }

    private void init() {
        Tab taskBurndownTab = createBurndownTab("Task", "Fractional Story Points", new Icon(BoxiconsRegular.CLIPBOARD), this.service.getTaskData());
        Tab usBurndownTab = createBurndownTab("User Story", "Full Story Points", new Icon(BoxiconsRegular.USER), this.service.getUserStoryData());
        Tab bvBurndownTab = createBurndownTab("Business Value", "Business Value Points", new Icon(BoxiconsRegular.BRIEFCASE), this.service.getBusinessValueData());
        Tab combinedBurndownTab = createCombinedBurndownTab(this.service.getTaskData(), this.service.getUserStoryData(), this.service.getBusinessValueData());

        tabPane.getTabs().setAll(taskBurndownTab, usBurndownTab, bvBurndownTab, combinedBurndownTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        getChildren().add(tabPane);
        this.service.start();
    }

    private Tab createBurndownTab(String name, String valueUnits, Icon icon, ObservableMap<Sprint, BurndownService.Data> dataMap) {
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

        dataMap.addListener((MapChangeListener.Change<? extends Sprint, ? extends BurndownService.Data> change) -> {
            if (change.wasRemoved()) {
                chart.getData().removeIf(series -> {
                    return series.getName().equals("Ideal (" + change.getKey().getName() + ")") || series.getName().equals("Current (" + change.getKey().getName() + ")");
                });
            }
            if (change.wasAdded()) {
                Sprint sprint = change.getKey();
                BurndownService.Data data = change.getValueAdded();

                XYChart.Series<String, Number> ideal = new XYChart.Series<>(data.getIdeal());
                ideal.setName("Ideal (" + sprint.getName() + ")");
                XYChart.Series<String, Number> current = new XYChart.Series<>(data.getCalculated());
                current.setName("Current (" + sprint.getName() + ")");

                chart.getData().addAll(ideal, current);
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
     * @param overlay Whether or not to make the burndown charts overlay one another. if false the
     *                burndown charts will be displayed chronologically.
     */
    public void selectSprints(List<Sprint> sprints, boolean overlay) {
        this.service.recalculate(sprints, overlay);
    }


    private Tab createCombinedBurndownTab(
        ObservableMap<Sprint, BurndownService.Data> taskData,
        ObservableMap<Sprint, BurndownService.Data> userStoryData,
        ObservableMap<Sprint, BurndownService.Data> businessValueData) {
        
        Tab tab = new Tab("Combined");
        tab.setGraphic(new Icon(BoxiconsRegular.CHART));
    
        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(this.service.runningProperty());

        CategoryAxis date = new CategoryAxis();
        date.setLabel("Date");
        NumberAxis value = new NumberAxis();
        value.setLabel("Points");

        AreaChart<String, Number> chart = new AreaChart<>(date, value);

        addSeriesToChart(chart, "Tasks", taskData);
        addSeriesToChart(chart, "User Stories", userStoryData);
        addSeriesToChart(chart, "Business Values", businessValueData);
    
        StackPane root = new StackPane(chart);
        tab.setContent(root);
    
        return tab;
    }
        
    private void addSeriesToChart(AreaChart<String, Number> chart, String seriesName, 
        ObservableMap<Sprint, BurndownService.Data> dataMap) {
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(seriesName);

        // assuming the first sprint's dates as the reference for all series
        if (!dataMap.isEmpty()) {
            Sprint firstSprint = dataMap.keySet().iterator().next();
            BurndownService.Data firstData = dataMap.get(firstSprint);

            for (int i = 0; i < firstData.getCalculated().size(); i++) {
                String dateLabel = "Day " + (i + 1);
                double value = firstData.getCalculated().get(i).getYValue().doubleValue();
                series.getData().add(new XYChart.Data<>(dateLabel, value));
            }
        }

        chart.getData().add(series);
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
