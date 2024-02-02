package ui.screens;

import atlantafx.base.controls.CustomTextField;
import atlantafx.base.theme.Styles;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import taiga.api.ProjectAPI;
import ui.ScreenManager;
import ui.components.Icon;
import ui.components.Screen;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectSelection extends Screen<VBox> {

    private static final VBox root = new VBox();

    @FXML
    private CustomTextField project_textfield;

    private final StringProperty project_textfield_value;

    private final ProgressIndicator progress;

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param name          A unique name for the scene.
     */
    public ProjectSelection(ScreenManager screenManager, String name) {
        super(screenManager, name);
        project_textfield_value = new SimpleStringProperty();
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
        project_textfield.setLeft(new Icon("bx-search", 16));
        progress.setMaxSize(16, 16);
        progress.setVisible(false);
        project_textfield.setRight(progress);
        project_textfield.textProperty().bindBidirectional(project_textfield_value);
    }

    private String extractSlug(String value) {
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
        project_textfield.setEditable(false);
        project_textfield.pseudoClassStateChanged(Styles.STATE_DANGER, false);
        String value = project_textfield_value.getValue();
        String slug = extractSlug(value);
        if (slug == null) {
            project_textfield.setEditable(true);
            project_textfield.pseudoClassStateChanged(Styles.STATE_DANGER, true);
            return;
        }
        progress.setVisible(true);
        new ProjectAPI().getProject(slug, result -> {
            project_textfield.setEditable(true);
            progress.setVisible(false);
            if (result.getStatus() == 200) {
                System.out.println(result.getContent());
            }
        });
    }
}
