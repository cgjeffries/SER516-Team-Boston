package ui.services;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import taiga.models.sprint.Sprint;
import ui.metrics.burndown.BurnDownEntry;
import ui.metrics.burndown.BusinessValueBurndown;
import ui.metrics.burndown.TaskBurndown;
import ui.metrics.burndown.UserStoryBurndown;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BurndownService extends Service<Object> {
    private List<Sprint> sprints;

    private final TaskBurndown taskBurndown;
    private final UserStoryBurndown userStoryBurndown;
    private final BusinessValueBurndown businessValueBurndown;

    private final ObservableMap<Sprint, Data> taskBurndownData;
    private final ObservableMap<Sprint, Data> userStoryBurndownData;
    private final ObservableMap<Sprint, Data> businessValueBurndownData;

    private boolean overlay = false;

    public BurndownService() {
        this.taskBurndown = new TaskBurndown();
        this.userStoryBurndown = new UserStoryBurndown();
        this.businessValueBurndown = new BusinessValueBurndown();

        this.taskBurndownData = FXCollections.observableHashMap();

        this.userStoryBurndownData = FXCollections.observableHashMap();
        this.businessValueBurndownData = FXCollections.observableHashMap();
        sprints = new ArrayList<>();
    }

    public void recalculate(List<Sprint> sprints) {
        this.recalculate(sprints, false);
    }

    public void recalculate(List<Sprint> sprints, boolean overlay) {
        this.sprints = sprints;
        this.overlay = overlay;
        this.restart();
    }

    public ObservableMap<Sprint, Data> getTaskData() {
        return this.taskBurndownData;
    }

    public ObservableMap<Sprint, Data> getUserStoryData() {
        return this.userStoryBurndownData;
    }

    public ObservableMap<Sprint, Data> getBusinessValueData() {
        return this.businessValueBurndownData;
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
                if (sprints.isEmpty()) {
                    return null;
                }

                Platform.runLater(() -> {
                    taskBurndownData.clear();
                    userStoryBurndownData.clear();
                    businessValueBurndownData.clear();
                });

                for (Sprint sprint : sprints) {
                    List<BurnDownEntry> taskXYData = taskBurndown.calculate(sprint);
                    List<BurnDownEntry> userStoryXYData = userStoryBurndown.calculate(sprint);
                    List<BurnDownEntry> businessValueXYData =
                            businessValueBurndown.calculate(sprint);

                    Platform.runLater(() -> {
                        updateBurndownData(taskBurndownData, taskXYData, sprint);
                        updateBurndownData(userStoryBurndownData, userStoryXYData, sprint);
                        updateBurndownData(businessValueBurndownData, businessValueXYData, sprint);
                    });
                }

                return null;
            }
        };
    }

    private void updateBurndownData(ObservableMap<Sprint, Data> burndownDataMap, List<BurnDownEntry> entries, Sprint sprint) {
        Data burndownData = new Data();

        SimpleDateFormat format = new SimpleDateFormat("MMM dd");

        if (overlay) {
            List<XYChart.Data<String, Number>> ideal = new ArrayList<>();
            List<XYChart.Data<String, Number>> calculated = new ArrayList<>();
            for (int i = 0; i < entries.size(); i++) {
                BurnDownEntry entry = entries.get(i);
                ideal.add(new XYChart.Data<>("Day " + (i + 1), entry.getIdeal()));
                calculated.add(new XYChart.Data<>("Day " + (i + 1), entry.getCurrent()));
            }

            burndownData.setIdeal(ideal);
            burndownData.setCalculated(calculated);
        } else {
            burndownData.setIdeal(entries.stream()
                    .map(d -> new XYChart.Data<>(format.format(d.getDate()), (Number) d.getIdeal()))
                    .toList());
            burndownData.setCalculated(entries.stream()
                    .map(d -> new XYChart.Data<>(format.format(d.getDate()), (Number) d.getCurrent()))
                    .toList());
        }

        burndownDataMap.put(sprint, burndownData);
    }

    public static class Data {
        private final ObservableList<XYChart.Data<String, Number>> calculated;
        private final ObservableList<XYChart.Data<String, Number>> ideal;

        protected Data() {
            this.calculated = FXCollections.observableArrayList();
            this.ideal = FXCollections.observableArrayList();
        }

        protected void setCalculated(List<XYChart.Data<String, Number>> calculated) {
            this.calculated.setAll(calculated);
        }

        protected void setIdeal(List<XYChart.Data<String, Number>> ideal) {
            this.ideal.setAll(ideal);
        }

        public ObservableList<XYChart.Data<String, Number>> getCalculated() {
            return calculated;
        }

        public ObservableList<XYChart.Data<String, Number>> getIdeal() {
            return ideal;
        }
    }
}
