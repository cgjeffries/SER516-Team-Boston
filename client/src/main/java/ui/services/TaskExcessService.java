package ui.services;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import taiga.models.sprint.Sprint;

import java.util.ArrayList;
import java.util.List;

public class TaskExcessService extends Service<Object> {
    private List<Sprint> sprints;

    private final ObservableMap<Sprint, TaskExcessData> taskExcessData;

    public TaskExcessService() {
        taskExcessData = FXCollections.observableHashMap();
        sprints = new ArrayList<>();
    }

    public void recalculate(List<Sprint> sprints) {
        this.sprints = sprints;
        this.restart();
    }

    @Override
    protected Task<Object> createTask() {
        return new Task<>() {
            @Override
            protected Object call() {
                for (Sprint sprint : sprints) {
                    int newTasks = fetchNewTasks(sprint);
                    int totalTasks = fetchTotalTasks(sprint);
                    TaskExcess taskExcess = new TaskExcess(newTasks, totalTasks);
                    Platform.runLater(() -> {
                        TaskExcessData data = new TaskExcessData(taskExcess.getTaskExcess());
                        taskExcessData.put(sprint, data);
                    });
                }
                return null;
            }
        };
    }

    public ObservableMap<Sprint, TaskExcessData> getTaskData() {
        return this.taskExcessData;
    }

    private int fetchNewTasks(Sprint sprint) {
        // Implement logic to fetch new tasks
        return 5; // Example data
    }

    private int fetchTotalTasks(Sprint sprint) {
        // Implement logic to fetch total tasks
        return 20; // Example data
    }

    public static class TaskExcessData {
        private final double taskExcessRatio;

        public TaskExcessData(double taskExcessRatio) {
            this.taskExcessRatio = taskExcessRatio;
        }

        public double getTaskExcessRatio() {
            return taskExcessRatio;
        }
    }
}
