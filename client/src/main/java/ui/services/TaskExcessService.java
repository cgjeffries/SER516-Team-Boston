package ui.services;

import bostonclient.BostonClient;
import bostonclient.apis.TaskExcessAPI;
import bostonmodel.taskexcess.TaskExcessMetrics;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.PieChart;
import taiga.models.sprint.Sprint;

public class TaskExcessService extends Service<Object> {
    private Sprint sprint;
    private final IntegerProperty totalTaskCount;
    private final IntegerProperty newTaskCount;
    private final DoubleProperty taskExcessRatio;
    private final TaskExcessAPI taskExcessAPI;
    private final BooleanProperty validSprintSelected = new SimpleBooleanProperty(false);
    private final ObservableList<PieChart.Data> taskExcessData;

    public TaskExcessService() {
        this.totalTaskCount = new SimpleIntegerProperty();
        this.newTaskCount = new SimpleIntegerProperty();
        this.taskExcessRatio = new SimpleDoubleProperty();
        this.taskExcessAPI = BostonClient.getTaskExcessAPI();
        this.taskExcessData = FXCollections.observableArrayList();
    }

    public BooleanProperty validSprintSelectedProperty() {
        return validSprintSelected;
    }

    public void recalculate(Sprint sprint) {
        if (sprint != null && sprint.getId() > 0) {
            this.sprint = sprint;
            restart();
        } else {
            Platform.runLater(() -> {
                totalTaskCount.set(0);
                newTaskCount.set(0);
                taskExcessRatio.set(0.0);
                validSprintSelected.set(false);
            });
        }
    }

    public ObservableList<PieChart.Data> getTaskPieChartData() {
        return taskExcessData;
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
                taskExcessAPI.getTaskExcess(sprint.getId(), response -> {
                    if (response.getStatus() == 200 && response.getContent() != null) {
                        TaskExcessMetrics metrics = response.getContent();
                        Platform.runLater(() -> {
                            totalTaskCount.set(metrics.getTotalTasks());
                            newTaskCount.set(metrics.getNewTasks());
                            taskExcessRatio.set(metrics.getTaskExcessRatio());
                            validSprintSelected.set(metrics.getTotalTasks() > 0);

                            taskExcessData.setAll(
                                    new PieChart.Data("New Tasks", metrics.getNewTasks()),
                                    new PieChart.Data("Existing Tasks", metrics.getTotalTasks() - metrics.getNewTasks())
                            );
                        });
                    } else {
                        Platform.runLater(() -> validSprintSelected.set(false));
                        System.err.println("Error: Task Excess service returned bad response code: " + response.getStatus());
                    }
                }).join();
                return null;
            }
        };
    }
}
