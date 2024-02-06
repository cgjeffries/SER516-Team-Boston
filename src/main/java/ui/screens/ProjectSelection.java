package ui.screens;

import atlantafx.base.controls.CustomTextField;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import atlantafx.base.util.Animations;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.javafx.FontIcon;
import settings.Settings;
import taiga.api.ProjectAPI;
import taiga.model.query.project.Project;
import taiga.util.TaigaUtil;
import ui.components.Icon;
import ui.components.Screen;
import ui.util.DefaultLogoResolver;
import ui.util.ScreenManager;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectSelection extends Screen<VBox> {

    private static final VBox root = new VBox();

    @FXML
    private CustomTextField project_search_bar;

    @FXML
    private Button project_search_btn;

    @FXML
    private Button project_back_btn;

    @FXML
    private ListView<Project> project_list;

    @FXML
    private Label metric_label;

    private final ProgressIndicator progress;

    private final ObservableList<Project> projects;

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param name          A unique name for the scene.
     */
    public ProjectSelection(ScreenManager screenManager, String name) {
        super(screenManager, name);
        progress = new ProgressIndicator(-1d);
        projects = FXCollections.observableArrayList(Settings.get().getAppModel().getProjects());
        projects.addListener((ListChangeListener<Project>) change -> {
            if (!change.next()) {
                return;
            }
            if (change.wasAdded()) {
                Project p = change.getAddedSubList().get(0);
                Settings.get().getAppModel().addProject(p);
            } else if (change.wasRemoved()) {
                Project p = change.getRemoved().get(0);
                Settings.get().getAppModel().removeProject(p);
            }
        });
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
        metric_label.textProperty().bind(Settings.get().getAppModel().getSelectedMetric());
        project_search_bar.setLeft(new Icon(BoxiconsRegular.SEARCH, 16));
        project_search_btn.setGraphic(new Icon(BoxiconsRegular.SEARCH, 16));
        project_search_btn.getStyleClass().add(Styles.ACCENT);
        project_back_btn.setGraphic(new Icon(BoxiconsRegular.ARROW_BACK));
        project_back_btn.getStyleClass().add(Styles.FLAT);
        progress.setMaxSize(16, 16);
        progress.setVisible(false);
        project_search_bar.setRight(progress);
        project_list.setCellFactory(c -> new ProjectCell(this));
        project_list.getStyleClass().add(Tweaks.EDGE_TO_EDGE);
        project_list.setItems(projects);
    }

    private void addProject(Project project) {
        Platform.runLater(() -> projects.add(project));
    }

    private void removeProject(Project project) {
        Platform.runLater(() -> projects.remove(project));
    }

    @FXML
    public void handleSearch(ActionEvent ae) {
        project_search_bar.setEditable(false);
        project_search_btn.setDisable(true);
        project_search_bar.pseudoClassStateChanged(Styles.STATE_DANGER, false);
        String value = project_search_bar.getText();
        String slug = TaigaUtil.extractSlug(value);
        if (slug == null) {
            project_search_bar.setEditable(true);
            project_search_btn.setDisable(false);
            Animations.shakeX(project_search_bar, 6).playFromStart();
            project_search_bar.pseudoClassStateChanged(Styles.STATE_DANGER, true);
            return;
        }
        progress.setVisible(true);
        new ProjectAPI().getProject(slug, result -> {
            project_search_bar.setEditable(true);
            project_search_btn.setDisable(false);
            progress.setVisible(false);
            if (result.getStatus() == 200) {
                addProject(result.getContent());
                Platform.runLater(() -> project_search_bar.clear());
            }
        });
    }

    @FXML
    public void goBack(ActionEvent ae) {
        screenManager.switchScreen("metric_selection");
    }
    
    public void gotoSprintSelection() {
        screenManager.switchScreen(("sprint_selection"));
    }

    private static class ProjectCell extends ListCell<Project> {
        private final ProjectSelection projectSelection;

        public ProjectCell(ProjectSelection projectSelection) {
            this.projectSelection = projectSelection;
        }

        private MenuItem deleteButton(Project p) {
            MenuItem delete = new MenuItem("Delete");
            delete.setGraphic(new FontIcon(BoxiconsRegular.TRASH));
            delete.getStyleClass().add(Styles.DANGER);
            delete.setOnAction(e -> projectSelection.removeProject(p));
            return delete;
        }

        @Override
        protected void updateItem(Project project, boolean empty) {
            super.updateItem(project, empty);
            if (empty || project == null) {
                setGraphic(null);
                return;
            }
            setStyle(getStyle() + "-fx-padding: 0px;");
            Tile root = new Tile();
            MenuButton menu = new MenuButton();
            menu.setGraphic(new FontIcon(BoxiconsRegular.DOTS_VERTICAL_ROUNDED));
            menu.getItems().setAll(deleteButton(project));
            menu.getStyleClass().addAll(Tweaks.NO_ARROW, Styles.BUTTON_OUTLINED);
            root.setGraphic(new ImageView(DefaultLogoResolver.getProjectLogoImage(project, 40, 40)));
            root.setTitle(project.getName());
            root.setDescription(project.getDescription());
            root.setAction(menu);
            root.setActionHandler(() -> {
                // TODO: transition to next screen
                Settings.get().getAppModel().setCurrentProject(project);
                projectSelection.gotoSprintSelection();
                System.out.println(project.getName());
            });
            setGraphic(root);
        }
    }
}
