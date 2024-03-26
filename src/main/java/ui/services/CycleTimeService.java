package ui.services;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import taiga.api.TasksAPI;
import taiga.api.UserStoryAPI;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.userstories.UserStoryInterface;
import taiga.util.TaskUtils;
import taiga.util.UserStoryUtils;
import taiga.util.timeAnalysis.CycleTimeEntry;
import ui.tooltips.CycleTimeTaskTooltip;
import ui.tooltips.CycleTimeUserStoryTooltip;
import ui.util.DateUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CycleTimeService extends Service<Object> {
    private Sprint sprint;
    private Integer projectId;
    private Date startDate;
    private Date endDate;

    private final TasksAPI tasksAPI;
    private final UserStoryAPI userStoryAPI;
    private final ObservableList<XYChart.Data<String, Number>> tasks;
    private final ObservableList<XYChart.Data<String, Number>> stories;

    public CycleTimeService() {
        this.tasksAPI = new TasksAPI();
        this.userStoryAPI = new UserStoryAPI();
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
        this.startDate = sprint.getEstimatedStart();
        this.endDate = sprint.getEstimatedFinish();
        this.projectId = sprint.getProject();
        this.restart();
    }

    public void recalculate(Integer projectId, Date startDate, Date endDate) {
        this.sprint = null;
        this.projectId = projectId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.restart();
    }

    private List<CycleTimeEntry<taiga.model.query.tasks.Task>> getAllTaskCycleTime(List<taiga.model.query.tasks.Task> rawTasks) {
        LocalDate start = DateUtil.toLocal(startDate);
        LocalDate end = DateUtil.toLocal(endDate);
        List<LocalDate> dates = start.datesUntil(end.plusDays(1)).toList();
        List<CycleTimeEntry<taiga.model.query.tasks.Task>> cycleTimes = rawTasks
                .parallelStream()
                .map(TaskUtils::getCycleTimeForTask)
                .filter(task -> task.getStartDate() != null
                        && task.getStartDate().before(DateUtil.toDate(end.plusDays(1)))
                        && task.getStartDate().after(startDate))
                .toList();
        List<CycleTimeEntry<taiga.model.query.tasks.Task>> finalCycleTimes = new ArrayList<>(cycleTimes);

        for (LocalDate date : dates) {
            CycleTimeEntry<taiga.model.query.tasks.Task> entry = new CycleTimeEntry<>(null, DateUtil.toDate(date), null,
                    false);
            entry.setTooltipCallback(new CycleTimeTaskTooltip());
            finalCycleTimes.add(entry);
        }

        return finalCycleTimes;
    }

    private List<CycleTimeEntry<UserStoryInterface>> getAllUserStoryCycleTime(List<UserStoryInterface> rawUserStories) {
        LocalDate start = DateUtil.toLocal(startDate);
        LocalDate end = DateUtil.toLocal(endDate);
        List<LocalDate> dates = start.datesUntil(end.plusDays(1)).toList();
        List<CycleTimeEntry<UserStoryInterface>> cycleTimes = rawUserStories
                .parallelStream()
                .map(UserStoryUtils::getCycleTimeForUserStory)
                .filter(story -> story.getStartDate() != null
                        && story.getStartDate().before(DateUtil.toDate(end.plusDays(1)))
                        && story.getStartDate().after(startDate))
                .toList();
        List<CycleTimeEntry<UserStoryInterface>> finalCycleTimes = new ArrayList<>(cycleTimes);

        for (LocalDate date : dates) {
            CycleTimeEntry<UserStoryInterface> entry = new CycleTimeEntry<>(null, DateUtil.toDate(date), null, false);
            entry.setTooltipCallback(new CycleTimeUserStoryTooltip());
            finalCycleTimes.add(entry);
        }

        return finalCycleTimes;
    }

    private <T> void updateCycleTimes(ObservableList<XYChart.Data<String, Number>> data,
            List<CycleTimeEntry<T>> entries) {
        SimpleDateFormat format = new SimpleDateFormat("MMM dd");
        List<CycleTimeEntry<T>> sortedEntries = entries.stream()
                .sorted(Comparator.comparing(CycleTimeEntry::getStartDate))
                .toList();
        data.setAll(
                sortedEntries.stream()
                        .map(t -> {
                            if (t.isValid()) {
                                return new XYChart.Data<>(format.format(t.getStartDate()),
                                        (Number) TimeUnit.MILLISECONDS.toDays(t.getTimeTaken()));
                            }
                            return new XYChart.Data<>(format.format(t.getStartDate()), (Number) 0);
                        })
                        .toList());

        for (int i = 0; i < data.size(); i++) {
            XYChart.Data<String, Number> d = data.get(i);
            CycleTimeEntry<T> story = sortedEntries.get(i);
            if (!story.isValid()) {
                d.getNode().setVisible(false);
            } else {
                Tooltip.install(d.getNode(), new Tooltip(story.applyTooltipCallback(story)));
            }
        }
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
                List<taiga.model.query.tasks.Task> rawTasks = new ArrayList<>();
                List<UserStoryInterface> rawUserStories = new ArrayList<>();

                if (startDate == null || endDate == null) {
                    return null;
                }

                if (sprint != null) {
                    tasksAPI.listTasksByMilestone(sprint.getId(), result -> {
                        if (result.getStatus() != 200) {
                            return;
                        }
                        rawTasks.addAll(List.of(result.getContent()));
                    }).join();

                    rawUserStories.addAll(sprint.getUserStories());
                } else {
                    CompletableFuture<Void> futureTasks = tasksAPI.listTasksByProject(projectId, result -> {
                        if (result.getStatus() != 200) {
                            return;
                        }
                        rawTasks.addAll(List.of(result.getContent()));
                    });

                    CompletableFuture<Void> futureUserStories = userStoryAPI.listProjectUserStories(projectId,
                            result -> {
                                if (result.getStatus() != 200) {
                                    return;
                                }
                                rawUserStories.addAll(List.of(result.getContent()));
                            });
                    futureTasks.join();
                    futureUserStories.join();
                }

                List<CycleTimeEntry<taiga.model.query.tasks.Task>> taskCycleTime = getAllTaskCycleTime(rawTasks);
                List<CycleTimeEntry<UserStoryInterface>> userStoryCycleTime = getAllUserStoryCycleTime(rawUserStories);

                Platform.runLater(() -> {
                    updateCycleTimes(tasks, taskCycleTime);
                    updateCycleTimes(stories, userStoryCycleTime);
                });

                return null;
            }
        };
    }

    // For Testing
    public Integer getProjectId() {
        return projectId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
