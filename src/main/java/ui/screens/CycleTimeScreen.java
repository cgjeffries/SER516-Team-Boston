package ui.screens;

import atlantafx.base.theme.Styles;
import java.text.SimpleDateFormat;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import settings.Settings;
import taiga.model.query.project.Project;
import taiga.model.query.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.cycletime.CycleTime;
import ui.util.DateUtil;

public class CycleTimeScreen extends BaseMetricConfiguration {
    private CycleTime cycleTime;
    private DatePicker startDate;
    private DatePicker endDate;

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param id            A unique id for the scene.
     * @param fxmlFilename  The fxml file to load for this screen.
     */
    public CycleTimeScreen(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
    }

    @Override
    protected void beforeVisualizationMount() {
        this.cycleTime = new CycleTime();
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
        return this.cycleTime;
    }

    /**
     * update the value of the calendars with the values from the sprint in the sprint combobox
     */
    private void calendarUpdate(){
        if(startDate.getValue() != null && endDate.getValue() != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

            sprint_name.setText(format.format(DateUtil.toDate(startDate.getValue())) + " - " +
                format.format(DateUtil.toDate(endDate.getValue())));
            updateCycleTime();
        }
    }

    /**
     * update the Lead Time based on the date range
     */
    private void updateCycleTime(){
        cycleTime.switchDates(Settings.get().getAppModel().getCurrentProject().get().getId(),
            DateUtil.toDate(startDate.getValue()), DateUtil.toDate(endDate.getValue()));
    }

    /**
     * Adds the switch sprint functionality to the sprint dropdown after the page is rendered.
     */
    @Override
    protected void afterVisualizationMount() {
        sprint_combobox.setOnAction((e) -> {
            Sprint sprint = sprint_combobox.getValue();
            if (sprint == null) {
                return;
            }
            this.cycleTime.switchSprint(sprint);
        });
        sprint_combobox.getSelectionModel().selectLast();
        this.cycleTime.switchSprint(sprint_combobox.getValue());
    }

    @Override
    protected Pane parameters() {
        return null;
    }

    @Override
    protected void onFocused() {
        if (this.cycleTime == null) {
            return;
        }
        sprint_combobox.getSelectionModel().selectLast();
        this.cycleTime.focusFirstTab();
    }
}
