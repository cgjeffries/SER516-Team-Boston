package ui.services;

import bostonclient.BostonClient;
import bostonmodel.taskchurn.TaskChurnItem;
import bostonmodel.taskchurn.TaskChurnMetrics;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import taiga.models.sprint.Sprint;

public class TaskChurnService extends Service<Object> {
    private Sprint sprint;

    private final ObservableList<TaskChurnItem> taskChurnItems;

    public TaskChurnService() {
        this.taskChurnItems = FXCollections.observableArrayList();
    }

    public ObservableList<TaskChurnItem> getTaskChurnItems(){
        return taskChurnItems;
    }

    public void recalculate(Sprint sprint) {
        if (sprint != null && sprint.getId() > 0) {
            this.sprint = sprint;
            restart();
        }
    }
    @Override
    protected Task<Object> createTask() {
        return new Task<>() {
            @Override
            protected Object call() {
                if (sprint == null) {
                    return null;
                }
                AtomicReference<TaskChurnMetrics> metricsReference = new AtomicReference<>();
                BostonClient.getTaskChurnAPI().getTaskChurn(sprint.getId(), result -> {
                    if (result.getStatus() != 200) {
                        System.out.println("Error: TaskChurn service returned bad response code: " +
                            result.getStatus());
                        metricsReference.set(new TaskChurnMetrics(
                            new ArrayList<>())); //set to empty (instead of null) so things don't explode
                    }
                    metricsReference.set(result.getContent());
                });

                TaskChurnMetrics taskChurnMetrics = metricsReference.get();

                Platform.runLater(() -> {
                    taskChurnItems.setAll(taskChurnMetrics.getTaskChurnItems());
                });

                return null;
            }
        };
    }
}
