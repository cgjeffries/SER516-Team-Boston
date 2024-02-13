package ui.screens;

import atlantafx.base.theme.Styles;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import settings.Settings;
import taiga.api.SprintAPI;
import taiga.model.query.project.Project;
import taiga.model.query.sprint.Sprint;
import taiga.util.burndown.BurnDownUtil;
import ui.components.Icon;
import ui.components.Screen;
import ui.util.DefaultLogoResolver;
import ui.util.ScreenManager;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class SprintSelection extends Screen<VBox> {
    private static final VBox root = new VBox();

    @FXML
    private Button back_btn;

    @FXML
    private Label metric_name;

    @FXML
    private ImageView project_logo;

    @FXML
    private Label project_title;

    @FXML
    private Label project_description;

    @FXML
    private Label sprint_name;

    @FXML
    private Label sprint_dates;

    @FXML
    private ComboBox<Sprint> sprint_combobox;

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param name          A unique name for the scene.
     */
    public SprintSelection(ScreenManager screenManager, String name) {
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
        metric_name.textProperty().bind(Settings.get().getAppModel().getSelectedMetric());

        SimpleObjectProperty<Project> currentProject = Settings.get().getAppModel().getCurrentProject();
        project_logo.imageProperty().bind(Bindings.createObjectBinding(() -> DefaultLogoResolver.getProjectLogoImage(currentProject.get()), currentProject));
        project_title.textProperty().bind(Bindings.createObjectBinding(() -> currentProject.get().getName(), currentProject));
        project_description.textProperty().bind(Bindings.createObjectBinding(() -> currentProject.get().getDescription(), currentProject));
        sprint_combobox.itemsProperty().bind(Bindings.createObjectBinding(() -> FXCollections.observableList(currentProject.get().getSprints()), currentProject));
        sprint_name.textProperty().bind(Bindings.createStringBinding(() -> {
            Sprint sprint = sprint_combobox.getValue();
            return sprint != null ? sprint.getName() : "(No Sprint Selected)";
        }, sprint_combobox.valueProperty()));
        sprint_dates.textProperty().bind(Bindings.createStringBinding(() -> {
            Sprint sprint = sprint_combobox.getValue();
            if (sprint == null) {
                return "";
            }
            new BurnDownUtil(sprint);
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
            String start = format.format(sprint.getEstimatedStart());
            String end = format.format(sprint.getEstimatedFinish());
            return start + " to " + end;
        }, sprint_combobox.valueProperty()));

        SprintComboboxCellFactory cellFactory = new SprintComboboxCellFactory();
        sprint_combobox.setButtonCell(cellFactory.call(null));
        sprint_combobox.setCellFactory(cellFactory);
        back_btn.setGraphic(new Icon(BoxiconsRegular.ARROW_BACK));
        back_btn.getStyleClass().add(Styles.FLAT);
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
}
