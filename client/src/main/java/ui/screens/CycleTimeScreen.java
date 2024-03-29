package ui.screens;

import atlantafx.base.theme.Styles;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import settings.Settings;
import taiga.models.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.cycletime.CycleTime;
import ui.util.DateUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

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
    private void calendarUpdate() {
        if (startDate.getValue() != null && endDate.getValue() != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

            sprint_name.setText(format.format(DateUtil.toDate(startDate.getValue())) + " - " +
                    format.format(DateUtil.toDate(endDate.getValue())));
            updateCycleTimeDates();
        }
    }

    /**
     * update the Cycle Time based on the date range
     */
    private void updateCycleTimeDates() {
        cycleTime.switchDates(Settings.get().getAppModel().getCurrentProject().get().getId(),
                DateUtil.toDate(startDate.getValue()), DateUtil.toDate(endDate.getValue()));
    }

    /**
     * update the Cycle Time based on the Sprint
     */
    private void updateCycleTimeSprint() {
        cycleTime.switchSprint(sprint_combobox.getValue());
    }

    @Override
    protected void beforeParameterMount() {
        sprint_name.textProperty().unbind();

        this.startDate = new DatePicker();
        this.endDate = new DatePicker();

        ChangeListener<LocalDate> startDateChangeListener = new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue,
                                LocalDate localDate, LocalDate t1) {
                if (cycleTime != null) {
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
            }
        };
        this.startDate.valueProperty().addListener(startDateChangeListener);

        ChangeListener<LocalDate> endDateChangeListener = new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue,
                                LocalDate localDate, LocalDate t1) {
                if (cycleTime != null) {
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
            }
        };
        this.endDate.valueProperty().addListener(endDateChangeListener);

        sprint_combobox.setOnAction((e) -> {
            Sprint sprint = sprint_combobox.getValue();
            if (sprint == null) {
                return;
            }
            CycleTime temp = cycleTime;
            cycleTime = null; //prevent calendars from updating the actual service

            this.startDate.setValue(DateUtil.toLocal(sprint.getEstimatedStart()));
            this.endDate.setValue(DateUtil.toLocal(sprint.getEstimatedFinish()));

            cycleTime = temp;
            sprint_name.setText(sprint.getName());
            updateCycleTimeSprint();
        });
    }

    /**
     * Adds the switch sprint functionality to the sprint dropdown after the page is rendered.
     */
    @Override
    protected void afterVisualizationMount() {

        sprint_combobox.getSelectionModel().selectLast();

        Sprint sprint = sprint_combobox.getValue();

        CycleTime temp = cycleTime;
        cycleTime = null; //prevent calendars from updating the actual service
        this.startDate.setValue(DateUtil.toLocal(sprint.getEstimatedStart()));
        this.endDate.setValue(DateUtil.toLocal(sprint.getEstimatedFinish()));
        cycleTime = temp;

        sprint_name.setText(sprint.getName());
        updateCycleTimeSprint();
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
        if (this.cycleTime == null) {
            return;
        }
        sprint_combobox.getSelectionModel().selectLast();
        this.cycleTime.focusFirstTab();
    }
}
