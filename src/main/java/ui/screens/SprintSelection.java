package ui.screens;

import atlantafx.base.theme.Styles;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import settings.Settings;
import taiga.model.query.project.Project;
import taiga.model.query.sprint.Sprint;
import ui.components.Icon;
import ui.components.Screen;
import ui.util.DefaultLogoResolver;
import ui.util.ScreenManager;

import java.net.URL;
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

    private SimpleObjectProperty<Project> project;

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param name          A unique name for the scene.
     */
    public SprintSelection(ScreenManager screenManager, String name) {
        super(screenManager, name);
        this.project = new SimpleObjectProperty<>();
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

        back_btn.setGraphic(new Icon(BoxiconsRegular.ARROW_BACK));
        back_btn.getStyleClass().add(Styles.FLAT);
    }

    @FXML
    private void goBack() {
        screenManager.switchScreen("project_selection");
    }
}
