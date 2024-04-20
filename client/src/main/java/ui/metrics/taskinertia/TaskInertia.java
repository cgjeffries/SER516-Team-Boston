package ui.metrics.taskinertia;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import settings.Settings;
import ui.services.TaskInertiaService;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskInertia extends StackPane {

    private final TaskInertiaService service;


    public TaskInertia() {
        this.service = new TaskInertiaService();
        init();
    }

    private void init() {

        CategoryAxis dateAxis = new CategoryAxis();
        dateAxis.setLabel("Date");
        NumberAxis percentAxis = new NumberAxis();
        percentAxis.setLabel("Task Inertia (%)");

        BarChart<String, Number> chart = new BarChart<>(dateAxis, percentAxis);
        chart.visibleProperty().bind(this.service.runningProperty().not());
        chart.setAnimated(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Task Inertia (%)");
        series.dataProperty().bind(Bindings.createObjectBinding(() ->
                this.service.getInertia()
                        .entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByKey())
                        .map(entry -> new XYChart.Data<>(entry.getKey(), (Number) (entry.getValue() * 100)))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)), this.service.getInertia()));
        series.dataProperty().addListener(observable -> {
            for (final XYChart.Data<String, Number> data : series.getData()) {
                Tooltip.install(data.getNode(), new Tooltip(String.format("Inertia: %.2f%%", (Double) data.getYValue())));
            }
        });
        chart.getData().addAll(series);

        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(this.service.runningProperty());

        StackPane root = new StackPane(chart, progress);

        getChildren().add(root);
    }

    public void recalculate(LocalDate start, LocalDate end) {
        this.service.recalculate(Settings.get().getAppModel().getCurrentProject().get().getId(), start, end);
    }
}