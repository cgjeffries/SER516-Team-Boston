package ui.screens;

import atlantafx.base.controls.CustomTextField;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import taiga.api.ProjectAPI;
import ui.ScreenManager;
import ui.components.Icon;
import ui.components.Screen;
import ui.util.DefaultLogoResolver;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectSelection extends Screen<VBox> {

    private static final VBox root = new VBox();

    @FXML
    private CustomTextField project_search_bar;

    private final StringProperty project_search_bar_value;

    @FXML
    private Button project_search_btn;

    @FXML
    private Button project_back_btn;

    private final ProgressIndicator progress;

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param name          A unique name for the scene.
     */
    public ProjectSelection(ScreenManager screenManager, String name) {
        super(screenManager, name);
        project_search_bar_value = new SimpleStringProperty();
        progress = new ProgressIndicator(-1d);
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
        project_search_bar.setLeft(new Icon(BoxiconsRegular.SEARCH, 16));
        project_search_btn.setGraphic(new Icon(BoxiconsRegular.SEARCH, 16));
        project_search_btn.getStyleClass().add(Styles.ACCENT);
        project_back_btn.setText("Back");
        project_back_btn.setGraphic(new Icon(BoxiconsRegular.ARROW_BACK, 16));
        progress.setMaxSize(16, 16);
        progress.setVisible(false);
        project_search_bar.setRight(progress);
        project_search_bar.textProperty().bindBidirectional(project_search_bar_value);
    }

    private String extractSlug(String value) {
        if (value == null) {
            return null;
        }
        Pattern urlPattern =
                Pattern.compile("https://tree\\.taiga\\.io/project/(?<slug>\\w+-[\\w-]+)");
        Matcher urlMatcher = urlPattern.matcher(value);

        Pattern slugPattern = Pattern.compile("(?<slug>\\w+-[\\w-]+)");
        Matcher slugMatcher = slugPattern.matcher(value);

        if (urlMatcher.find()) {
            return urlMatcher.group("slug");
        } else if (slugMatcher.find()) {
            return slugMatcher.group("slug");
        }
        return null;
    }

    @FXML
    public void handleSearch(ActionEvent ae) {
        project_search_bar.setEditable(false);
        project_search_btn.setDisable(true);
        project_search_bar.pseudoClassStateChanged(Styles.STATE_DANGER, false);
        String value = project_search_bar_value.getValue();
        String slug = extractSlug(value);
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
                System.out.println(result.getContent());
            }
        });
    }

    @FXML
    public void goBack(ActionEvent ae) {
        screenManager.switchScreen("metric_selection");
    }
}
