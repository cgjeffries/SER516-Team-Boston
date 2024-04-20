package ui.metrics.taskinertia;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import settings.Settings;
import ui.services.TaskInertiaService;

import java.time.LocalDate;
import java.util.Comparator;
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

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.dataProperty().bind(Bindings.createObjectBinding(() ->
                this.service.getInertia()
                        .entrySet()
                        .stream()
                        .sorted(Comparator.comparing(e -> e.getKey()))
                        .map(entry -> new XYChart.Data<>(entry.getKey(), (Number) (entry.getValue() * 100)))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)), this.service.getInertia()));
        chart.getData().addAll(series);

        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(this.service.runningProperty());

        StackPane root = new StackPane(chart, progress);

        getChildren().add(root);
    }

    public void recalculate(LocalDate start, LocalDate end) {
        this.service.recalculate(Settings.get().getAppModel().getCurrentProject().get().getId(), start, end);
    }

//    private void updateChartData(BarChart<String, Number> barChart) {
//        LocalDate start = startDate.get();
//        LocalDate end = endDate.get();
//        // TreeMap<LocalDate, Float> taskInertiaMap = TaskInertia.getTaskInertia(projectId, authToken, endpoint, start, end);
//
//        // Clear previous data from the chart
//        barChart.getData().clear();
//
//        // Assuming CategoryAxis is your X axis
//        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
//        xAxis.getCategories().clear(); // Clear previous categories
//
//        // Create a new series for the data
//        XYChart.Series<String, Number> series = new XYChart.Series<>();
//        series.setName("Task Inertia");
//
//        // Generate a category for every day in the range to ensure even spacing
//        LocalDate currentDate = start;
//        while (!currentDate.isAfter(end)) {
//            String category = currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//            xAxis.getCategories().add(category); // Add category even if there is no data for this date
//            currentDate = currentDate.plusDays(1);
//        }
//
//        // Add data points to the series
//        // taskInertiaMap.forEach((date, inertia) -> {
//        //     String dateString = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//        //     XYChart.Data<String, Number> data = new XYChart.Data<>(dateString, inertia);
//        //     series.getData().add(data);
//        //     Tooltip tooltip = new Tooltip(String.format("Inertia: %.2f%%", inertia));
//        //     // Install tooltip when the node for this data point is created
//        //     data.nodeProperty().addListener((obs, oldNode, newNode) -> {
//        //         if (newNode != null) {
//        //             Tooltip.install(newNode, tooltip);
//        //         }
//        //     });
//        // });
//
//        // Add the series to the chart
//        barChart.getData().add(series);
//    }

}