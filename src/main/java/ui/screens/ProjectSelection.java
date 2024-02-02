package ui.screens;

import javafx.scene.layout.VBox;
import ui.ScreenManager;
import ui.components.Screen;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectSelection extends Screen<VBox> {

    private static final VBox root = new VBox();

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param name          A unique name for the scene.
     */
    public ProjectSelection(ScreenManager screenManager, String name) {
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

    }
}
