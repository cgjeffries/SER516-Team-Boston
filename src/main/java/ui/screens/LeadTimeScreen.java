package ui.screens;

import atlantafx.base.theme.Styles;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import settings.Settings;
import taiga.model.query.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.leadtime.LeadTime;
import ui.util.DateUtil;

public class LeadTimeScreen extends BaseMetricConfiguration {
    private LeadTime leadTime;
    private DatePicker startDate;
    private DatePicker endDate;

    public LeadTimeScreen(ScreenManager screenManager, String id, String fxmlName) {
        super(screenManager, id, fxmlName);
    }

    @Override
    protected void beforeVisualizationMount() {
        this.leadTime = new LeadTime();
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
        return this.leadTime;
    }

    /**
     * update the value of the calendars with the values from the sprint in the sprint combobox
     */
    private void calendarUpdate(){
        if(startDate.getValue() != null && endDate.getValue() != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

            sprint_name.setText(format.format(DateUtil.toDate(startDate.getValue())) + " - " +
                format.format(DateUtil.toDate(endDate.getValue())));
            updateLeadTime();
        }
    }

    /**
     * update the Lead Time based on the date range
     */
    private void updateLeadTime(){
        leadTime.switchDates(Settings.get().getAppModel().getCurrentProject().get().getId(),
            DateUtil.toDate(startDate.getValue()), DateUtil.toDate(endDate.getValue()));
    }

    @Override
    protected void beforeParameterMount() {
        sprint_name.textProperty().unbind();
        sprint_name.setText("asdlfjhkjalshhfdkljsagf");

        this.startDate = new DatePicker();
        this.endDate = new DatePicker();
        this.startDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (leadTime != null) {
                calendarUpdate();
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
            if (leadTime != null) {
                calendarUpdate();
            }
        });
    }

    @Override
    protected void afterVisualizationMount() {
        sprint_combobox.setOnAction((e) -> {
            Sprint sprint = sprint_combobox.getValue();
            if (sprint == null) {
                return;
            }
            this.startDate.setValue(DateUtil.toLocal(sprint.getEstimatedStart()));
            this.endDate.setValue(DateUtil.toLocal(sprint.getEstimatedFinish()));

            sprint_name.setText(sprint.getName());
            updateLeadTime();
        });
        sprint_combobox.getSelectionModel().selectLast();

        Sprint sprint = sprint_combobox.getValue();
        this.startDate.setValue(DateUtil.toLocal(sprint.getEstimatedStart()));
        this.endDate.setValue(DateUtil.toLocal(sprint.getEstimatedFinish()));
        sprint_name.setText(sprint.getName());
        updateLeadTime();
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
        if (this.leadTime == null) {
            return;
        }
        sprint_combobox.getSelectionModel().selectLast();
        this.leadTime.focusFirstTab();
    }
}
