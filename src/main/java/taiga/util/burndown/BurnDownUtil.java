package taiga.util.burndown;

import java.text.SimpleDateFormat;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import taiga.model.query.sprint.Sprint;

public class BurnDownUtil {

    private final TaskBurndown taskBurndown;
    private final UserStoryBurndown userStoryBurndown;
    private final BusinessValueBurndown businessValueBurndown;

    private ObservableList<XYChart.Data<String, Number>> taskBurndownData;
    private ObservableList<XYChart.Data<String, Number>> userStoryBurndownData;
    private ObservableList<XYChart.Data<String, Number>> businessValueBurndownData;

    public BurnDownUtil() {
        this.taskBurndown = new TaskBurndown();
        this.userStoryBurndown = new UserStoryBurndown();
        this.businessValueBurndown = new BusinessValueBurndown();

        this.taskBurndownData = FXCollections.observableArrayList();
        this.userStoryBurndownData = FXCollections.observableArrayList();
        this.businessValueBurndownData = FXCollections.observableArrayList();
    }

    private void clear() {
        this.taskBurndownData.clear();
        this.userStoryBurndownData.clear();
        this.businessValueBurndownData.clear();
    }

    public void recalculate(Sprint sprint) {
        this.clear();
        this.taskBurndownData.addAll(asXYData(this.taskBurndown.calculate(sprint)));
        this.userStoryBurndownData.addAll(asXYData(this.userStoryBurndown.calculate(sprint)));
        this.businessValueBurndownData.addAll(asXYData(this.businessValueBurndown.calculate(sprint)));
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
}
