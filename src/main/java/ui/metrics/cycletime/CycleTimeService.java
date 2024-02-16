package ui.metrics.cycletime;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import taiga.api.TasksAPI;
import taiga.model.query.sprint.Sprint;
import taiga.util.TaskUtils;
import taiga.util.UserStoryUtils;
import taiga.util.timeAnalysis.CycleTimeEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CycleTimeService extends Service<Object> {
    private Sprint sprint;
    private final TasksAPI tasksAPI;
    private final ObservableList<XYChart.Data<String, Number>> tasks;
    private final ObservableList<XYChart.Data<String, Number>> stories;

    public CycleTimeService() {
        this.tasksAPI = new TasksAPI();
        this.tasks = FXCollections.observableArrayList();
        this.stories = FXCollections.observableArrayList();
    }

    public void recalculate(Sprint sprint) {
        this.sprint = sprint;
        this.restart();
    }

    @Override
    protected void failed() {
        super.failed();
        Throwable exception = getException();
        if (exception != null) {
            exception.printStackTrace();
        }
    }

    private List<CycleTimeEntry> getAllTaskCycleTime() {
        List<taiga.model.query.tasks.Task> tasks = new ArrayList<>();
        tasksAPI.listTasksByMilestone(sprint.getId(), result -> {
            if (result.getStatus() != 200) {
                return;
            }
            tasks.addAll(List.of(result.getContent()));
        }).join();
        return tasks.parallelStream().map(TaskUtils::getCycleTimeForTask).toList();
    }

    private List<CycleTimeEntry> getAllUserStoryCycleTime() {
        return sprint.getUserStories().parallelStream().map(UserStoryUtils::getCycleTimeForUserStory).toList();
    }

    private void updateCycleTimes(ObservableList<XYChart.Data<String, Number>> data, List<CycleTimeEntry> entries) {
        SimpleDateFormat format = new SimpleDateFormat("MMM dd");
        data.setAll(entries.stream().filter(t -> t.getStartDate() != null && t.getEndDate() != null).map(t -> new XYChart.Data<>(format.format(t.getStartDate()), (Number) TimeUnit.MILLISECONDS.toDays(t.getTimeTaken()))).toList());
    }

    @Override
    protected Task<Object> createTask() {
        return new Task<>() {
            @Override
            protected Object call() throws Exception {
                if (sprint == null) {
                    return null;
                }

                List<CycleTimeEntry> taskCycleTime = getAllTaskCycleTime();
                List<CycleTimeEntry> userStoryCycleTime = getAllUserStoryCycleTime();

                Platform.runLater(() -> {
                    updateCycleTimes(tasks, taskCycleTime);
                    updateCycleTimes(stories, userStoryCycleTime);
                });

                return null;
            }
        };
    }
}
