package ui.metrics.taskexcess;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import taiga.models.sprint.Sprint;
import ui.services.TaskExcessService;

public class TaskExcess extends StackPane {
    private final TaskExcessService service;

    public TaskExcess() {
        this.service = new TaskExcessService();
        this.init();
    }

    public TaskExcessService getService() {
        return service;
    }

    public void recalculate(Sprint sprint) {
        this.service.recalculate(sprint);
    }
    
    private void init() {
        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(service.runningProperty());

        PieChart chart = new PieChart();
        chart.setAnimated(false);
        chart.visibleProperty().bind(service.validSprintSelectedProperty().and(service.runningProperty().not()));
        chart.setData(service.getTaskPieChartData());

        Label ratio = new Label();
        ratio.textProperty().bind(Bindings.format("Task Excess: %.2f%%", service.taskExcessRatioProperty()));

        Label emptyText = new Label("No task excess data for the selected sprint.");
        emptyText.visibleProperty().bind(service.validSprintSelectedProperty().not());

        VBox.setVgrow(this, Priority.ALWAYS);
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(progress, ratio, chart, emptyText);
        getChildren().add(root);
        this.service.start();
    }
}
