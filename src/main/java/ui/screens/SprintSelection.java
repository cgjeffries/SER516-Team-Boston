package ui.screens;

import javafx.scene.layout.VBox;
import settings.Settings;
import ui.components.Screen;
import ui.util.ScreenManager;

import java.net.URL;
import java.util.ResourceBundle;

public class SprintSelection extends Screen<VBox> {
    private static final VBox root = new VBox();

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
        System.out.println(Settings.get().getAppModel().getCurrentProject().getName());

    }
}
