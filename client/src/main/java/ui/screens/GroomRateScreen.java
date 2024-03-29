package ui.screens;

import atlantafx.base.theme.Styles;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import taiga.models.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.groomrate.GroomRate;
import ui.util.DateUtil;

import java.time.LocalDate;

public class GroomRateScreen extends BaseMetricConfiguration {
    private GroomRate groomRate;
    private DatePicker startDate;
    private DatePicker endDate;

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param id            A unique id for the scene.
     * @param fxmlFilename  The fxml file to load for this screen.
     */
    public GroomRateScreen(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
    }

    @Override
    protected void beforeVisualizationMount() {
        hideSprintParameter();
    }

    @Override
    protected Pane visualization() {
        return this.groomRate;
    }

    @Override
    protected void beforeParameterMount() {
        this.startDate = new DatePicker();
        this.endDate = new DatePicker();
        this.startDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (groomRate != null) {
                groomRate.recalculate(DateUtil.toDate(newValue), DateUtil.toDate(endDate.getValue()));
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
            if (groomRate != null) {
                groomRate.recalculate(DateUtil.toDate(startDate.getValue()), DateUtil.toDate(newValue));
            }
        });
        sprint_combobox.getSelectionModel().selectLast();
        Sprint selectedSprint = sprint_combobox.getValue();
        this.startDate.setValue(DateUtil.toLocal(selectedSprint.getEstimatedStart()));
        this.endDate.setValue(DateUtil.toLocal(selectedSprint.getEstimatedFinish()));
        this.groomRate = new GroomRate(this.startDate.valueProperty(), this.endDate.valueProperty());
    }

    private VBox createDatePickerBox(String name, DatePicker datePicker) {
        Label text = new Label(name);
        text.getStyleClass().add(Styles.TEXT_BOLD);
        return new VBox(text, datePicker);
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
        if (this.groomRate == null) {
            return;
        }
        sprint_combobox.getSelectionModel().selectLast();
        Sprint selectedSprint = sprint_combobox.getValue();
        this.groomRate.recalculate(
                selectedSprint.getEstimatedStart(),
                selectedSprint.getEstimatedFinish()
        );
    }
}
