package ui.metrics.taskexcess;

import javafx.beans.binding.Bindings;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import taiga.models.sprint.Sprint;
import ui.components.Icon;
import ui.services.TaskExcessService;

public class TaskExcess extends StackPane {
    private final TaskExcessService service;
    private final TabPane tabPane;
    public TaskExcess() {
        this.service = new TaskExcessService();
        this.tabPane = new TabPane();
        this.init();
    }

    public TaskExcessService getService() {
        return service;
    }

    public void recalculate(Sprint sprint) {
        this.service.recalculate(sprint);
    }
    private void init() {
        Tab pieChartTab = createPieChartTab("Task Excess", new Icon(BoxiconsRegular.PIE_CHART));

        tabPane.getTabs().add(pieChartTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(service.runningProperty());

        PieChart chart = new PieChart();
        chart.visibleProperty().bind(service.validSprintSelectedProperty());

        Label ratio = new Label();
        ratio.textProperty().bind(Bindings.format("Task Excess: %.2f%%", service.taskExcessRatioProperty()));

        Label emptyText = new Label("No task excess data for the selected sprint.");
        emptyText.visibleProperty().bind(service.validSprintSelectedProperty().not());

        VBox.setVgrow(this, Priority.ALWAYS);
        StackPane root = new StackPane(tabPane, progress, chart, emptyText);
        getChildren().add(root);
        this.service.start();
    }


    private Tab createPieChartTab(String name, Icon icon) {
        VBox root = new VBox();
        Tab tab = new Tab(name);
        tab.setGraphic(icon);

        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(service.runningProperty());

        PieChart chart = new PieChart();
        chart.setData(service.getTaskPieChartData());

        service.newTaskCountProperty().addListener((obs, oldVal, newVal) -> updateChartData(chart));
        service.totalTaskCountProperty().addListener((obs, oldVal, newVal) -> updateChartData(chart));
        service.validSprintSelectedProperty().addListener((obs, oldVal, newVal) -> updateChartData(chart));

        Label ratio = new Label();
        ratio.textProperty().bind(Bindings.format("Task Excess: %.2f%%", service.taskExcessRatioProperty()));

        root.getChildren().addAll(progress, ratio, chart);
        VBox.setVgrow(chart, Priority.ALWAYS);

        tab.setContent(root);
        return tab;
    }

    private void updateChartData(PieChart chart) {
        if (service.validSprintSelectedProperty().get()) {
            chart.setData(service.getTaskPieChartData());
        } else {
            chart.getData().clear();
        }
    }
}
