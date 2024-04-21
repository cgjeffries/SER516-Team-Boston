package ui.metrics.taskDefectDensity;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import taiga.models.sprint.Sprint;

import ui.services.TaskDefectDensityService;
import javafx.scene.chart.PieChart;


public class TaskDefectDensity extends StackPane {
    private final TaskDefectDensityService service;

    public TaskDefectDensity() {
        this.service = new TaskDefectDensityService();
        this.init();
    }

    public void recalculate(Sprint sprint) {
        this.service.recalculate(sprint);
    }

    public TaskDefectDensityService getService() {
        return service;
    }

    private void init() {
        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(service.runningProperty());

        PieChart chart = new PieChart();
        chart.setAnimated(false);
        chart.setData(service.getTaskPieChartData());

        Label ratio = new Label();
        ratio.textProperty().bind(Bindings.format("Task Defect Density: %.2f%%", service.tddRatioProperty()));

        Label emptyText = new Label("No task defect density data for the selected sprint.");
        emptyText.visibleProperty().bind(service.validSprintSelectedProperty().not().and(service.runningProperty().not()));

        VBox.setVgrow(this, Priority.ALWAYS);
        VBox root = new VBox(ratio,chart);
        root.visibleProperty().bind(service.validSprintSelectedProperty().and(service.runningProperty().not()));
        root.setAlignment(Pos.CENTER);

        getChildren().addAll(progress, root, emptyText);
        this.service.start();
    }

}
