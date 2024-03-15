package ui.metrics.burndown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javafx.scene.chart.*;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import taiga.model.query.sprint.Sprint;
import ui.components.Icon;
import ui.services.BurndownService;

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

        tabPane.getTabs().setAll(taskBurndownTab, usBurndownTab, bvBurndownTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        getChildren().add(tabPane);
        this.service.start();
    }

    private Tab createBurndownTab(String name, String valueUnits, Icon icon, HashMap<Sprint, BurndownService.Data> dataMap) {
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

        for(Sprint sprint : dataMap.keySet()) {
            BurndownService.Data data = dataMap.get(sprint);

            XYChart.Series<String, Number> ideal = new XYChart.Series<>(data.getIdeal());
            ideal.setName("Ideal (" + sprint.getName() + ")");
            XYChart.Series<String, Number> current = new XYChart.Series<>(data.getCalculated());
            current.setName("Current (" + sprint.getName() + ")");

            chart.getData().addAll(ideal, current);
        }

        chart.setAnimated(false);
        chart.visibleProperty().bind(this.service.runningProperty().not());

        root.getChildren().add(chart);
        root.getChildren().add(progress);

        tab.setContent(root);

        return tab;
    }

    public void switchSprint(Sprint sprint) {
        this.service.recalculate(new ArrayList<>(Arrays.asList(sprint))); //TODO: actually pass multiple sprints
    }

    public void focusFirstTab() {
        tabPane.getSelectionModel().selectFirst();
    }

    public void cancel() {
        this.service.cancel();
    }
}
