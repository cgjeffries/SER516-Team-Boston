package ui.metrics.taskchurn;

import javafx.collections.ListChangeListener;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import taiga.models.sprint.Sprint;
import ui.services.TaskChurnService;

public class TaskChurn extends StackPane {
    private final TaskChurnService service;

    public TaskChurn() {
        this.service = new TaskChurnService();
        this.init();
    }

    private void init() {
        
        CategoryAxis dateAxis = new CategoryAxis();
        dateAxis.setLabel("Date");
        NumberAxis percentAxis = new NumberAxis();
        percentAxis.setLabel("Task Churn (Tasks added/modified)");

        LineChart<String, Number> chart = new LineChart<>(dateAxis, percentAxis);
        chart.visibleProperty().bind(this.service.runningProperty().not());
        chart.setAnimated(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>(this.service.getTaskChurnItems());
        series.setName("Task Churn");
        series.getData().addListener((ListChangeListener<? super XYChart.Data<String, Number>>) e -> {
            for (final XYChart.Data<String, Number> data : series.getData()) {
                Tooltip.install(data.getNode(), new Tooltip("Churn: " + data.getYValue()));
            }
        });
        chart.getData().addAll(series);

        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(this.service.runningProperty());

        StackPane root = new StackPane(chart, progress);

        getChildren().add(root);
    }

    public TaskChurnService getService() {
        return service;
    }

    public void recalculate(Sprint sprint) {
        this.service.recalculate(sprint);
    }
}
