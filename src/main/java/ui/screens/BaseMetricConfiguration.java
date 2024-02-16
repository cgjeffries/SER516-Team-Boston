package ui.screens;

import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import settings.Settings;
import taiga.model.query.project.Project;
import taiga.model.query.sprint.Sprint;
import ui.components.Icon;
import ui.components.screens.Screen;
import ui.components.screens.ScreenManager;

public abstract class BaseMetricConfiguration extends Screen<VBox> {
    private final VBox root = new VBox();

    @FXML
    private Button back;

    @FXML
    private Label metric_name;

    // @FXML
    // private ImageView project_logo;

    @FXML
    private Label project_name;

    @FXML
    private VBox visualization_root;

    @FXML
    private VBox parameters_root;
    // @FXML
    // private Label project_description;

    // @FXML
    // private Label sprint_name;

    // @FXML
    // private Label sprint_dates;

    @FXML
    protected ComboBox<Sprint> sprint_combobox;

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param name          A unique name for the scene.
     */
    public BaseMetricConfiguration(ScreenManager screenManager, String name) {
        super(screenManager, name);
    }

    @Override
    public VBox getRoot() {
        return root;
    }

    @Override
    public Object getController() {
        return this;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    @FXML
    private void goBack() {
        screenManager.switchScreen("project_selection");
    }

    private static class SprintComboboxCellFactory implements Callback<ListView<Sprint>, ListCell<Sprint>> {
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

    protected abstract Pane visualization();

    protected abstract Pane parameters();

    protected void onFocused() {

    }

    protected void onInitialize() {

    }

    protected void beforeParameterMount() {

    }

    protected void afterParameterMount() {

    }

    protected void afterVisualizationMount() {

    }

    protected void beforeVisualizationMount() {

    }
}
