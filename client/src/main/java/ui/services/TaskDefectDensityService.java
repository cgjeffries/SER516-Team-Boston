package ui.services;

import bostonclient.BostonClient;
import bostonclient.apis.TaskDefectDensityAPI;
import bostonmodel.taskdefectdensity.TaskDefectDensityMetrics;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import taiga.models.sprint.Sprint;


public class TaskDefectDensityService extends Service<Object> {
    private Sprint sprint;
    private final IntegerProperty totalTasks;
    private final IntegerProperty unfinishedTasks;
    private final ObservableList<PieChart.Data> taskDefectData;
    private final TaskDefectDensityAPI taskddAPI;
    private final BooleanProperty validSprintSelected = new SimpleBooleanProperty(false);


    public TaskDefectDensityService() {
        this.totalTasks = new SimpleIntegerProperty();
        this.unfinishedTasks = new SimpleIntegerProperty();
        this.taskDefectData = FXCollections.observableArrayList();
        this.taskddAPI = BostonClient.getTaskDefectDensityAPI();
    }

    public void recalculate(Sprint sprint) {
        if (sprint != null && sprint.getId() > 0) {
            this.sprint = sprint;
            restart();
        } else {
            Platform.runLater(() -> {
                totalTasks.set(0);
                unfinishedTasks.set(0);
                validSprintSelected.set(false);
            });
        }
    }

    public BooleanProperty validSprintSelectedProperty() {
        return validSprintSelected;
    }
    public ObservableList<PieChart.Data> getTaskPieChartData() {
        return taskDefectData;
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
                            validSprintSelected.set(metrics.getTotalTasks() > 0);

                            taskDefectData.setAll(
                                    new PieChart.Data("Unfinished Tasks", metrics.getNewTasks()),
                                    new PieChart.Data("Finished Tasks", metrics.getTotalTasks()-metrics.getNewTasks())
                            );
                        });
                    } else {
                        Platform.runLater(() -> validSprintSelected.set(false));
                        System.err.println("Error: Task Defect Density service returned bad response code: " + response.getStatus());
                    }
                }).join();
                return null;
            }
        };
    }
}
