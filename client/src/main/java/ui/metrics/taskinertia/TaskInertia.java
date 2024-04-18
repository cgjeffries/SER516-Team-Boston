package ui.metrics.taskinertia;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskInertia extends Application {

    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private int projectId = 0; // Update with actual project ID
    private String authToken = null; // Update with actual auth token
    private String endpoint = null; // Update with actual endpoint URL



    public TaskInertia(){
        endDate.set(LocalDate.now());
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Task Inertia");

        // Initialize DatePickers
        DatePicker startDatePicker = new DatePicker(LocalDate.now().minusDays(30));
        DatePicker endDatePicker = new DatePicker(LocalDate.now());
        startDate.bindBidirectional(startDatePicker.valueProperty());
        endDate.bindBidirectional(endDatePicker.valueProperty());
    
        // Initialize Button
        Button btnSubmit = new Button("Show Task Inertia");
        
        // Bar Chart setup
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Task Inertia (%)");
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Task Inertia Chart");
    
        // Layout
        VBox layout = new VBox(10, startDatePicker, endDatePicker, btnSubmit, barChart);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
    
        // Scene setup
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    
        // Update chart when button is clicked
        btnSubmit.setOnAction(e -> updateChartData(barChart));
    }

    private void updateChartData(BarChart<String, Number> barChart) {
        LocalDate start = startDate.get();
        LocalDate end = endDate.get();
        // TreeMap<LocalDate, Float> taskInertiaMap = TaskInertia.getTaskInertia(projectId, authToken, endpoint, start, end);
    
        // Clear previous data from the chart
        barChart.getData().clear();
    
        // Assuming CategoryAxis is your X axis
        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        xAxis.getCategories().clear(); // Clear previous categories
    
        // Create a new series for the data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Task Inertia");
    
        // Generate a category for every day in the range to ensure even spacing
        LocalDate currentDate = start;
        while (!currentDate.isAfter(end)) {
            String category = currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            xAxis.getCategories().add(category); // Add category even if there is no data for this date
            currentDate = currentDate.plusDays(1);
        }
    
        // Add data points to the series
        // taskInertiaMap.forEach((date, inertia) -> {
        //     String dateString = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        //     XYChart.Data<String, Number> data = new XYChart.Data<>(dateString, inertia);
        //     series.getData().add(data);
        //     Tooltip tooltip = new Tooltip(String.format("Inertia: %.2f%%", inertia));
        //     // Install tooltip when the node for this data point is created
        //     data.nodeProperty().addListener((obs, oldNode, newNode) -> {
        //         if (newNode != null) {
        //             Tooltip.install(newNode, tooltip);
        //         }
        //     });
        // });
    
        // Add the series to the chart
        barChart.getData().add(series);
    }
    
}