package ui.services;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import bostonclient.BostonClient;
import bostonclient.apis.TaskExcessAPI;
import bostonmodel.pbhealth.PBHealthMetrics;
import bostonmodel.taskexcess.TaskExcessMetrics;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.PieChart;

public class TaskExcessService extends Service<Object> {
    private int sprintId;
    private final IntegerProperty totalTaskCount;
    private final IntegerProperty newTaskCount;
    private final DoubleProperty taskExcessRatio;
    private TaskExcessAPI taskExcessAPI;

    public TaskExcessService() {
        this.totalTaskCount = new SimpleIntegerProperty();
        this.newTaskCount = new SimpleIntegerProperty();
        this.taskExcessRatio = new SimpleDoubleProperty();
        this.taskExcessAPI = BostonClient.getTaskExcessAPI();
    }

    public ObservableList<PieChart.Data> getTaskPieChartData() {
        return FXCollections.observableArrayList(
                new PieChart.Data("New Tasks", newTaskCount.get()),
                new PieChart.Data("Existing Tasks", totalTaskCount.get() - newTaskCount.get())
        );
    }
    public DoubleProperty taskExcessRatioProperty() {
        return taskExcessRatio;
    }

    public IntegerProperty newTaskCountProperty() {
        return newTaskCount;
    }

    public IntegerProperty totalTaskCountProperty() {
        return totalTaskCount;
    }

    @Override
    protected Task<Object> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                AtomicReference<TaskExcessMetrics> metricsReference = new AtomicReference<>();
                taskExcessAPI.getTaskExcess(sprintId, response -> {
                    if (response.getStatus() == 200) {
                        metricsReference.set(response.getContent());

                    } else {
                        System.err.println("Error: Task Excess service returned bad response code: " + response.getStatus());
                    }
                }).join();

                TaskExcessMetrics metrics = metricsReference.get();
                Platform.runLater(() -> {
                    totalTaskCount.set(metrics.getTotalTasks());
                    newTaskCount.set(metrics.getNewTasks());
                    taskExcessRatio.set(metrics.gettaskExcessRatio());
                });

                return null;
            }
        };
    }


    public void recalculate(int sprintId) {
        this.sprintId = sprintId;
        restart();
    }

}
