package ui.metrics.burndown;

import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import taiga.model.query.sprint.Sprint;

public class Burndown extends StackPane {
    private BurndownService service;

    private final XYChart.Series<String, Number> taskSeries;
    private final XYChart.Series<String, Number> userStorySeries;
    private final XYChart.Series<String, Number> bvSeries;
    private final LineChart<String, Number> chart;

    public Burndown() {
        this.service = new BurndownService();
        this.taskSeries = new XYChart.Series<>(this.service.getTaskData());
        this.userStorySeries = new XYChart.Series<>(this.service.getUserStoryData());
        this.bvSeries = new XYChart.Series<>(this.service.getBusinessValueData());

        CategoryAxis date = new CategoryAxis();
        date.setLabel("Date");
        NumberAxis value = new NumberAxis();
        value.setLabel("Value");
        this.chart = new LineChart<>(date, value);

        this.init();
    }

    private void init() {
        this.taskSeries.setName("Task Burndown");
        this.userStorySeries.setName("User Story Burndown");
        this.bvSeries.setName("Business Value Burndown");

        setAlignment(Pos.CENTER);

        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(this.service.runningProperty());

        chart.getData().addAll(this.taskSeries, this.userStorySeries, this.bvSeries);
        chart.setAnimated(false); 
        chart.visibleProperty().bind(this.service.runningProperty().not());

        getChildren().add(chart);
        getChildren().add(progress);
        this.service.start();
    }

    public void switchSprint(Sprint sprint) {
        this.service.recalculate(sprint);
    }

    public void clear() {
        chart.getData().removeAll();
    }

    public void cancel() {
        this.service.cancel();
    }
}
