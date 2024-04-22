package ui.screens;

import java.time.LocalDate;

import atlantafx.base.theme.Styles;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import taiga.models.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.taskinertia.TaskInertia;
import ui.util.DateUtil;

public class TaskInertiaScreen extends BaseMetricConfiguration {

    private TaskInertia taskInertia;
    private DatePicker startDate;
    private DatePicker endDate;

    public TaskInertiaScreen(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
    }

    @Override
    protected void beforeParameterMount() {
        hideSprintParameter();
        this.startDate = new DatePicker();
        this.endDate = new DatePicker();
        this.startDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (taskInertia != null) {
                taskInertia.recalculate(startDate.getValue(), endDate.getValue());
            }
            endDate.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(empty || date.isBefore(startDate.getValue().plusDays(1)));
                }
            });
        });
        this.endDate.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (taskInertia != null) {
                taskInertia.recalculate(startDate.getValue(), endDate.getValue());
            }
        });
        sprint_combobox.getSelectionModel().selectLast();
        Sprint selectedSprint = sprint_combobox.getValue();
        this.startDate.setValue(DateUtil.toLocal(selectedSprint.getEstimatedStart()));
        this.endDate.setValue(DateUtil.toLocal(selectedSprint.getEstimatedFinish()));
        // TODO instantiate with start and end
        this.taskInertia = new TaskInertia();
    }

    @Override
    protected Pane parameters() {
        HBox container = new HBox(
                createDatePickerBox("Start Date: ", startDate),
                createDatePickerBox("End Date: ", endDate)
        );
        container.setSpacing(5);
        return container;
    }

    @Override
    protected Pane visualization() {
        return this.taskInertia;
    }

    private VBox createDatePickerBox(String name, DatePicker datePicker) {
        Label text = new Label(name);
        text.getStyleClass().add(Styles.TEXT_BOLD);
        return new VBox(text, datePicker);
    }

    @Override
    protected void onFocused() {
        if (this.taskInertia == null) {
            return;
        }
        sprint_combobox.getSelectionModel().selectLast();
        Sprint selectedSprint = sprint_combobox.getValue();
        taskInertia.recalculate(startDate.getValue(), endDate.getValue());
        // TODO add method call to recalculate task inertia
    }
}
