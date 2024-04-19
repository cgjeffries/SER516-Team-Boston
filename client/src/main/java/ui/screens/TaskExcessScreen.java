package ui.screens;

import atlantafx.base.theme.Styles;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import settings.Settings;
import taiga.models.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.taskexcess.TaskExcess;
import ui.util.DateUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class TaskExcessScreen extends BaseMetricConfiguration{

    private TaskExcess taskExcess;
    private DatePicker startDate;
    private DatePicker endDate;

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param id            A unique id for the scene.
     * @param fxmlName      The fxml file to load for this screen.
     */
    public TaskExcessScreen(ScreenManager screenManager, String id, String fxmlName) {
        super(screenManager, id, fxmlName);
    }

    @Override
    protected void beforeVisualizationMount(){
        this.taskExcess = new TaskExcess();
    }

    @Override
    protected void beforeParameterMount(){
        sprint_name.textProperty().unbind();

        this.startDate = new DatePicker();
        this.endDate = new DatePicker();
        this.startDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (taskExcess != null) {
                calendarUpdate();
            }

            //disable all days in the EndDate picker before the start date
            endDate.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(empty || date.isBefore(startDate.getValue().plusDays(1)));
                }
            });
        });
        this.endDate.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (taskExcess != null) {
                calendarUpdate();
            }

            //disable all days in the StartDate picker after the end date
            startDate.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(empty || date.isAfter(endDate.getValue()));
                }
            });
        });
    }

    @Override
    protected void afterVisualizationMount(){
        sprint_combobox.setOnAction((e) -> {
            Sprint sprint = sprint_combobox.getValue();
            if (sprint == null) {
                return;
            }
            this.startDate.setValue(DateUtil.toLocal(sprint.getEstimatedStart()));
            this.endDate.setValue(DateUtil.toLocal(sprint.getEstimatedFinish()));

            sprint_name.setText(sprint.getName());
            updateTaskExcess();
        });
        sprint_combobox.getSelectionModel().selectLast();

        Sprint sprint = sprint_combobox.getValue();
        this.startDate.setValue(DateUtil.toLocal(sprint.getEstimatedStart()));
        this.endDate.setValue(DateUtil.toLocal(sprint.getEstimatedFinish()));
        sprint_name.setText(sprint.getName());
        updateTaskExcess();
    }
    /**
     * helper function for creating datePicker boxes in the parameters box
     */
    private VBox createDatePickerBox(String name, DatePicker datePicker) {
        Label text = new Label(name);
        text.getStyleClass().add(Styles.TEXT_BOLD);
        return new VBox(text, datePicker);
    }

    @Override
    protected Pane visualization() {
        return this.taskExcess;
    }


    /**
     * update the value of the calendars with the values from the sprint in the sprint combobox
     */
    private void calendarUpdate() {
        if (startDate.getValue() != null && endDate.getValue() != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

            sprint_name.setText(format.format(DateUtil.toDate(startDate.getValue())) + " - " +
                    format.format(DateUtil.toDate(endDate.getValue())));
            updateTaskExcess();
        }
    }

    /**
     * update the Task Excess based on the date range
     */
    private void updateTaskExcess() {
        taskExcess.switchDates(Settings.get().getAppModel().getCurrentProject().get().getId(),
                DateUtil.toDate(startDate.getValue()), DateUtil.toDate(endDate.getValue()));
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
    protected void onFocused() {
        if (this.taskExcess == null) {
            return;
        }
        sprint_combobox.getSelectionModel().selectLast();
        this.taskExcess.focusFirstTab();
    }
}

