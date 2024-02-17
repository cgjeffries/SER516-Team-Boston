package ui.metrics.cycletime;

import javafx.collections.ObservableList;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import ui.components.Icon;

public class CycleTime extends StackPane {

    private final CycleTimeService service;
    private final TabPane tabPane;

    public CycleTime() {
        this.service = new CycleTimeService();
        this.tabPane = new TabPane();
        this.init();
    }

    private void init() {
        Tab taskCycleTimeTab = createCycleTimeTab("Task", new Icon(BoxiconsRegular.CLIPBOARD), this.service.getTasks());
        Tab usCycleTimeTab = createCycleTimeTab("User Story", new Icon(BoxiconsRegular.USER), this.service.getStories());

        tabPane.getTabs().setAll(taskCycleTimeTab, usCycleTimeTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        getChildren().add(tabPane);
        this.service.start();
    }

    private Tab createCycleTimeTab(String name, Icon icon, ObservableList<XYChart.Data<String, Number>> data) {
        StackPane root = new StackPane();

        Tab tab = new Tab(name);
        tab.setGraphic(icon);

        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(this.service.runningProperty());

        CategoryAxis date = new CategoryAxis();
        date.setLabel("Date");
        NumberAxis value = new NumberAxis();
        value.setLabel("Value");

        XYChart.Series<String, Number> points = new XYChart.Series<>(data);

        AreaChart<String, Number> chart = new AreaChart<>(date, value);

        chart.getData().addAll(points);

        chart.setAnimated(false);
        chart.visibleProperty().bind(this.service.runningProperty().not());

        root.getChildren().add(chart);
        root.getChildren().add(progress);

        tab.setContent(root);

        return tab;
    }
}
