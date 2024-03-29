package ui.services;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import taiga.TaigaClient;
import taiga.models.sprint.Sprint;
import taiga.models.userstories.UserStoryInterface;
import taigaold.util.TaskUtils;
import taigaold.util.UserStoryUtils;
import taigaold.util.timeAnalysis.CycleTimeEntry;
import ui.tooltips.CycleTimeTaskTooltip;
import ui.tooltips.CycleTimeUserStoryTooltip;
import ui.util.DateUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CycleTimeService extends Service<Object> {
    private Sprint sprint;
    private Integer projectId;
    private Date startDate;
    private Date endDate;

    private final ObservableList<XYChart.Data<String, Number>> tasks;
    private final ObservableList<XYChart.Data<String, Number>> stories;

    public CycleTimeService() {
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

    private List<CycleTimeEntry<taiga.models.tasks.Task>> getAllTaskCycleTime() {
        List<taiga.models.tasks.Task> rawTasks = fetchTasks();
        LocalDate start = DateUtil.toLocal(startDate);
        LocalDate end = DateUtil.toLocal(endDate);
        List<LocalDate> dates = start.datesUntil(end.plusDays(1)).toList();
        List<CycleTimeEntry<taiga.models.tasks.Task>> cycleTimes = rawTasks
                .parallelStream()
                .map(TaskUtils::getCycleTimeForTask)
                .filter(task -> task.getStartDate() != null
                        && task.getStartDate().before(DateUtil.toDate(end.plusDays(1)))
                        && task.getStartDate().after(startDate))
                .toList();
        List<CycleTimeEntry<taiga.models.tasks.Task>> finalCycleTimes = new ArrayList<>(cycleTimes);

        for (LocalDate date : dates) {
            CycleTimeEntry<taiga.models.tasks.Task> entry = new CycleTimeEntry<>(null, DateUtil.toDate(date), null,
                    false);
            entry.setTooltipCallback(new CycleTimeTaskTooltip());
            finalCycleTimes.add(entry);
        }

        return finalCycleTimes;
    }

    private List<CycleTimeEntry<UserStoryInterface>> getAllUserStoryCycleTime() {
        List<UserStoryInterface> rawUserStories = fetchStories();
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

    public List<taiga.models.tasks.Task> fetchTasks() {
        List<taiga.models.tasks.Task> tasks = new ArrayList<>();
        if (sprint != null) {
            TaigaClient.getTasksAPI().listTasksByMilestone(sprint.getId(), result -> {
                if (result.getStatus() != 200) {
                    return;
                }
                tasks.addAll(List.of(result.getContent()));
            }).join();
            return tasks;
        }
        TaigaClient.getTasksAPI().listTasksByProject(projectId, result -> {
            if (result.getStatus() != 200) {
                return;
            }
            tasks.addAll(List.of(result.getContent()));
        }).join();
        return tasks;
    }

    public List<UserStoryInterface> fetchStories() {
        List<UserStoryInterface> stories = new ArrayList<>();
        if (sprint != null) {
            stories.addAll(sprint.getUserStories());
            return stories;
        }
        TaigaClient.getUserStoryAPI().listProjectUserStories(projectId, result -> {
            if (result.getStatus() != 200) {
                return;
            }
            stories.addAll(List.of(result.getContent()));
        }).join();
        return stories;
    }

    @Override
    protected Task<Object> createTask() {
        return new Task<>() {
            @Override
            protected Object call() throws Exception {
                if (startDate == null || endDate == null) {
                    return null;
                }

                List<CycleTimeEntry<taiga.models.tasks.Task>> taskCycleTime = getAllTaskCycleTime();
                List<CycleTimeEntry<UserStoryInterface>> userStoryCycleTime = getAllUserStoryCycleTime();

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
