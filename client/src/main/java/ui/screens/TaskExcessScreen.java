package ui.screens;

import atlantafx.base.theme.Styles;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import settings.Settings;
import ui.components.screens.ScreenManager;
import ui.metrics.taskexcess.TaskExcess;
import ui.util.DateUtil;

import java.text.SimpleDateFormat;

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
        //TODO
    }

    @Override
    protected void beforeParameterMount(){
        //TODO
    }

    @Override
    protected void afterVisualizationMount(){
        //TODO
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

