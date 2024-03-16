package ui.screens;

import atlantafx.base.theme.Styles;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import settings.Settings;
import taiga.model.query.project.Project;
import taiga.model.query.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.burndown.Burndown;

public class BurndownScreen extends BaseMetricConfiguration {

    private Burndown burndown;
    private ComboBox<Sprint> endSprint;

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param id            A unique id for the scene.
     * @param fxmlFilename  The fxml file to load for this screen.
     */
    public BurndownScreen(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
    }

    @Override
    protected void beforeVisualizationMount() {
        this.burndown = new Burndown();
    }

    @Override
    protected StackPane visualization() {
        return this.burndown;
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
            this.burndown.switchSprint(sprint);
        });
        sprint_combobox.getSelectionModel().selectLast();
        this.burndown.switchSprint(sprint_combobox.getValue());
    }


    @Override
    protected void beforeParameterMount() {
        SimpleObjectProperty<Project> currentProject = Settings.get().getAppModel().getCurrentProject();
        endSprint = new ComboBox<>();
        SprintComboboxCellFactory cellFactory = new SprintComboboxCellFactory();
        endSprint.setButtonCell(cellFactory.call(null));
        endSprint.setCellFactory(cellFactory);
        endSprint.itemsProperty().bind(Bindings.createObjectBinding(
                () -> FXCollections.observableList(currentProject.get().getSprints()), currentProject));
        endSprint.setPrefWidth(150);
    }

    @Override
    protected VBox parameters() {
        Label label = new Label("End Sprint");
        label.getStyleClass().add(Styles.TEXT_BOLD);
        return new VBox(label, endSprint);
    }

    @Override
    protected void onFocused() {
        if (this.burndown == null) {
            return;
        }
        sprint_combobox.getSelectionModel().selectLast();
        this.burndown.focusFirstTab();
    }
}
