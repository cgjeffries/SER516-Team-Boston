package ui.components.screens;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import ui.util.FXMLManager;

/**
 * A screen is a managed container for UI content. To facilitate transitions between screens, see {@link ScreenManager ScreenManager}
 *
 * @param <T> The type of root component this screen should have. This needs to be the same as the root element in the corresponding FXML.
 */
public abstract class Screen<T extends Parent> implements Initializable {
    private final String id;
    private final String fxmlPath;
    protected ScreenManager screenManager;

    protected boolean loaded;

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param id            A unique id for the scene.
     * @param fxmlFilename  The fxml file to load for this screen.
     */
    public Screen(ScreenManager screenManager, String id, String fxmlFilename) {
        this.id = id;
        this.fxmlPath = fxmlFilename;
        this.screenManager = screenManager;
        this.loaded = false;
    }

    /**
     * Gets the root element. This needs to be the same as the root element in the corresponding FXML.
     *
     * @return the root element
     */
    public abstract T getRoot();

    /**
     * Get the controller for the screen. This should be the immediate superclass of the screen (so return `this`)
     *
     * @return The controller for the screen
     */
    public abstract Object getController();

    /**
     * Loads the screen's FXML. Used for lazy loading.
     * If you are using {@link ScreenManager}, you should not call this manually.
     */
    public void load() {
        if (this.loaded) {
            return;
        }
        FXMLManager.load("/fxml/" + fxmlPath + ".fxml", getRoot(), getController());
        this.loaded = true;
    }

    public String getId() {
        return this.id;
    }

    protected abstract void onFocused();
}

