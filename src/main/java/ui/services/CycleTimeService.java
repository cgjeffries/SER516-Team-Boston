package ui.services;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import taiga.api.TasksAPI;
import taiga.api.UserStoryAPI;
import taiga.model.query.project.Project;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.UserStory;
import taiga.model.query.sprint.UserStoryDetail;
import taiga.model.query.userstories.UserStoryInterface;
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
    private Integer projectId;
    private Date startDate;
    private Date endDate;

    private List<taiga.model.query.tasks.Task> rawTasks;
    private List<UserStoryInterface> rawUserStories;

    private final TasksAPI tasksAPI;
    private final UserStoryAPI userStoryAPI;
    private final ObservableList<XYChart.Data<String, Number>> tasks;
    private final ObservableList<XYChart.Data<String, Number>> stories;

    public CycleTimeService() {
        this.tasksAPI = new TasksAPI();
        this.userStoryAPI = new UserStoryAPI();
        this.tasks = FXCollections.observableArrayList();
        this.stories = FXCollections.observableArrayList();
        this.rawTasks = new ArrayList<>();
        this.rawUserStories = new ArrayList<>();
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

    private List<CycleTimeEntry> getAllTaskCycleTime(){
        List<CycleTimeEntry> cycleTimes = rawTasks.parallelStream().map(TaskUtils::getCycleTimeForTask).toList();
        LocalDate start = DateUtil.toLocal(startDate);
        LocalDate end = DateUtil.toLocal(endDate);
        List<LocalDate> dates = start.datesUntil(end.plusDays(1)).toList();
        List<CycleTimeEntry> finalCycleTimes = new ArrayList<>(cycleTimes);

        for (LocalDate date : dates) {
            finalCycleTimes.add(new CycleTimeEntry(DateUtil.toDate(date), null, false));
        }

        return finalCycleTimes;
    }

    private List<CycleTimeEntry> getAllUserStoryCycleTime() {
        List<CycleTimeEntry> cycleTimes = rawUserStories.parallelStream().map(UserStoryUtils::getCycleTimeForUserStory).toList();
        LocalDate start = DateUtil.toLocal(startDate);
        LocalDate end = DateUtil.toLocal(endDate);
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
                        .filter(t -> t.getStartDate().after(startDate) && t.getStartDate().before(endDate))
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
                if(startDate == null || endDate == null){
                    return null;
                }


                if(sprint != null) {
                    tasksAPI.listTasksByMilestone(sprint.getId(), result -> {
                        if (result.getStatus() != 200) {
                            return;
                        }
                        rawTasks.addAll(List.of(result.getContent()));
                    }).join();

                    rawUserStories.addAll(sprint.getUserStories());
                }
                else{
                    CompletableFuture<Void> futureTasks = tasksAPI.listTasksByProject(projectId, result -> {
                        if (result.getStatus() != 200) {
                            return;
                        }
                        rawTasks.addAll(List.of(result.getContent()));
                    });

                    CompletableFuture<Void> futureUserStories = userStoryAPI.listProjectUserStories(projectId, result -> {
                        if (result.getStatus() != 200) {
                            return;
                        }
                        rawUserStories.addAll(List.of(result.getContent()));
                    });
                    futureTasks.join();
                    futureUserStories.join();
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
