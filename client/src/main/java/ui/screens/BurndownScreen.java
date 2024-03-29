package ui.screens;

import atlantafx.base.theme.Styles;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import settings.Settings;
import taiga.models.project.Project;
import taiga.models.sprint.Sprint;
import ui.components.Multiselect;
import ui.components.screens.ScreenManager;
import ui.metrics.burndown.Burndown;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BurndownScreen extends BaseMetricConfiguration {

    private Burndown burndown;
    private Multiselect<Sprint> sprintMultiselect;
    private CheckBox overlayCheckBox;

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
        sprintMultiselect.disableProperty().bind(this.burndown.serviceRunning());
        overlayCheckBox.disableProperty().bind(this.burndown.serviceRunning());
    }

    @Override
    protected StackPane visualization() {
        return this.burndown;
    }

    protected void updateDisplayedSprints(boolean overlay) {
        List<Sprint> list = new ArrayList<>(sprintMultiselect.getSelectionModel().getSelectedItems());
        Collections.reverse(list);
        this.burndown.selectSprints(list, overlay);
    }

    /**
     * Adds the switch sprint functionality to the sprint dropdown after the page is
     * rendered.
     */
    @Override
    protected void afterVisualizationMount() {
        overlayCheckBox.visibleProperty().bind(Bindings.createBooleanBinding(() -> {
            boolean shouldOverlay = sprintMultiselect.getSelectionModel().getSelectedItems().size() > 1;
            if (shouldOverlay) {
                updateDisplayedSprints(overlayCheckBox.isSelected());
            } else {
                updateDisplayedSprints(false);
            }
            return shouldOverlay;
        }, sprintMultiselect.getSelectionModel().getSelectedItems()));

        overlayCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            updateDisplayedSprints(overlayCheckBox.isSelected());
        });

        sprint_name.textProperty().bind(Bindings.createStringBinding(() -> {
            List<Sprint> sprints = sprintMultiselect.getSelectionModel().getSelectedItems();
            return sprints.isEmpty() ? "(No Sprint Selected)" : sprints.stream().map(Sprint::getName).collect(Collectors.joining(", "));
        }, sprintMultiselect.getSelectionModel().selectedItemProperty()));

        updateDisplayedSprints(overlayCheckBox.isSelected());
    }

    @Override
    protected void beforeParameterMount() {
        this.hideSprintParameter();
        sprint_name.setVisible(true);
        sprint_name.setManaged(true);
        sprint_name.textProperty().unbind();
        SimpleObjectProperty<Project> currentProject = Settings.get().getAppModel().getCurrentProject();

        sprintMultiselect = new Multiselect<>();
        sprintMultiselect.itemsProperty().bind(Bindings.createObjectBinding(
                () -> FXCollections.observableList(currentProject.get().getSprints()), currentProject));

        overlayCheckBox = new CheckBox("Overlay Sprints");
    }

    @Override
    protected VBox parameters() {
        Label label = new Label("Select Sprints");
        label.getStyleClass().add(Styles.TEXT_BOLD);
        HBox hbox = new HBox(sprintMultiselect, overlayCheckBox);
        hbox.setSpacing(5);
        return new VBox(label, hbox);
    }

    @Override
    protected void onFocused() {
        if (this.burndown == null) {
            return;
        }
        sprintMultiselect.getSelectionModel().selectLast();
        this.burndown.focusFirstTab();
    }
}
