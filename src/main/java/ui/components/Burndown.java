package ui.components;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import taiga.model.query.sprint.Sprint;
import taiga.util.burndown.BurnDownUtil;

public class Burndown extends VBox {
    private BurnDownUtil burnDownUtil;

    private final XYChart.Series<String, Number> taskSeries;
    private final XYChart.Series<String, Number> userStorySeries;
    private final XYChart.Series<String, Number> bvSeries;

    public Burndown() {
        this.burnDownUtil = new BurnDownUtil();
        this.taskSeries = new XYChart.Series<>(this.burnDownUtil.getTaskData());
        this.userStorySeries = new XYChart.Series<>(this.burnDownUtil.getUserStoryData());
        this.bvSeries = new XYChart.Series<>(this.burnDownUtil.getBusinessValueData());
        this.init();
    }

    private void init() {
        this.taskSeries.setName("Task Burndown");
        this.userStorySeries.setName("User Story Burndown");
        this.bvSeries.setName("Business Value Burndown");

        CategoryAxis date = new CategoryAxis();
        date.setLabel("Date");

        NumberAxis value = new NumberAxis();
        value.setLabel("Value");

        LineChart<String, Number> chart = new LineChart<>(date, value);
        chart.getData().addAll(this.taskSeries, this.userStorySeries, this.bvSeries);
        getChildren().add(chart);
    }

    public void switchSprint(Sprint sprint) {
        this.burnDownUtil.recalculate(sprint);
    }
}
