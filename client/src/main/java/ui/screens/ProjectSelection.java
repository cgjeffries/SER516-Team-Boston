package ui.screens;

import atlantafx.base.controls.CustomTextField;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import atlantafx.base.util.Animations;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.javafx.FontIcon;
import settings.Settings;
import taiga.TaigaClient;
import taiga.models.project.Project;
import taigaold.util.TaigaUtil;
import ui.components.Icon;
import ui.components.screens.Screen;
import ui.components.screens.ScreenManager;
import ui.util.DefaultLogoResolver;

public class ProjectSelection extends Screen {

    private static final StackPane root = new StackPane();

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
     * @param id            A unique id for the scene.
     * @param fxmlFilename  The fxml file to load for this screen.
     */
    public ProjectSelection(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
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
    public StackPane getRoot() {
        return root;
    }

    @Override
    public Object getController() {
        return this;
    }

    @Override
    protected void setup() {
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

    /**
     * Calls the Taiga API at set endpoint with currently entered project slug
     *
     * @param ae The even triggering the function, in this case the search button.
     */
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
            showErrorAlert("Invalid search input. Please enter a valid project name or slug.");
            return;
        }
        progress.setVisible(true);
        this.getRoot().setDisable(true);
        TaigaClient.getProjectAPI().getProject(slug, result -> {
            if (result.getStatus() != 200) {
                project_search_bar.setEditable(true);
                project_search_btn.setDisable(false);
                progress.setVisible(false);
                this.getRoot().setDisable(false);
                showErrorAlert("Failed to fetch project. Please try again later.");
                return;
            }
            Project project = result.getContent();
            project.loadSprints().thenAccept(x -> {
                project_search_bar.setEditable(true);
                project_search_btn.setDisable(false);
                progress.setVisible(false);
                this.getRoot().setDisable(false);
                addProject(project);
                Platform.runLater(() -> project_search_bar.clear());
            }).exceptionally(ex -> {
                showErrorAlert("Failed to load sprints for project: " + project.getName());
                return null;
            });
        }).exceptionally(ex -> {
            showErrorAlert("Failed to fetch project due to an unexpected error.");
            return null;
        });
    }

    private void showErrorAlert(String message) {
        Platform.runLater(() ->{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    @FXML
    public void goBack(ActionEvent ae) {
        screenManager.switchScreen("Metric Selection");
    }

    public void gotoMetricConfiguration() {
        screenManager.switchScreen(Settings.get().getAppModel().getSelectedMetric().get());
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
                Settings.get().getAppModel().setCurrentProject(project);
                projectSelection.gotoMetricConfiguration();
            });
            setGraphic(root);
        }
    }

    @Override
    protected void onFocused() {

    }
}
