package ui.metrics.taskDefectDensity;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import ui.services.TaskDefectDensityService;
import javafx.scene.chart.PieChart;


public class TaskDefectDensity extends StackPane {
    private final TaskDefectDensityService service;
    private PieChart pieChart; // moving hard-coded pie over

    public TaskDefectDensity() {
        this.service = new TaskDefectDensityService();
        this.init();
    }

    private void init() {
        VBox.setVgrow(this, Priority.ALWAYS);    
        StackPane root = new StackPane();
        VBox.setVgrow(root, Priority.ALWAYS);

        pieChart = new PieChart();

        pieChart.setTitle("Task Defect Density");
        pieChart.getData().addAll(
            new PieChart.Data("Finished", 30),
            new PieChart.Data("Unfinished", 20),
            new PieChart.Data("Deleted", 50)
        );

        VBox container = new VBox(
            pieChart
        );
        container.setPrefHeight(VBox.USE_PREF_SIZE);
        container.setAlignment(Pos.CENTER);
        container.visibleProperty().bind(this.service.runningProperty().not());
        VBox.setVgrow(container, Priority.ALWAYS);

        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(this.service.runningProperty());

        root.getChildren().add(progress);
        root.getChildren().add(container);

        getChildren().add(root);

        this.service.start();
    }

    public void calculate(int projectId) {
        this.service.recalculate(projectId);
    }
}
