package ui.services;

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
import ui.util.DateUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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

    public ObservableList<XYChart.Data<String, Number>> getTasks() {
        return tasks;
    }

    public ObservableList<XYChart.Data<String, Number>> getStories() {
        return stories;
    }

    public void recalculate(Sprint sprint) {
        this.sprint = sprint;
        this.restart();
    }

    private List<CycleTimeEntry> getAllTaskCycleTime() {
        List<taiga.model.query.tasks.Task> tasks = new ArrayList<>();
        tasksAPI.listTasksByMilestone(sprint.getId(), result -> {
            if (result.getStatus() != 200) {
                return;
            }
            tasks.addAll(List.of(result.getContent()));
        }).join();

        List<CycleTimeEntry> cycleTimes = tasks.parallelStream().map(TaskUtils::getCycleTimeForTask).toList();
        LocalDate start = DateUtil.toLocal(sprint.getEstimatedStart());
        LocalDate end = DateUtil.toLocal(sprint.getEstimatedFinish());
        List<LocalDate> dates = start.datesUntil(end.plusDays(1)).toList();
        List<CycleTimeEntry> finalCycleTimes = new ArrayList<>(cycleTimes);

        for (LocalDate date : dates) {
            finalCycleTimes.add(new CycleTimeEntry(DateUtil.toDate(date), null, false));
        }

        return finalCycleTimes;
    }

    private List<CycleTimeEntry> getAllUserStoryCycleTime() {
        List<CycleTimeEntry> cycleTimes = sprint.getUserStories().parallelStream().map(UserStoryUtils::getCycleTimeForUserStory).toList();
        LocalDate start = DateUtil.toLocal(sprint.getEstimatedStart());
        LocalDate end = DateUtil.toLocal(sprint.getEstimatedFinish());
        List<LocalDate> dates = start.datesUntil(end.plusDays(1)).toList();
        List<CycleTimeEntry> finalCycleTimes = new ArrayList<>(cycleTimes);

        for (LocalDate date : dates) {
            finalCycleTimes.add(new CycleTimeEntry(DateUtil.toDate(date), null, false));
        }

        return finalCycleTimes;
    }

    private void updateCycleTimes(ObservableList<XYChart.Data<String, Number>> data, List<CycleTimeEntry> entries) {
        SimpleDateFormat format = new SimpleDateFormat("MMM dd");
        data.setAll(
                entries.stream()
                        .filter(t -> t.getStartDate() != null)
                        .sorted(Comparator.comparing(CycleTimeEntry::getStartDate))
                        .map(t -> {
                            if (t.isValid()) {
                                return new XYChart.Data<>(format.format(t.getStartDate()), (Number) TimeUnit.MILLISECONDS.toDays(t.getTimeTaken()));
                            }
                            return new XYChart.Data<>(format.format(t.getStartDate()), (Number) 0);
                        })
                        .toList()
        );
        data.forEach(d -> {
            if (d.getYValue().equals(0)) {
                d.getNode().setVisible(false);
            }
        });
    }

    @Override
    protected void failed() {
        super.failed();
        Throwable exception = getException();
        if (exception != null) {
            exception.printStackTrace();
        }
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
