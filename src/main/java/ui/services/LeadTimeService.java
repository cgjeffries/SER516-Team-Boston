package ui.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import taiga.model.query.common.Project;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.userstories.UserStoryInterface;
import taiga.util.timeAnalysis.LeadTimeHelper;
import taiga.util.timeAnalysis.LeadTimeStats;
import ui.util.DateUtil;

public class LeadTimeService extends Service<Object> {
//    private Sprint sprint;

    private Integer projectId;
    private Date startDate;
    private Date endDate;

    private final ObservableList<XYChart.Data<String, Number>> notCreatedStories;
    private final ObservableList<XYChart.Data<String, Number>> inBacklogStories;
    private final ObservableList<XYChart.Data<String, Number>> inSprintStories;
    private final ObservableList<XYChart.Data<String, Number>> inProgressStories;
    private final ObservableList<XYChart.Data<String, Number>> readyForTestStories;
    private final ObservableList<XYChart.Data<String, Number>> doneStories;

    public LeadTimeService() {
        this.notCreatedStories = FXCollections.observableArrayList();
        this.inBacklogStories = FXCollections.observableArrayList();
        this.inSprintStories = FXCollections.observableArrayList();
        this.inProgressStories = FXCollections.observableArrayList();
        this.readyForTestStories = FXCollections.observableArrayList();
        this.doneStories = FXCollections.observableArrayList();
    }

    public ObservableList<XYChart.Data<String, Number>> getNotCreatedStories() {
        return notCreatedStories;
    }

    public ObservableList<XYChart.Data<String, Number>> getInBacklogStories() {
        return inBacklogStories;
    }

    public ObservableList<XYChart.Data<String, Number>> getInProgressStories() {
        return inSprintStories;
    }

    public ObservableList<XYChart.Data<String, Number>> getInSprintStories() {
        return inProgressStories;
    }

    public ObservableList<XYChart.Data<String, Number>> getReadyForTestStories() {
        return readyForTestStories;
    }

    public ObservableList<XYChart.Data<String, Number>> getDoneStories() {
        return doneStories;
    }

    public void recalculate(Sprint sprint){
        recalculate(sprint.getProject(), sprint.getEstimatedStart(), sprint.getEstimatedFinish());
    }

    public void recalculate(Integer projectId, Date startDate, Date endDate){
        this.projectId = projectId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.restart();
    }

    private List<LeadTimeStats> getAllLeadTimeStats(){
        LeadTimeHelper leadTimeHelper = new LeadTimeHelper(projectId);

        LocalDate start = DateUtil.toLocal(startDate);
        LocalDate end = DateUtil.toLocal(endDate);
        List<LocalDate> dates = start.datesUntil(end.plusDays(1)).toList();

        List<LeadTimeStats> leadTimeStats = new ArrayList<>();

        for (LocalDate date : dates) {
            leadTimeStats.add(leadTimeHelper.getLeadTimeStatsForDate(DateUtil.toDate(date)));
        }
        return leadTimeStats;
    }

    private void updateLeadTimes(ObservableList<XYChart.Data<String, Number>> data, List<LeadTimeStats> entries, LeadTimeCallback callback){
        SimpleDateFormat format = new SimpleDateFormat("MMM dd");

        data.setAll(
          entries.stream()
              .sorted(Comparator.comparing(LeadTimeStats::getDate))
              .map(e -> {
                  return new XYChart.Data<>(format.format(e.getDate()), (Number) callback.getStories(e).size());
              })
              .toList()
        );
    }


    @Override
    protected Task<Object> createTask() {
        return new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                if (projectId == null || startDate == null || endDate == null) {
                    return null;
                }

                List<LeadTimeStats> stats = getAllLeadTimeStats();

                Platform.runLater(() -> {
                    updateLeadTimes(notCreatedStories, stats, LeadTimeStats::getNotCreated);
                    updateLeadTimes(inBacklogStories, stats, LeadTimeStats::getInBacklog);
                    updateLeadTimes(inSprintStories, stats, LeadTimeStats::getInSprint);
                    updateLeadTimes(inProgressStories, stats, LeadTimeStats::getInProgress);
                    updateLeadTimes(readyForTestStories, stats, LeadTimeStats::getInTest);
                    updateLeadTimes(doneStories, stats, LeadTimeStats::getInDone);

                });

                return null;
            }
        };
    }

    interface LeadTimeCallback {
        List<UserStoryInterface> getStories(LeadTimeStats stats);
    }
}
