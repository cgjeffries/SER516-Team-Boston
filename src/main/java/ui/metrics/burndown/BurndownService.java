package ui.metrics.burndown;

import java.text.SimpleDateFormat;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import taiga.model.query.sprint.Sprint;

public class BurndownService extends Service<Object> {
    private Sprint sprint;

    private final TaskBurndown taskBurndown;
    private final UserStoryBurndown userStoryBurndown;
    private final BusinessValueBurndown businessValueBurndown;

    private final BurndownService.Data taskBurndownData;
    private final BurndownService.Data userStoryBurndownData;
    private final BurndownService.Data businessValueBurndownData;

    public BurndownService() {
        this.taskBurndown = new TaskBurndown();
        this.userStoryBurndown = new UserStoryBurndown();
        this.businessValueBurndown = new BusinessValueBurndown();

        this.taskBurndownData = new Data();
        this.userStoryBurndownData = new Data();
        this.businessValueBurndownData = new Data();
    }

    public void recalculate(Sprint sprint) {
        this.sprint = sprint;
        this.restart();
    }

    protected Data getTaskData() {
        return this.taskBurndownData;
    }

    protected Data getUserStoryData() {
        return this.userStoryBurndownData;
    }

    protected Data getBusinessValueData() {
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
                if (sprint == null) {
                    return null;
                }

                List<BurnDownEntry> taskXYData = taskBurndown.calculate(sprint);
                List<BurnDownEntry> userStoryXYData = userStoryBurndown.calculate(sprint);
                List<BurnDownEntry> businessValueXYData = businessValueBurndown.calculate(sprint);

                Platform.runLater(() -> {
                    updateBurndownData(taskBurndownData, taskXYData);
                    updateBurndownData(userStoryBurndownData, userStoryXYData);
                    updateBurndownData(businessValueBurndownData, businessValueXYData);
                });

                return null;
            }
        };
    }

    private void updateBurndownData(Data burndownData, List<BurnDownEntry> entries) {
        SimpleDateFormat format = new SimpleDateFormat("MMM dd");
        burndownData.setIdeal(entries.stream().map(d -> new XYChart.Data<>(format.format(d.getDate()), (Number) d.getIdeal())).toList());
        burndownData.setCalculated(entries.stream().map(d -> new XYChart.Data<>(format.format(d.getDate()), (Number) d.getCurrent())).toList());
    }

    protected static class Data {
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

        protected ObservableList<XYChart.Data<String, Number>> getCalculated() {
            return calculated;
        }

        protected ObservableList<XYChart.Data<String, Number>> getIdeal() {
            return ideal;
        }
    }
}
