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

    private ObservableList<XYChart.Data<String, Number>> taskBurndownData;
    private ObservableList<XYChart.Data<String, Number>> userStoryBurndownData;
    private ObservableList<XYChart.Data<String, Number>> businessValueBurndownData;

    public BurndownService() {
        this.taskBurndown = new TaskBurndown();
        this.userStoryBurndown = new UserStoryBurndown();
        this.businessValueBurndown = new BusinessValueBurndown();

        this.taskBurndownData = FXCollections.observableArrayList();
        this.userStoryBurndownData = FXCollections.observableArrayList();
        this.businessValueBurndownData = FXCollections.observableArrayList();
    }

    public void recalculate(Sprint sprint) {
        this.sprint = sprint;
        this.restart();
    }

    private static List<XYChart.Data<String, Number>> asXYData(List<BurnDownEntry> data) {
        SimpleDateFormat format = new SimpleDateFormat("MMM dd");
        return data.stream()
                .map(d -> new XYChart.Data<>(format.format(d.getDate()), (Number) d.getCurrent()))
                .toList();
    }

    public ObservableList<XYChart.Data<String, Number>> getTaskData() {
        return this.taskBurndownData;
    }

    public ObservableList<XYChart.Data<String, Number>> getUserStoryData() {
        return this.userStoryBurndownData;
    }

    public ObservableList<XYChart.Data<String, Number>> getBusinessValueData() {
        return this.businessValueBurndownData;
    }

    @Override
    protected Task<Object> createTask() {
        return new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                if (sprint == null) {
                    return null;
                }
                System.out.println("Calculating burndown data");

                List<XYChart.Data<String, Number>> taskXYData = asXYData(taskBurndown.calculate(sprint));
                List<XYChart.Data<String, Number>> userStoryXYData = asXYData(userStoryBurndown.calculate(sprint));
                List<XYChart.Data<String, Number>> businessValueXYData = asXYData(
                        businessValueBurndown.calculate(sprint));

                Platform.runLater(() -> {
                    taskBurndownData.setAll(taskXYData);
                    userStoryBurndownData.setAll(userStoryXYData);
                    businessValueBurndownData.setAll(businessValueXYData);
                });

                return null;
            }
        };
    }
}
