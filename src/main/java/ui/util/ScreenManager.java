package ui.util;

import javafx.scene.Parent;
import javafx.scene.Scene;
import ui.components.Screen;

import java.util.HashMap;

/**
 * Utility class for managing {@link Screen}s.
 */
public class ScreenManager {
    private final HashMap<String, Screen<? extends Parent>> screenHashMap;
    private Scene scene;

    public ScreenManager() {
        this.screenHashMap = new HashMap<>();
    }

    /**
     * Adds the initial screen to the manager and loads it
     *
     * @param screen The screen to add
     */
    public void initialize(Screen<? extends Parent> screen) {
        addScreen(screen);
        screen.load();
        scene = new Scene(screen.getRoot(), 800, 600);
    }

    /**
     * Get the scene being managed by the ScreenManager
     *
     * @return The managed scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Add a screen to the manager. The screen will only be loaded when {@link #switchScreen(String)} is called.
     *
     * @param screen The screen to add
     */
    public void addScreen(Screen<? extends Parent> screen) {
        addScreen(screen, false);
    }

    /**
     * Add a screen to the manager. Can optionally decide to switch to the screen upon addition.
     *
     * @param screen       The screen to add.
     * @param shouldSwitch Whether the manager should call {@link #switchScreen(String)}
     */
    public void addScreen(Screen<? extends Parent> screen, boolean shouldSwitch) {
        screenHashMap.put(screen.getName(), screen);
        if (shouldSwitch) {
            switchScreen(screen.getName());
        }
    }

    /**
     * Get a screen based on it's name
     *
     * @param screenName The name of the screen to get
     * @return The {@link Screen} instance associated with the screen name
     */
    public Screen<? extends Parent> getScreen(String screenName) {
        return this.screenHashMap.get(screenName);
    }

    /**
     * Switches to a screen given its name. Loads the screen if it isn't already loaded.
     *
     * @param screenName The name of the screen to switch to
     */
    public void switchScreen(String screenName) {
        Screen<? extends Parent> targetScreen = getScreen(screenName);
        if (targetScreen == null) {
            System.err.println("No screen '" + screenName + "' was found.");
            return;
        }
        targetScreen.load();
        scene.setRoot(targetScreen.getRoot());
    }
}
