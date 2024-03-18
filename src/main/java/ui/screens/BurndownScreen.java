package ui.screens;

import atlantafx.base.theme.Styles;
import java.util.ArrayList;
import java.util.Collections;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;
import settings.Settings;
import taiga.model.query.project.Project;
import taiga.model.query.sprint.Sprint;
import ui.components.screens.ScreenManager;
import ui.metrics.burndown.Burndown;

public class BurndownScreen extends BaseMetricConfiguration {

    private Burndown burndown;
    private ComboBox<Sprint> endSprint;

    private CheckComboBox<Sprint> checkComboBox;

    private Label overlayLabel;
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
        checkComboBox.disableProperty().bind(this.burndown.serviceRunning());
    }

    @Override
    protected StackPane visualization() {
        return this.burndown;
    }

    protected void updateDisplayedSprints(boolean overlay){
        ArrayList<Sprint> list = new ArrayList<>(checkComboBox.getCheckModel().getCheckedItems());
        Collections.reverse(list);
        this.burndown.selectSprints(list, overlay);
    }


    /**
     * Adds the switch sprint functionality to the sprint dropdown after the page is rendered.
     */
    @Override
    protected void afterVisualizationMount() {
        checkComboBox.getCheckModel().getCheckedItems().addListener(
            (ListChangeListener<? super Sprint>) (change) -> {
                if(checkComboBox.getCheckModel().getCheckedItems().size() > 1){
                    overlayCheckBox.setVisible(true);
                    overlayCheckBox.setDisable(false);
                    overlayLabel.setVisible(true);
                    updateDisplayedSprints(overlayCheckBox.isSelected());
                }
                else{
                    overlayCheckBox.setVisible(false);
                    overlayCheckBox.setDisable(true);
                    overlayLabel.setVisible(false);
                    updateDisplayedSprints(false);
                }
            });

        overlayCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            updateDisplayedSprints(overlayCheckBox.isSelected());
        });

        updateDisplayedSprints(overlayCheckBox.isSelected());
    }


    @Override
    protected void beforeParameterMount() {
        this.hideSprintParameter();

        SimpleObjectProperty<Project> currentProject = Settings.get().getAppModel().getCurrentProject();

        checkComboBox = new CheckComboBox<>(FXCollections.observableList(currentProject.get().getSprints()));
        checkComboBox.getCheckModel().check(FXCollections.observableList(currentProject.get().getSprints()).size() - 1);
        checkComboBox.setPrefWidth(150);

        overlayLabel = new Label("Overlay Sprints");
        overlayLabel.setVisible(false);
        overlayCheckBox = new CheckBox();
        overlayCheckBox.setVisible(false);
        overlayCheckBox.setDisable(true);
    }

    @Override
    protected VBox parameters() {
        Label label = new Label("Select Sprints");
        label.getStyleClass().add(Styles.TEXT_BOLD);
        HBox hbox = new HBox(checkComboBox, overlayLabel, overlayCheckBox);
        hbox.setSpacing(5);
        return new VBox(label, hbox);
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
