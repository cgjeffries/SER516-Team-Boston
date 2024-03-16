package ui.screens;

import org.kordamp.ikonli.boxicons.BoxiconsRegular;

import atlantafx.base.theme.Styles;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import settings.Settings;
import taiga.model.query.project.Project;
import taiga.model.query.sprint.Sprint;
import ui.components.Icon;
import ui.components.screens.Screen;
import ui.components.screens.ScreenManager;

public abstract class BaseMetricConfiguration extends Screen {
    private final StackPane root = new StackPane();

    @FXML
    private Button back;

    @FXML
    private Label metric_name;


    @FXML
    private Label project_name;

    @FXML
    private VBox visualization_root;

    @FXML
    private VBox parameters_root;

    @FXML
    protected ComboBox<Sprint> sprint_combobox;

    @FXML
    protected Label sprint_combobox_label;

    @FXML
    protected VBox sprint_parameter_box;


    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param id            A unique id for the scene.
     * @param fxmlFilename  The fxml file to load for this screen.
     */
    public BaseMetricConfiguration(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
    }

    @Override
    public StackPane getRoot() {
        return root;
    }

    @Override
    public Object getController() {
        return this;
    }

    protected void setup() {
        SimpleObjectProperty<Project> currentProject = Settings.get().getAppModel().getCurrentProject();
        metric_name.textProperty().bind(Settings.get().getAppModel().getSelectedMetric());
        project_name.textProperty()
                .bind(Bindings.createObjectBinding(() -> currentProject.get().getName(), currentProject));
        sprint_combobox.itemsProperty().bind(Bindings.createObjectBinding(
                () -> FXCollections.observableList(currentProject.get().getSprints()), currentProject));

        SprintComboboxCellFactory cellFactory = new SprintComboboxCellFactory();
        sprint_combobox.setButtonCell(cellFactory.call(null));
        sprint_combobox.setCellFactory(cellFactory);
        back.setGraphic(new Icon(BoxiconsRegular.ARROW_BACK));
        back.getStyleClass().add(Styles.FLAT);

        beforeParameterMount();
        Pane parametersBody = parameters();
        if (parametersBody != null) {
            parameters_root.getChildren().add(parametersBody);
            afterParameterMount();
        }

        beforeVisualizationMount();
        Pane visualizationBody = visualization();
        if (visualizationBody != null) {
            visualization_root.getChildren().add(visualizationBody);
            afterVisualizationMount();
        }
    }

    protected void hideSprintParameter() {
        sprint_combobox.setVisible(false);
        sprint_combobox.setManaged(false);
        sprint_combobox_label.setVisible(false);
        sprint_combobox_label.setManaged(false);
        sprint_parameter_box.setVisible(false);
        sprint_parameter_box.setManaged(false);
    }

    @FXML
    private void goBack() {
        screenManager.switchScreen("project_selection");
    }

    protected static class SprintComboboxCellFactory implements Callback<ListView<Sprint>, ListCell<Sprint>> {
        @Override
        public ListCell<Sprint> call(ListView<Sprint> sprintListView) {
            return new ListCell<>() {
                @Override
                protected void updateItem(Sprint sprint, boolean empty) {
                    super.updateItem(sprint, empty);
                    if (sprint == null || empty) {
                        setText(null);
                        return;
                    }
                    setText(sprint.getName());
                }
            };
        }
    }

    /**
     * Method for creating a visualization component. This is automatically added to the screen.
     * @return The component to add to the screen
     */
    protected abstract Pane visualization();

    /**
     * Method for adding additional parameters. This is automatically added to the screen.
     * @return Component containing additional parameters
     */
    protected abstract Pane parameters();

    /**
     * {@link ScreenManager} hook, occurs after the screen is set as the focused root
     */
    protected void onFocused() {

    }

    /**
     * Hook method that runs before the {@link BaseMetricConfiguration#parameters()} component is added
     */
    protected void beforeParameterMount() {

    }

    /**
     * Hook method that runs after the {@link BaseMetricConfiguration#parameters()} component is added
     */
    protected void afterParameterMount() {

    }

    /**
     * Hook method that runs before the {@link BaseMetricConfiguration#visualization()} component is added
     */
    protected void afterVisualizationMount() {

    }

    /**
     * Hook method that runs after the {@link BaseMetricConfiguration#visualization()} component is added
     */
    protected void beforeVisualizationMount() {

    }
}
