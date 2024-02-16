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
    private final String name;
    protected ScreenManager screenManager;

    protected boolean loaded;

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param name          A unique name for the scene.
     */
    public Screen(ScreenManager screenManager, String name) {
        this.name = name;
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
        FXMLManager.load("/fxml/" + name + ".fxml", getRoot(), getController());
        this.loaded = true;
    }

    public String getName() {
        return this.name;
    }

    protected abstract void onFocused();
}

