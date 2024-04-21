package ui.services;

import bostonclient.BostonClient;
import bostonclient.apis.TaskDefectDensityAPI;
import bostonmodel.taskdefectdensity.TaskDefectDensityMetrics;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import taiga.models.sprint.Sprint;


public class TaskDefectDensityService extends Service<Object> {
    private int projectId;
    private Sprint sprint;
    private final IntegerProperty totalTasks;
    private final IntegerProperty unfinishedTasks;
    private final ObservableList<PieChart.Data> taskDefectData;
    private final TaskDefectDensityAPI taskddAPI;

    public TaskDefectDensityService() {
        this.totalTasks = new SimpleIntegerProperty();
        this.unfinishedTasks = new SimpleIntegerProperty();
        this.taskDefectData = FXCollections.observableArrayList();
        this.taskddAPI = BostonClient.getTaskDefectDensityAPI();
    }

    public IntegerProperty totalTasksCount() {
        return totalTasks;
    }

    public IntegerProperty unfinishedTasksCount() {
        return unfinishedTasks;
    }

    @Override
    protected Task<Object> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                taskddAPI.getTaskDD(sprint.getId(), response -> {
                    if (response.getStatus() == 200 && response.getContent() != null) {
                        TaskDefectDensityMetrics metrics = response.getContent();
                        Platform.runLater(() -> {
                            totalTasks.set(metrics.getTotalTasks());
                            unfinishedTasks.set(metrics.getNewTasks());

                            taskDefectData.setAll(
                                    new PieChart.Data("Unfinished Tasks", metrics.getNewTasks()),
                                    new PieChart.Data("Finished Tasks", metrics.getTotalTasks())
                            );
                        });
                    } else {
                        System.err.println("Error: Task Defect Density service returned bad response code: " + response.getStatus());
                    }
                }).join();
                return null;
            }
        };
    }

    public void recalculate(int projectId) {
        this.projectId = projectId;
        this.restart();
    }

}
